package test.java;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.*;

public class FundPropertiesTest {

  FundProperties p = FundProperties.getInstance();

  @Test
  public void test() {
    assertEquals("target/classes/TestData",FundProperties.getProperty("inputDirectory",""));
    assertEquals("hasHeader changed to false",FundProperties.getProperty("hasHeader","false"),"true");
  }

}
