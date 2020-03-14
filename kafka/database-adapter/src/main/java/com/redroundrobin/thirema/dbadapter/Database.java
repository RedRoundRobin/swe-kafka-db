package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Database {
    private String address;
    private String username;
    private String password;

    Database(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    Connection openConnection() {   //RICORDARSI DI CHIUDERE LA CONNESSIONE
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

    void sinkData(Connection c, List<JsonObject> data) {
        Statement stat = null;
        try{
            stat = c.createStatement();
            Iterator it = data.iterator();
            while(it.hasNext()) {
                JsonObject record = (JsonObject) it.next();
                String insert = "INSERT INTO sensors (sensor_id, device_id, value, time)" + "VALUES ("+
                        record.get("sensorId").getAsInt()+
                        record.get("deviceId").getAsInt()+
                        record.get("data").getAsInt()+
                        record.get("timestamp").getAsLong()+
                        ")";
                stat.executeUpdate(insert);
            }
            stat.close();
            c.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    List<JsonObject> getData(Connection c) {
        Statement stat = null;
        List<JsonObject> records = new ArrayList<>();
        try {
            stat = c.createStatement();
            ResultSet rs = stat.executeQuery( "SELECT * FROM sensors;" );
            while ( rs.next() ) {
                int sensorId = rs.getInt("sensor_id");
                int deviceId = rs.getInt("device_id");
                double value  = rs.getDouble("value");
                long timestamp = rs.getLong("time");
                JsonObject record = new JsonObject();
            }
            rs.close();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
