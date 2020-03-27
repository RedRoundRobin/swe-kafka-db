package com.redroundrobin.thirema.dbadapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class DatabaseAdapter {
    private Connection connection;
    private Database database;

    DatabaseAdapter(Database db) {
        database = db;
    }

    public void start() throws SQLException {
        connection = database.openConnection();
        connection.setAutoCommit(false);
    }


    private class AlertsUpdater implements Callable<List<Alert>> {

        @Override
        public List<Alert> call() throws Exception {
            return database.getActiveAlerts(connection);
        }
    }

    private class DataSinker implements Runnable {
        List<JsonObject> records;

        public DataSinker(List<JsonObject> records) {
            this.records = records;
        }

        @Override
        public void run() {
            database.sinkData(connection, records);
        }
    }

    private class AlertsAdder implements Runnable {
        List<JsonObject> records;
        List<Alert> updatedAlerts;

        @Override
        public void run() {
            try{

                Iterator it = records.iterator();
                while(it.hasNext()) {
                    JsonObject record = (JsonObject) it.next();
                    for(JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
                        JsonObject sensor = jsonSensor.getAsJsonObject();
                        String insert = "INSERT INTO sensors (sensor_id, device_id, gateway_id, value) VALUES (" +
                                sensor.get("sensorId").getAsInt()+","+
                                record.get("deviceId").getAsInt()+","+
                                "'"+record.get("gateway").getAsString()+"'"+","+
                                sensor.get("data").getAsDouble()
                                +");";

                    }
                }
        }
    }

}


