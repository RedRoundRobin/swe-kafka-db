package com.redroundrobin.thirema.dbadapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;

public class TimescaleAdapter {

    private Database database;
    private Consumer consumer;



    TimescaleAdapter(Database db, Consumer cons) {
        database = db;
        consumer = cons;
    }

    public static void main(String[] args){

    }
}


