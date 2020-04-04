package com.redroundrobin.thirema.kafkadatacollector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.redroundrobin.thirema.kafkadatacollector.utils.Consumer;
import com.redroundrobin.thirema.kafkadatacollector.utils.Database;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInserter implements Runnable{
    private Connection connection;
    private Database database;
    private Consumer consumer;
    private static final Logger logger = Logger.getLogger(DataInserter.class.getName());

    public DataInserter(Database database, Consumer consumer) {
        this.database = database;
        this.consumer = consumer;
    }

    private void sinkData(@NotNull Connection c, List<JsonObject> data) {
        Statement stat = null;
        try{

            for(JsonObject record : data) {
                for(JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
                    stat = c.createStatement();
                    JsonObject sensor = jsonSensor.getAsJsonObject();
                    System.out.println(sensor.toString());
                    String insert = "INSERT INTO sensors (sensor_id, device_id, gateway_id, value) VALUES (" +
                            sensor.get("sensorId").getAsInt()+","+
                            record.get("deviceId").getAsInt()+","+
                            "'"+record.get("gateway").getAsString()+"'"+","+
                            sensor.get("data").getAsDouble()
                            +");";

                    stat.executeUpdate(insert);
                    stat.close();
                    c.commit();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }


    @Override
    public void run() {
        connection = database.openConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occur!", e);
        }
        while(true){
            List<JsonObject> records = consumer.fetchMessages();
            this.sinkData(connection, records);
        }
    }
}
