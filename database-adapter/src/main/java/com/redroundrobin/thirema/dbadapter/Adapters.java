package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class Adapters {
    public static void main(String[] args) throws SQLException {

        DatabaseAdapter timescaleAdapter = new DatabaseAdapter(
                new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user"));

        DatabaseAdapter postgreAdapter = new DatabaseAdapter(
                new Database("jdbc:postgresql://localhost:6543/postgre", "user", "user"));

        Consumer cons = new Consumer(Pattern.compile("^gw_.*"), "localhost:29092");

        Connection timescaleConnection = timescaleAdapter.start();
        Connection postgreConnection = postgreAdapter.start();

        while(true){
            List<JsonObject> records = cons.fetchMessages();

        }
        //Un thread che prende records e li inserisce con sinkData
        //Un altro thread che prende i dati, li filtra e li pubblica su un topic "alerts"
    }
}
