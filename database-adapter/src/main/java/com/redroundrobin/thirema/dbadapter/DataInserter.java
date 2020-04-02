package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;
import com.redroundrobin.thirema.dbadapter.utils.Consumer;

import java.sql.Connection;
import java.sql.SQLException;
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
            database.sinkData(connection, records);
        }
    }
}
