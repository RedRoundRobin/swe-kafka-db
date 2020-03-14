package com.redroundrobin.thirema.dbadapter;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private String address;
    private String username;
    private String password;

    Database(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    void openConnection(){
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
    }
}
