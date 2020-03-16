package com.redroundrobin.thirema.dbadapter;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class TimescaleAdapter {

    private Database database;
    private Consumer consumer;



    TimescaleAdapter(Database db, Consumer cons) {
        database = db;
        consumer = cons;
    }

    public void start() throws SQLException {
        Connection conn = database.openConnection();
        conn.setAutoCommit(false);
        while(true){
            List<JsonObject> records = consumer.fetchMessages();
            database.sinkData(conn, records);

        }
    }


    public static void main(String[] args) {

        TimescaleAdapter adapter = new TimescaleAdapter(
                new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user"),
                new Consumer(Pattern.compile("^gw_.*"), "localhost:29092")
        );

        try{
            adapter.start();
        } catch (SQLException e) {
            System.out.println("Error in query execution");
            e.printStackTrace();
        }

    }
}


