package com.redroundrobin.thirema.kafkadatacollector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.redroundrobin.thirema.kafkadatacollector.utils.Consumer;
import com.redroundrobin.thirema.kafkadatacollector.utils.CustomLogger;
import com.redroundrobin.thirema.kafkadatacollector.utils.Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class DataInserter implements Runnable {
  private final Database database;
  private final Consumer consumer;

  private static final Logger logger = CustomLogger.getLogger(DataInserter.class.getName(),
      Level.INFO);

  public DataInserter(Database database, Consumer consumer) {
    this.database = database;
    this.consumer = consumer;
  }

  private void sinkData(@NotNull Connection c, List<JsonObject> data) {
    logger.log(Level.FINE, data::toString);
    for (JsonObject record : data) {
      logger.log(Level.FINER, record::toString);
      for (JsonElement jsonSensor : record.get("sensors").getAsJsonArray()) {
        logger.log(Level.FINEST, jsonSensor::toString);
        try (Statement statement = c.createStatement()) {
          JsonObject sensor = jsonSensor.getAsJsonObject();
          String insert = "INSERT INTO sensors (real_sensor_id, real_device_id, gateway_name, value, req_time) VALUES ("
              + sensor.get("sensorId").getAsInt() + ","
              + record.get("deviceId").getAsInt() + ","
              + "'" + record.get("gateway").getAsString() + "'" + ","
              + sensor.get("data").getAsDouble() + ","
              + "'" + new Timestamp(sensor.get("timestamp").getAsLong()).toString() + "'"
              + ");";
          logger.log(Level.FINEST, insert);

          statement.executeUpdate(insert);
          c.commit();
        } catch (SQLException e) {
          logger.log(Level.SEVERE, "SQL Exception occur!", e);
        }
      }
    }
    logger.log(Level.INFO, "Records created successfully");
  }


  @Override
  public void run() {
    Connection connection = database.openConnection();
    try {
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occur!", e);
    }
    while (true) {
      List<JsonObject> records = consumer.fetchMessages();
      logger.log(Level.INFO, () -> records.size() + " kafka records received");

      if (!records.isEmpty()) {
        this.sinkData(connection, records);
      }
    }
  }
}
