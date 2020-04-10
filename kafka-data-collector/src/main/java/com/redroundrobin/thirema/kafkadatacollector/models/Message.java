package com.redroundrobin.thirema.kafkadatacollector.models;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private final String reqType;
    private transient int alertId;
    private transient int entityId;
    private String sensorType;
    private int realDeviceId;
    private int realSensorId;
    private String realGatewayName;
    private int currentThreshold;
    private int currentThresholdType;
    private int currentValue;
    private List<String> telegramChatIds;

    public Message() {
        reqType = "alert";
    }

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public void setRealDeviceId(int realDeviceId) {
        this.realDeviceId = realDeviceId;
    }

    public void setRealSensorId(int realSensorId) {
        this.realSensorId = realSensorId;
    }

    public void setCurrentThreshold(int currentThreshold) {
        this.currentThreshold = currentThreshold;
    }

    public void setCurrentThresholdType(int currentThresholdType) {
        this.currentThresholdType = currentThresholdType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setRealGatewayName(String realGatewayName) {
        this.realGatewayName = realGatewayName;
    }
}
