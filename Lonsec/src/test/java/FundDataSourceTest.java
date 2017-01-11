package test.java;

import static org.junit.Assert.*;
import java.util.ArrayList;
import main.java.*;
import org.junit.Test;

public class FundDataSourceTest {

  ArrayList<String> data = FundDataSource.getData("fund","test");
  @Test
  public final void testGetData() {
    assertTrue(data.size()==3);
    assertEquals(data.get(0),"fund1,fund 1,bm1");
  }

  @Test
  public final void testSaveData() {
    assertNotNull(FundDataSource.saveData("output","test"));
  }

}
