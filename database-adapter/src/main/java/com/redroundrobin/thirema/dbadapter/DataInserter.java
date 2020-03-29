package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;
import com.redroundrobin.thirema.dbadapter.utils.Consumer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataInserter implements Runnable{
    private Connection connection;
    private Database database;
    private Consumer consumer;

    public DataInserter(Database database, Consumer consumer) {
        this.database = database;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        connection = database.openConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true){
            List<JsonObject> records = consumer.fetchMessages();
            database.sinkData(connection, records);
        }
    }
}
