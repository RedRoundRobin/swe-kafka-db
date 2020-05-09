package com.redroundrobin.thirema.kafkadatacollector.models;

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
    long currentTimestamp = System.currentTimeMillis();

    if (hashMap.containsKey(alertId)) {
      long timestamp = hashMap.get(alertId);

      // La registrazione è avvenuta 2 volte negli ultimi N minuti
      if (currentTimestamp < timestamp + secondsDelay*1000) {
        hashMap.remove(alertId);
        return true;
      }
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
