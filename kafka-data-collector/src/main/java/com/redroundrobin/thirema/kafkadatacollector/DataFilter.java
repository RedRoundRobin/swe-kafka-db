package com.redroundrobin.thirema.kafkadatacollector;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.redroundrobin.thirema.kafkadatacollector.models.AlertTimeTable;
import com.redroundrobin.thirema.kafkadatacollector.models.Message;
import com.redroundrobin.thirema.kafkadatacollector.utils.Consumer;
import com.redroundrobin.thirema.kafkadatacollector.utils.CustomLogger;
import com.redroundrobin.thirema.kafkadatacollector.utils.Database;
import com.redroundrobin.thirema.kafkadatacollector.utils.Producer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class DataFilter implements Runnable {
  private Connection connection;
  private final Database database;
  private final Consumer consumer;
  private final Producer producer;
  private final AlertTimeTable alertTimeTable;

  private static final Logger logger = CustomLogger.getLogger(DataFilter.class.getName(),
      Level.INFO);

  public DataFilter(Database database, Consumer consumer, Producer producer) {
    this.database = database;
    this.consumer = consumer;
    this.producer = producer;
    this.alertTimeTable = new AlertTimeTable();
  }

  private boolean databaseCheckDeviceExistence(int realDeviceId, String gatewayName) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM devices d, gateways g WHERE d.gateway_id=g.gateway_id AND d.real_device_id = ? AND g.name = ? LIMIT 1")) {
      preparedStatement.setInt(1, realDeviceId);
      preparedStatement.setString(2, gatewayName);
      return database.findData(preparedStatement);
    }
  }

  private boolean databaseCheckSensorExistence(int realSensorId) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM sensors WHERE real_sensor_id = ? LIMIT 1")) {
      preparedStatement.setInt(1, realSensorId);
      return database.findData(preparedStatement);
    }
  }

  private boolean databaseCheckDisabledAlert(int userId, int alertId) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM disabled_users_alerts WHERE user_id = ? AND alert_id = ? LIMIT 1")) {
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, alertId);
      return database.findData(preparedStatement);
    }
  }

  private Pair<Integer, String> databaseGetSensorLogicalIdAndType(int realDeviceId, int realSensorId, String gatewayName) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT sensor_id, type FROM sensors_devices_view WHERE real_device_id = ? AND real_sensor_id = ? AND name = ? LIMIT 1")) {
      preparedStatement.setInt(1, realDeviceId);
      preparedStatement.setInt(2, realSensorId);
      preparedStatement.setString(3, gatewayName);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        resultSet.next();

        int logicalSensorId = resultSet.getInt("sensor_id");
        String sensorType = resultSet.getString("type");

        return new Pair<>(logicalSensorId, sensorType);
      }
    }
  }

  private void databaseUpdateAlert(int alertId) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE alerts SET last_sent = NOW() WHERE alert_id = ?")) {
      preparedStatement.setInt(1, alertId);
      preparedStatement.executeUpdate();
      connection.commit();
    }
  }

  private List<Message> filterRealAlerts(List<JsonObject> data) throws SQLException {
    List<Message> alerts = new ArrayList<>();

    for (JsonObject device : data) {
      int realDeviceId = device.get("deviceId").getAsInt();
      String gatewayName = device.get("gateway").getAsString();

      if (!databaseCheckDeviceExistence(realDeviceId, gatewayName)) {
        logger.warning("Device not found in DB. The gateway configuration is not up to date.");
        continue;
      }

      for (JsonElement jsonSensor : device.get("sensors").getAsJsonArray()) {
        JsonObject sensor = jsonSensor.getAsJsonObject();
        int realSensorId = sensor.get("sensorId").getAsInt();

        if (!databaseCheckSensorExistence(realSensorId)) {
          logger.warning("Sensor not found in DB. The gateway configuration is not up to date.");
          continue;
        }

        Pair<Integer, String> sensorData = databaseGetSensorLogicalIdAndType(realDeviceId, realSensorId, gatewayName);
        int sensorValue = sensor.get("data").getAsInt();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM alerts WHERE (sensor_id = ? AND deleted = false AND ((last_sent < (NOW() - '10 minutes'::interval)) OR (last_sent IS NULL))) AND ((type = 0 AND ? > threshold) OR (type = 1 AND ? < threshold) OR (type = 2 AND ? = threshold)) ")) {
          preparedStatement.setInt(1, sensorData.getKey());
          preparedStatement.setInt(2, sensorValue);
          preparedStatement.setInt(3, sensorValue);
          preparedStatement.setInt(4, sensorValue);

          try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
              int alertId = resultSet.getInt("alert_id");

              if (alertTimeTable.verifyAlert(alertId)) {
                Message message = new Message();

                message.setAlertId(alertId);
                message.setEntityId(resultSet.getInt("entity_id"));
                message.setSensorType(sensorData.getValue());
                message.setRealDeviceId(realDeviceId);
                message.setRealSensorId(realSensorId);
                message.setCurrentThreshold(resultSet.getInt("threshold"));
                message.setCurrentThresholdType(resultSet.getInt("type"));
                message.setCurrentValue(sensorValue);
                message.setRealGatewayName(gatewayName);

                logger.log(Level.INFO, "Valid alert: {0}", alertId);

                alerts.add(message);
                databaseUpdateAlert(alertId);
              }
            }
          }
        }
      }
    }

    return alerts;
  }

  private List<Message> filterTelegramUsers(List<Message> messages) throws SQLException {
    List<Message> telegramChats = new ArrayList<>();
    for (Message message : messages) {
      try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id, telegram_chat FROM users WHERE deleted = false AND telegram_name IS NOT NULL AND telegram_chat IS NOT NULL AND entity_id= ?")) {
        preparedStatement.setInt(1, message.getEntityId());
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            if (!databaseCheckDisabledAlert(resultSet.getInt("user_id"), message.getAlertId())) {
              telegramChats.add(message);
            }
          }
        }
      }
    }
    return telegramChats;
  }

  @Override
  public void run() {
    String topicName = "alerts";
    connection = database.openConnection();
    try {
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurs!", e);
    }
    Gson gson = new Gson();
    while (true) {
      List<JsonObject> records = consumer.fetchMessages();
      logger.log(Level.INFO, () -> records.size() + " kafka records received");
      /*
          Procedimento:
          - Chiamo il filterAlerts --> mi ritorna una lista di alerts filtrati e validi, senza gli utenti telegram
                                       che vanno contattati
          - Chiamo il filterTelegramUser --> esegue il controllo di chi deve essere notificato
                                             e che non abbia quella notifica disabilitata
          - La struttura List<Message> la inoltro con la formattazione automatica al producer Kafka
       */

      if (records.size() > 0) {
        try {
          List<Message> messages = filterRealAlerts(records);
          List<Message> finalMessages = messages;
          logger.log(Level.INFO, () -> finalMessages.size() + " messages created after RealAlerts filter");
          messages = filterTelegramUsers(messages);
          List<Message> finalMessages1 = messages;
          logger.log(Level.INFO, () -> finalMessages1.size() + " messages created after TelegramUsers filter");
          String jsonMessages = gson.toJson(messages);
          if (!messages.isEmpty()) {
            logger.fine(jsonMessages);
            producer.executeProducer(topicName, jsonMessages);
          }

        } catch (SQLException e) {
          logger.log(Level.SEVERE, "SQL Exception occurs!", e);
        } catch (InterruptedException e) {
          logger.log(Level.SEVERE, "Interrupted Exception occur!", e);
        }
      }
    }
  }
}
