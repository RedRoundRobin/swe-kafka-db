package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.redroundrobin.thirema.dbadapter.utils.Consumer;
import com.redroundrobin.thirema.dbadapter.utils.Producer;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class DataFilter implements DatabaseAdapter {
    private Connection connection;
    private Database database;
    private Consumer consumer;
    private Producer producer;

    public DataFilter(Database database, Consumer consumer, Producer producer) {
        this.database = database;
        this.consumer = consumer;
        this.producer = producer;
    }

    private List<JsonObject> filter(List<JsonObject> data) {

        Iterator it = data.iterator();
        while(it.hasNext()) {
            JsonObject record = (JsonObject) it.next();
            for(JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
                JsonObject sensor = jsonSensor.getAsJsonObject();
                

            }
        }
    }

    private void produce(List<JsonObject> data) throws Exception {
        producer.executeProducer("alerts", data.toString());
    }

    @Override
    public void start() throws SQLException {
        connection = database.openConnection();
        connection.setAutoCommit(false);
    }

    @Override
    public void run() {
        while(true) {
            List<JsonObject> records = consumer.fetchMessages();
            //Filtrare jsonObjects
            //Produce nel topic alerts
        }
    }

}
