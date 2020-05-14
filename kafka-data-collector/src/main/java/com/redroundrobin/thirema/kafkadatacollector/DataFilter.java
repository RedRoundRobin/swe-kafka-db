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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      Level.FINE);

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

  private boolean databaseCheckSensorExistence(String gatewayName, int realDeviceId, int realSensorId) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM sensors_devices_view WHERE name = ? AND real_device_id = ? AND real_sensor_id = ? LIMIT 1")) {
      preparedStatement.setString(1, gatewayName);
      preparedStatement.setInt(2, realDeviceId);
      preparedStatement.setInt(3, realSensorId);
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

  private Map<String, Object> databaseGetSensorLogicalIdAndType(int realDeviceId, int realSensorId, String gatewayName) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT SDV.device_id, D.name AS device_name, SDV.sensor_id, SDV.type FROM sensors_devices_view SDV JOIN devices D ON SDV.device_id = D.device_id WHERE SDV.real_device_id = ? AND SDV.real_sensor_id = ? AND SDV.name = ? LIMIT 1")) {
      preparedStatement.setInt(1, realDeviceId);
      preparedStatement.setInt(2, realSensorId);
      preparedStatement.setString(3, gatewayName);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        resultSet.next();

        Map<String, Object> row = new HashMap<>();
        row.put("device_id", resultSet.getInt("device_id"));
        row.put("device_name", resultSet.getString("device_name"));
        row.put("sensor_id", resultSet.getInt("sensor_id"));
        row.put("type", resultSet.getString("type"));

        return row;
      }
    }
  }

  private void databaseUpdateAlert(int alertId) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE alerts SET last_sent = clock_timestamp() WHERE alert_id = ?")) {
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

        if (!databaseCheckSensorExistence(gatewayName, realDeviceId, realSensorId)) {
          logger.warning("Sensor not found in DB. The gateway configuration is not up to date.");
          continue;
        }

        Map<String, Object> sensorData = databaseGetSensorLogicalIdAndType(realDeviceId, realSensorId, gatewayName);
        int sensorValue = sensor.get("data").getAsInt();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM alerts WHERE (sensor_id = ? AND deleted = false AND ((last_sent < (clock_timestamp() - INTERVAL '10 minutes')) OR (last_sent IS NULL))) AND ((type = 0 AND ? > threshold) OR (type = 1 AND ? < threshold) OR (type = 2 AND ? = threshold))")) {
          preparedStatement.setInt(1, (int)sensorData.get("sensor_id"));
          preparedStatement.setInt(2, sensorValue);
          preparedStatement.setInt(3, sensorValue);
          preparedStatement.setInt(4, sensorValue);

          try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
              int alertId = resultSet.getInt("alert_id");

              logger.log(Level.FINE, () -> "Found alertId " + alertId);
              if (alertTimeTable.verifyAlert(alertId)) {
                Message message = new Message();

                message.setAlertId(alertId);
                message.setEntityId(resultSet.getInt("entity_id"));
                message.setSensorType((String)sensorData.get("type"));
                message.setDeviceId((int)sensorData.get("device_id"));
                message.setDeviceName((String)sensorData.get("device_name"));
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
          List<String> telegramChatIds = new ArrayList<>();
          while (resultSet.next()) {
            if (!databaseCheckDisabledAlert(resultSet.getInt("user_id"), message.getAlertId())) {
              telegramChatIds.add(resultSet.getString("telegram_chat"));
            }
          }
          message.setTelegramChatIds(telegramChatIds);
        }
      }
      telegramChats.add(message);
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

      if (!records.isEmpty()) {
        try {
          List<Message> messages = filterRealAlerts(records);
          logger.log(Level.INFO, () -> messages.size() + " messages created after RealAlerts filter");
          List<Message> finalMessages = filterTelegramUsers(messages);
          logger.log(Level.INFO, () -> finalMessages.size() + " messages created after TelegramUsers filter");
          if (!finalMessages.isEmpty()) {
            String jsonMessages = gson.toJson(finalMessages);
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
