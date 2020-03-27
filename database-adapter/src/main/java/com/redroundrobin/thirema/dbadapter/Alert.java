package com.redroundrobin.thirema.dbadapter;

import java.util.List;

public class Alert {
    private int alertId;
    private double threshold;
    private int type;
    private int sensorId;
    private int entityId;

    public Alert(int alertId, double threshold, int type, int sensorId, int entityId) {
        this.alertId = alertId;
        this.threshold = threshold;
        this.type = type;
        this.sensorId = sensorId;
        this.entityId = entityId;
    }


}
