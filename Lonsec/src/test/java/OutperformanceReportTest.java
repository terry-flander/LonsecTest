package test.java;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import main.java.*;

public class OutperformanceReportTest extends OutperformanceReport {

  @Before
  public void setUp() throws Exception {
    FundProperties.getInstance();
  }

  @Test
  public final void testSaveResults() {
    HashMap<String,FundSeries> fundSeriesI = FundSeries.getFundSeries("test");
    ArrayList<FundSeries> fundSeries = new ArrayList<FundSeries>(fundSeriesI.values());
    saveResults(fundSeries, "output", "test");
 }

  @Test
  public final void testFormatPercent() {
    assertEquals("12.34",formatPercent(12.34,2));
  }

  @Test
  public final void testRound() {
    assertTrue(round(1.2345678,2)==1.23);
    assertTrue(round(1.2345678,4)==1.2346);
    
  }

}
