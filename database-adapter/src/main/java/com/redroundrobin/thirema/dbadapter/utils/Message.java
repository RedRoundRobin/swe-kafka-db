package com.redroundrobin.thirema.dbadapter.utils;

import java.util.List;

public class Message {
    private String reqType = "alert";
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

    public String getReqType() {
        return reqType;
    }

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getRealDeviceId() {
        return realDeviceId;
    }

    public void setRealDeviceId(int realDeviceId) {
        this.realDeviceId = realDeviceId;
    }

    public int getRealSensorId() {
        return realSensorId;
    }

    public void setRealSensorId(int realSensorId) {
        this.realSensorId = realSensorId;
    }

    public int getCurrentThreshold() {
        return currentThreshold;
    }

    public void setCurrentThreshold(int currentThreshold) {
        this.currentThreshold = currentThreshold;
    }

    public int getCurrentThresholdType() {
        return currentThresholdType;
    }

    public void setCurrentThresholdType(int currentThresholdType) {
        this.currentThresholdType = currentThresholdType;
    }

    public List<String> getTelegramChatIds() {
        return telegramChatIds;
    }

    public void setTelegramChatIds(List<String> telegramChatId) {
        this.telegramChatIds = telegramChatId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getRealGatewayName() {
        return realGatewayName;
    }

    public void setRealGatewayName(String realGatewayName) {
        this.realGatewayName = realGatewayName;
    }
}
