package com.redroundrobin.thirema.dbadapter.models;

import java.sql.Timestamp;
import java.util.HashMap;

public class AlertTimeTable {

    private HashMap<Integer, Long> hashMap;
    private long secondsDelay = 300; // 5 minuti

    public AlertTimeTable() {
        hashMap = new HashMap<>();
    }

    public boolean verifyAlert(int alertId) {
        long currentTimestamp = new Timestamp(System.currentTimeMillis()).getTime();

        if(hashMap.containsKey(alertId)) {
            long timestamp = hashMap.get(alertId);

            // La registrazione è avvenuta 2 volte negli ultimi N minuti
            if(timestamp > currentTimestamp-secondsDelay)
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

    public long getSecondsDelay() {
        return secondsDelay;
    }

    public void setSecondsDelay(long minutesDelay) {
        if(minutesDelay < 0)
        {
            minutesDelay = 0;
        }
        this.secondsDelay = minutesDelay;
    }
}
