package com.redroundrobin.thirema.kafkadatacollector.models;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {
  Message message;

  @Before
  public void before() {
    message = new Message();
  }

  @Test
  public void getReqType() {
    message.setReqType("test");
    assertEquals("test", message.getReqType());
  }

  @Test
  public void getAlertId() {
    message.setAlertId(1);
    assertEquals(1, message.getAlertId());
  }

  @Test
  public void getEntityId() {
    message.setEntityId(1);
    assertEquals(1, message.getEntityId());
  }

  @Test
  public void getSensorType() {
    message.setSensorType("test");
    assertEquals("test", message.getSensorType());
  }

  @Test
  public void getDeviceId() {
    message.setDeviceId(1);
    assertEquals(1, message.getDeviceId());
  }

  @Test
  public void getDeviceName() {
    message.setDeviceName("name");
    assertEquals("name", message.getDeviceName());
  }

  @Test
  public void getRealDeviceId() {
    message.setRealDeviceId(1);
    assertEquals(1, message.getRealDeviceId());
  }

  @Test
  public void getRealSensorId() {
    message.setRealSensorId(1);
    assertEquals(1, message.getRealSensorId());
  }

  @Test
  public void getRealGatewayName() {
    message.setRealGatewayName("test");
    assertEquals("test", message.getRealGatewayName());
  }

  @Test
  public void getCurrentThreshold() {
    message.setCurrentThreshold(1);
    assertEquals(1, message.getCurrentThreshold());
  }

  @Test
  public void getCurrentThresholdType() {
    message.setCurrentThresholdType(1);
    assertEquals(1, message.getCurrentThresholdType());
  }

  @Test
  public void getCurrentValue() {
    message.setCurrentValue(1);
    assertEquals(1, message.getCurrentValue());
  }

  @Test
  public void getTelegramChatIds() {
    List<String> list = new ArrayList<>();
    message.setTelegramChatIds(list);
    assertEquals(list, message.getTelegramChatIds());
  }
}