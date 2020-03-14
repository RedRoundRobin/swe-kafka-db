package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public void sinkData(Connection c, List<JsonObject> data) {
        Statement stat = null;
        try{
            stat = c.createStatement();
            Iterator it = data.iterator();
            while(it.hasNext()) {
                JsonObject record = (JsonObject) it.next();
                for(JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
                    JsonObject sensor = jsonSensor.getAsJsonObject();
                    String insert = "INSERT INTO sensors (sensor_id, device_id, value, time) VALUES ("+
                            sensor.get("sensorId").getAsInt()+","+
                            record.get("deviceId").getAsInt()+","+
                            sensor.get("data").getAsDouble()+"," +
                            "CURRENT_TIMESTAMP);";
                    stat.executeUpdate(insert);
                }
            }

            stat.close();
            c.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }

    public List<JsonObject> getData(Connection c) {
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
                //Deve essere ricorstruito il dato
            }
            rs.close();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Database db = new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user");
        Consumer consumer = new Consumer(new String[]{"US-GATEWAY-1"}, "localhost:29092");
        Connection conn = db.openConnection();
        conn.setAutoCommit(false);
        while(true){
            List<JsonObject> records = consumer.fetchMessages();
            db.sinkData(conn, records);

        }
    }
}
