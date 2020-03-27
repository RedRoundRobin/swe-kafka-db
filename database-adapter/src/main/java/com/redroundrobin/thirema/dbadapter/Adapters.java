package com.redroundrobin.thirema.dbadapter;

import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class Adapters {
    public static void main(String[] args) throws SQLException {

        //Un thread che prende records e li inserisce con sinkData
        //Un altro thread che prende i dati, li filtra e li pubblica su un topic "alerts"
    }
}
