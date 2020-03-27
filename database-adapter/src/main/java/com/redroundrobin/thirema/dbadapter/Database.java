package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

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

    public List<Alert> getActiveAlerts(Connection c) {
        Statement stat = null;
        List<Alert> activeAlerts = new ArrayList<>();
        try {
            stat = c.createStatement();
            ResultSet rs = stat.executeQuery( "SELECT * FROM alerts;" );
            while ( rs.next() ) {
                if(rs.getBoolean("deleted")) {
                    continue;
                } else {
                    int alertId = rs.getInt("alert_id"); //TIPO SERIAL?
                    double threshold = rs.getDouble("threshold");
                    int type  = rs.getInt("type");
                    int sensorId = rs.getInt("sensor_id");
                    int entityId = rs.getInt("entity_id");
                    activeAlerts.add(new Alert(alertId, threshold, type, sensorId, entityId));
                }

            }
            rs.close();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeAlerts;
    }

}
