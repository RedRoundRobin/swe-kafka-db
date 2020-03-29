package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.redroundrobin.thirema.dbadapter.utils.AlertTimeTable;
import com.redroundrobin.thirema.dbadapter.utils.Consumer;
import com.redroundrobin.thirema.dbadapter.utils.Message;
import com.redroundrobin.thirema.dbadapter.utils.Producer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataFilter implements DatabaseAdapter {
    private Connection connection;
    private Database database;
    private Consumer consumer;
    private Producer producer;
    private AlertTimeTable alertTimeTable;

    public DataFilter(Database database, Consumer consumer, Producer producer) {
        this.database = database;
        this.consumer = consumer;
        this.producer = producer;
        this.alertTimeTable = new AlertTimeTable();
    }

    private void databaseCommit() throws SQLException {
        connection.commit();
    }

    private boolean databaseCheckDeviceExistence(int realDeviceId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM devices " +
                "WHERE real_device_id = ? LIMIT 1");
        preparedStatement.setInt(1, realDeviceId);
        boolean result = database.findData(preparedStatement);
        return result;
    }

    private boolean databaseCheckSensorExistence(int realSensorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM sensors " +
                "WHERE real_sensor_id = ? LIMIT 1");
        preparedStatement.setInt(1, realSensorId);
        boolean result = database.findData(preparedStatement);
        return result;
    }

    private boolean databaseCheckDisabledAlert(int userId, int alertId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM disabled_users_alerts " +
                "WHERE user_id = ? AND alert_id = ? LIMIT 1");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, alertId);
        boolean result = database.findData(preparedStatement);
        return result;
    }

    private int databaseGetSensorLogicalId(int realDeviceId, int realSensorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT device_id, sensor_id " +
                "FROM sensors_devices_view " +
                "WHERE real_device_id = ? AND real_sensor_id = ? LIMIT 1");
        preparedStatement.setInt(1, realDeviceId);
        preparedStatement.setInt(2, realSensorId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int logicalSensorId = resultSet.getInt("sensor_id");
        preparedStatement.close();
        return logicalSensorId; // only sensor_id
    }

    private void databaseUpdateAlert(int alertId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE alerts SET last_sent = NOW() " +
                "WHERE alert_id = ? LIMIT 1");
        preparedStatement.setInt(1, alertId);
        preparedStatement.executeQuery();
        preparedStatement.close();
        databaseCommit();
    }

    private List<Message> filterRealAlerts(List<JsonObject> data) throws SQLException {

        List<Message> alerts = new ArrayList<>();

        for(JsonObject device : data){
            int realDeviceId = device.get("deviceId").getAsInt();
            if(!databaseCheckDeviceExistence(realDeviceId)) {
                System.out.println("Device not found in DB. The gateway configuration is not up to date.");
                continue;
            }

            for(JsonElement jsonSensor : device.get("sensors").getAsJsonArray()) {
                JsonObject sensor = jsonSensor.getAsJsonObject();
                int realSensorId = sensor.get("sensorId").getAsInt();
                if(!databaseCheckSensorExistence(realSensorId)) {
                    System.out.println("Sensor not found in DB. The gateway configuration is not up to date.");
                    continue;
                }

                int logicalSensorId = databaseGetSensorLogicalId(realDeviceId, realSensorId);
                int sensorValue = sensor.get("data").getAsInt();

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM alerts " +
                        "WHERE (sensor_id = ? AND deleted = 0 " +
                        "AND last_sent < (NOW() - '10 minutes'::interval) ) AND" +
                        "((type==0 AND threshold > ?) OR " +
                        "(type==1 AND threshold < ?) OR " +
                        "(type==2 AND threshold == ?)) ");
                preparedStatement.setInt(1, realSensorId);
                preparedStatement.setInt(2, sensorValue);
                preparedStatement.setInt(3, sensorValue);
                preparedStatement.setInt(4, sensorValue);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next())
                {
                    if(alertTimeTable.verifyAlert(resultSet.getInt("alert_id"))) {
                        Message message = new Message();
                        message.setAlertId(resultSet.getInt("alert_id"));
                        message.setAlertId(resultSet.getInt("entity_id"));
                        message.setRealDeviceId(realDeviceId);
                        message.setRealSensorId(realSensorId);
                        message.setCurrentThreshold(resultSet.getInt("threshold"));
                        message.setCurrentThresholdType(resultSet.getInt("type"));

                        alerts.add(message);
                        databaseUpdateAlert(resultSet.getInt("alert_id"));
                    }
                }
                preparedStatement.close();
            }
        }

        return alerts;
    }

    private List<Message> filterTelegramUsers(List<Message> messages) throws SQLException {
        for(Message message : messages){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id,telegram_chat FROM users " +
                    "WHERE deleted = 0 AND telegram_name IS NOT NULL AND telegram_chat IS NOT NULL " +
                    "AND entity_id= ?");
            preparedStatement.setInt(1, message.getEntityId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> telegramChats = new ArrayList<>();
            while(resultSet.next()){
                if(!databaseCheckDisabledAlert(resultSet.getInt("user_id"), message.getAlertId()))
                {
                    telegramChats.add(resultSet.getString("telegram_chat"));
                }
            }
        }
        return messages;
    }

    private void produce(List<JsonObject> data) throws Exception {
        producer.executeProducer("alerts", data.toString());
    }

    @Override
    public void start() throws SQLException {
        connection = database.openConnection();
        connection.setAutoCommit(false);
    }

    @Override
    public void run() {
        while(true) {
            List<JsonObject> records = consumer.fetchMessages();
            //Filtrare jsonObjects
            //Produce nel topic alerts

            /*
                Procedimento:
                - Chiamo il filterAlerts --> mi ritorna una lista di alerts filtrati e validi, senza gli utenti telegram
                                             che vanno contattati
                - Chiamo il filterTelegramUser --> esegue il controllo di chi deve essere notificato
                                                   e che non abbia quella notifica disabilitata
                - La struttura List<Message> la inoltro con la formattazione automatica al producer Kafka


                Nota:
                - Il controllo attuale viene fatto con un hashmap che verifica se ci sono stati almeno 2 casi
                  negli ultimi 5 minuti. In caso positivo, si ritiene la registrazione valida, altrimenti no.
                  Tuttavia questo potrebbe non funzionare con frequenze dati > 5 minuti.
                - Inoltre, sarebbe da loggare la notifica nella tabella alerts di postgres, segnalando l'ultimo messaggio inviato
                  così da evitare di ricontattare ogni pochi secondi gli alert, nel caso ci siano più segnalazioni positive.
             */

            try {
                List<Message> messages = filterRealAlerts(records);
                messages = filterTelegramUsers(messages);

                // messaggi da inviare al producer

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
