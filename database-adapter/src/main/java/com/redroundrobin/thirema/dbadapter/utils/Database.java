package com.redroundrobin.thirema.dbadapter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

public class Database {
    private String address;
    private String username;
    private String password;

    public Database(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public Connection openConnection() {   //RICORDARSI DI CHIUDERE LA CONNESSIONE
        Connection c = null;
        try {
            c = DriverManager
                    .getConnection(address, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    public boolean findData(@NotNull PreparedStatement preparedStatement) throws SQLException {
        boolean res = false;
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            res = resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        preparedStatement.close();
        return res;
    }

}
