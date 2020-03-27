package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataInserter implements DatabaseAdapter{
    private Connection connection;
    private Database database;
    private Consumer consumer;

    public DataInserter(Database database, Consumer consumer) {
        this.database = database;
        this.consumer = consumer;
    }

    @Override
    public void start() throws SQLException {
        connection = database.openConnection();
        connection.setAutoCommit(false);
    }

    @Override
    public void run() {
        while(true){
            List<JsonObject> records = consumer.fetchMessages();
            database.sinkData(connection, records);
        }
    }
}
