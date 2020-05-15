package com.redroundrobin.thirema.kafkadatacollector.models;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
  private String reqType;
  private int alertId;
  private transient int entityId;
  private String sensorType;
  private int deviceId;
  private String deviceName;
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

  public String getReqType() {
    return reqType;
  }

  public int getAlertId() {
    return alertId;
  }

  public int getEntityId() {
    return entityId;
  }

  public String getSensorType() {
    return sensorType;
  }

  public int getDeviceId() {
    return deviceId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public int getRealDeviceId() {
    return realDeviceId;
  }

  public int getRealSensorId() {
    return realSensorId;
  }

  public String getRealGatewayName() {
    return realGatewayName;
  }

  public int getCurrentThreshold() {
    return currentThreshold;
  }

  public int getCurrentThresholdType() {
    return currentThresholdType;
  }

  public int getCurrentValue() {
    return currentValue;
  }

  public List<String> getTelegramChatIds() {
    return telegramChatIds;
  }

  public void setReqType(String reqType) {
    this.reqType = reqType;
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

  public void setTelegramChatIds(List<String> telegramChatIds) {
    this.telegramChatIds = telegramChatIds;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }
}
