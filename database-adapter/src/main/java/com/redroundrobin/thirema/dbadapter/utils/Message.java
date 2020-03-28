package com.redroundrobin.thirema.dbadapter.utils;

import java.util.List;

public class Message {
    private static String type = "alert";
    private int alertId;
    private int entityId;
    private int realDeviceId;
    private int realSensorId;
    private int currentThreshold;
    private int currentThresholdType;
    private List<String> telegramChatId;
    private List<String> telegramName;

    public String getType() {
        return type;
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

    public List<String> getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(List<String> telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public List<String> getTelegramName() {
        return telegramName;
    }

    public void setTelegramName(List<String> telegramName) {
        this.telegramName = telegramName;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
