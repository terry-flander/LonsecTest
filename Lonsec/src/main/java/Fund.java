package main.java;

import java.util.ArrayList;
import java.util.HashMap;

public class Fund {
  private String fundCode;
  private String fundName;
  private String benchmarkCode;
  
  public Fund(String fundCode, String fundName, String benchmarkCode) {
    this.fundCode = fundCode;
    this.fundName = fundName;
    this.benchmarkCode = benchmarkCode;
  }
  
  public Fund(String line) {
    try {
      String[] s = line.split(",");
      if (s.length < 3) {
          // error: input row does not contain three values
      } else {
        this.fundCode = s[0].trim();
        this.fundName = s[1].trim();
        this.benchmarkCode = s[2].trim();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
   public String getFundCode () {
    return this.fundCode;
  }
  
  public String getFundName() {
    return this.fundName;
  }
  
  public String getBenchmarkCode() {
    return this.benchmarkCode;
  }  
  
  public String toString() {
    return this.fundCode + "," + this.fundName + "," + this.benchmarkCode;
  }
 
  public static HashMap<String,Fund> getFundTable (String mode) {
    ArrayList<String> data = FundDataSource.getData("fund",mode);
    HashMap<String,Fund> result = new HashMap<String,Fund>();
    for (int i=0;i<data.size();i++) {
      Fund f = new Fund(data.get(i));
      if (f.getFundCode()!=null) {
        result.put(f.getFundCode(), f);
      }
    }
    return result;
  }
}
