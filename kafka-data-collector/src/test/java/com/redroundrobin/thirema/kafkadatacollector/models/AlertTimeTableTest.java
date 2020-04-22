package com.redroundrobin.thirema.kafkadatacollector.models;

import static org.junit.Assert.*;
import org.junit.Test;

public class AlertTimeTableTest {

  @Test
  public void NoMemberInside() {
    AlertTimeTable alertTimeTable = new AlertTimeTable();
    assertEquals(0, alertTimeTable.size());
  }

  @Test
  public void verifyAlertMethodMultipleItem() {
    AlertTimeTable alertTimeTable = new AlertTimeTable();
    alertTimeTable.verifyAlert(500);
    alertTimeTable.verifyAlert(500);
    alertTimeTable.verifyAlert(501);
    alertTimeTable.verifyAlert(501);
    alertTimeTable.verifyAlert(503);
    alertTimeTable.verifyAlert(504);
    alertTimeTable.verifyAlert(504);
    alertTimeTable.verifyAlert(505);
    alertTimeTable.verifyAlert(506);
    assertTrue(alertTimeTable.verifyAlert(503));
  }

  @Test
  public void verifyAlertMethodWithoutItems() {
    AlertTimeTable alertTimeTable = new AlertTimeTable();
    alertTimeTable.verifyAlert(1);
    alertTimeTable.verifyAlert(2);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(4);
    assertFalse(alertTimeTable.verifyAlert(5));
  }

  @Test
  public void CheckSizeMethod() {
    AlertTimeTable alertTimeTable = new AlertTimeTable();
    alertTimeTable.verifyAlert(1);
    alertTimeTable.verifyAlert(2);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(4);
    assertEquals(3, alertTimeTable.size());
  }

  @Test
  public void CheckClearMethod() {
    AlertTimeTable alertTimeTable = new AlertTimeTable();
    alertTimeTable.verifyAlert(1);
    alertTimeTable.verifyAlert(2);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(3);
    alertTimeTable.verifyAlert(4);
    alertTimeTable.clear();
    alertTimeTable.verifyAlert(5);
    assertEquals(1, alertTimeTable.size());
  }

}