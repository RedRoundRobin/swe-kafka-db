package com.redroundrobin.thirema.dbadapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;

public class TimescaleAdapter {

  
    TimescaleAdapter(Database db) {

    }

    public static void main(String[] args){
        TimescaleAdapter adapter = new TimescaleAdapter();
        adapter.openConnection("jdbc:postgresql://localhost:3456/timescale", "user", "user");

    }
}


