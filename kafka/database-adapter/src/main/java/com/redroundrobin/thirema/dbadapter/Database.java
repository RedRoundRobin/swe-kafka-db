package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Database {
    private String address;
    private String username;
    private String password;

    Database(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public Connection openConnection() {   //RICORDARSI DI CHIUDERE LA CONNESSIONE
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(address, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    public void sinkData(@NotNull Connection c, List<JsonObject> data) {
        Statement stat = null;
        try{

            Iterator it = data.iterator();
            while(it.hasNext()) {
                JsonObject record = (JsonObject) it.next();
                Random rand = new Random();
                for(JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
                    stat = c.createStatement();
                    JsonObject sensor = jsonSensor.getAsJsonObject();
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

    public List<JsonObject> getData(Connection c) {
        Statement stat = null;
        try {
            stat = c.createStatement();
            ResultSet rs = stat.executeQuery( "SELECT * FROM sensors;" );
            while ( rs.next() ) {
                int sensorId = rs.getInt("sensor_id");
                int deviceId = rs.getInt("device_id");
                double value  = rs.getDouble("value");
                long timestamp = rs.getLong("time");
                //Deve essere ricorstruito il dato
            }
            rs.close();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
