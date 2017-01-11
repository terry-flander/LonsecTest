package test.java;

import static org.junit.Assert.*;
import main.java.*;

import org.junit.Test;

public class FundTest {

  Fund f = new Fund("Fund1, Testing Fund 1,Bench1");

  @Test
  public void test() {
    assertEquals(f.getFundCode(),"Fund1");
    assertEquals(f.getFundName(),"Testing Fund 1");
    assertEquals(f.getBenchmarkCode(),"Bench1");
    assertEquals(new Fund("Fund1, Testing Fund 1,Bench1").toString(),"Fund1,Testing Fund 1,Bench1");
    assertEquals(new Fund(",,Bench1").toString(),",,Bench1");
    assertTrue("Input string does not contain 3 entries.",new Fund(",").getFundCode()==null);
  }

}
