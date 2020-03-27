package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;

import com.redroundrobin.thirema.dbadapter.utils.Consumer;
import org.apache.kafka.clients.producer.Producer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AlertFilter implements DatabaseAdapter {
    private Connection connection;
    private Database database;
    private Consumer consumer;
    private Producer producer;

    public AlertFilter(Database database, Consumer consumer, Producer producer) {
        this.database = database;
        this.consumer = consumer;
        this.producer = producer;
    }

    private List<JsonObject> filter() {
        return null;        //DA RISCRIVERE ANCHE LA FIRMA
    }

    private void produce(List<JsonObject> data) {
    }

    @Override
    public void start() throws SQLException {
        connection = database.openConnection();
        connection.setAutoCommit(false);
    }

    @Override
    public void run() {
        List<JsonObject> records = consumer.fetchMessages();
        //Filtrare jsonObjects
        //Produce nel topic alerts
    }

}
