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



    private boolean databaseCheckDeviceExistence(int realDeviceId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM devices " +
                "WHERE real_device_id = ? LIMIT 1");
        preparedStatement.setInt(1, realDeviceId);
        return database.findData(preparedStatement);
    }

    private boolean databaseCheckSensorExistence(int realSensorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM sensors " +
                "WHERE real_sensor_id = ? LIMIT 1");
        preparedStatement.setInt(1, realSensorId);
        return database.findData(preparedStatement);
    }

    private int databaseGetSensorLogicalId(int realDeviceId, int realSensorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT device_id, sensor_id " +
                "FROM sensors_devices_view " +
                "WHERE real_device_id = ? AND real_sensor_id = ? LIMIT 1");
        preparedStatement.setInt(1, realDeviceId);
        preparedStatement.setInt(2, realSensorId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(2); // only sensor_id
    }


    private List<Message> filter(List<JsonObject> data) throws SQLException {

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
                        "WHERE (sensor_id = ? AND deleted = 0) AND" +
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
                    }
                }
            }
        }

        return alerts;
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
        }
    }

}
