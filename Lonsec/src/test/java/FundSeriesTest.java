package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import main.java.*;

public class FundSeriesTest {

  @Before
  public void setUp() throws Exception {
    FundProperties.getInstance();
  }
  
  @Test
  public  void testSetFundName() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    fs.setFundName("Fred");
    assertEquals(fs.getFundName(),"Fred");
  }

  @Test
  public  void testSetExcess() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    fs.setExcess(1.2345);
    assertTrue(fs.getExcess()==1.2345);
  }

  @Test
  public  void testSetRank() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    fs.setRank(5);
    assertTrue(fs.getRank()==5);
  }

  @Test
  public  void testGetFundCode() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    assertEquals("fund1",fs.getFundCode());
  }

  @Test
  public  void testGetFormattedReturnDate() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    assertEquals("31/01/2016",fs.getFormattedReturnDate());
  }

  @Test
  public  void testGetReturnPercent() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    assertTrue(fs.getReturnPercent()==1.23456);
  }

  @Test
  public  void testGetLabel() {
    FundProperties.getInstance();
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    fs.setExcess(1.01);
    assertEquals("out performed",fs.getLabel());
    fs.setExcess(1);
    assertEquals("",fs.getLabel());
    fs.setExcess(-1);
    assertEquals("",fs.getLabel());
    fs.setExcess(-1.01);
    assertEquals("under performed",fs.getLabel());
  }

  @Test
  public  void testToString() {
    FundSeries fs = new FundSeries("fund1,31/01/2016,1.23456");
    fs.setFundName("Fred");
    assertEquals("fund1,Fred,31/01/2016,1.23456,0.0,0.0,,0",fs.toString());
  }

}
