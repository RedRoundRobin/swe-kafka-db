package com.redroundrobin.thirema.kafkadatacollector.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class Database {
  private final String address;
  private final String username;
  private final String password;

  private static final Logger logger = CustomLogger.getLogger(Database.class.getName());

  public Database(String address, String username, String password) {
    this.address = address;
    this.username = username;
    this.password = password;
  }

  public Connection openConnection() { // RICORDARSI DI CHIUDERE LA CONNESSIONE
    Connection c = null;
    try {
      c = DriverManager
          .getConnection(address, username, password);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Exception occurs!", e);
      logger.log(Level.SEVERE, () -> e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    logger.log(Level.INFO, "Opened database successfully");
    return c;
  }

  public boolean findData(@NotNull PreparedStatement preparedStatement) throws SQLException {
    boolean res = false;
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      resultSet.next();
      res = resultSet.getInt(1) > 0;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Exception occurs!", e);
    }
    preparedStatement.close();
    return res;
  }

}
