package test.java;

import static org.junit.Assert.*;
import main.java.*;

import org.junit.Test;

public class BenchmarkTest {

  Benchmark b = new Benchmark("bm1,Benchmark Test");

  @Test
  public void test() {
    assertEquals(b.getBenchmarkCode(),"bm1");
    assertEquals(b.getBenchmarkName(),"Benchmark Test");
    assertEquals(b.toString(),"bm1,Benchmark Test,seriesCount=0");
  }

}
