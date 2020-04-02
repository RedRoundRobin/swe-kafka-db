package com.redroundrobin.thirema.dbadapter.utils;

import java.sql.Timestamp;
import java.util.HashMap;

public class AlertTimeTable {

    private HashMap<Integer, Long> hashMap;
    private static long minutesDelay = 5*60; // 5 min

    public AlertTimeTable() {
        hashMap = new HashMap<>();
    }

    public boolean verifyAlert(int alertId) {
        long currentTimestamp = new Timestamp(System.currentTimeMillis()).getTime();

        if(hashMap.containsKey(alertId)) {
            long timestamp = hashMap.get(alertId);

            // La registrazione è avvenuta 2 volte negli ultimi N minuti
            if(timestamp > currentTimestamp-minutesDelay)
            {
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
