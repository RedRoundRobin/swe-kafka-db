package com.redroundrobin.thirema.dbadapter;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test(expected = NullPointerException.class)
    public void getDataTest() {
        Database db = new Database("", "", "");
        db.getData(null);
    }
}