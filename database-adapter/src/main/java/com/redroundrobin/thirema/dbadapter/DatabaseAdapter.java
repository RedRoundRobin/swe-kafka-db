package com.redroundrobin.thirema.dbadapter;

import java.sql.SQLException;

public interface DatabaseAdapter extends Runnable {
    void start() throws SQLException;
}
