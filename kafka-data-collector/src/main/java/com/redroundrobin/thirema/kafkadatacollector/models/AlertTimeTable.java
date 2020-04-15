package com.redroundrobin.thirema.kafkadatacollector.models;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class AlertTimeTable {

  private final Map<Integer, Long> hashMap;
  private final long secondsDelay;

  public AlertTimeTable() {
    hashMap = new HashMap<>();
    secondsDelay = 300; // 5 minuti
  }

  public boolean verifyAlert(int alertId) {
    long currentTimestamp = new Timestamp(System.currentTimeMillis()).getTime();

    if (hashMap.containsKey(alertId)) {
      long timestamp = hashMap.get(alertId);

      // La registrazione è avvenuta 2 volte negli ultimi N minuti
      if (timestamp > currentTimestamp - secondsDelay) {
        hashMap.remove(alertId);
        return true;
      }
    } else {
      return true;
    }

    hashMap.put(alertId, currentTimestamp);
    return false;
  }

  public void clear() {
    hashMap.clear();
  }

  public int size() {
    return hashMap.size();
  }
}
