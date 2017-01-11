package main.java;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class Benchmark {
  private String benchmarkCode;
  private String benchmarkName;
  private HashMap<Date,BenchmarkSeries> benchmarkSeries;
  
  public Benchmark(String benchmarkCode, String benchmarkName) {
    this.benchmarkCode = benchmarkCode;
    this.benchmarkName = benchmarkName;
    this.benchmarkSeries = new HashMap<Date,BenchmarkSeries>();
  }
  
  public Benchmark(String line) {
    String[] s = line.split(",");
    if (s.length < 2) {
        // error: input row does not contain two values
    } else {
      this.benchmarkCode = s[0].trim();
      this.benchmarkName = s[1].trim();
      this.benchmarkSeries = new HashMap<Date,BenchmarkSeries>();
    }
  }
  
  public String getBenchmarkCode() {
    return this.benchmarkCode;
  }  
  
  public String getBenchmarkName() {
    return this.benchmarkName;
  }
  
  public double getBenchmarkReturnByDate(Date d) {
    double result = 0;
    if (this.benchmarkSeries.containsKey(d)) {
      result = this.benchmarkSeries.get(d).getReturnPercent();
    }
    return result;
  }
  
  public static HashMap<String,Benchmark> getBenchmarkTable(String mode) {
    ArrayList<String> data = FundDataSource.getData("benchmark",mode);
    HashMap<String,Benchmark> result = new HashMap<String,Benchmark>();
    for (int i=0;i<data.size();i++) {
      Benchmark b = new Benchmark(data.get(i));
      if (b.getBenchmarkCode()!=null) {
        result.put(b.getBenchmarkCode(), b);
      }
    }
    // Add Benchmark Return Series to Benchmark table
    data = FundDataSource.getData("benchmarkReturnSeries",mode);
    for (Benchmark b : result.values()) {
      b.loadSeries(data);
    }
    return result;
  }
  
  public void loadSeries(List<String> loadText) {
    for (int i=0;i<loadText.size();i++) {
      String[] s = loadText.get(i).split(",");
      Date returnDate = null;
      double returnPercent = 0;
      if (s.length < 3) {
        // error: input row does not contain three values
      } else if (s[0].equals(this.benchmarkCode)) {
        returnDate = parseDate(s[1]);
        returnPercent = parsePercent(s[2]);
        if (returnDate != null) {
          BenchmarkSeries bs = new BenchmarkSeries(returnDate, returnPercent);
          this.benchmarkSeries.put(bs.getReturnDate(),bs);
        }
      }
    }
  }
  
  public String toString() {
    return this.benchmarkCode + "," + this.benchmarkName + ",seriesCount=" + String.valueOf(benchmarkSeries.size());
  }

  
  private Date parseDate(String d) {
    Date result = null;
    String[] formats = { "dd/MM/yyyy", "yyyy-MM-dd" };
    for (int i=0; i<formats.length && result == null;i++) {
      SimpleDateFormat sdf = new SimpleDateFormat(formats[i]);
      try {
        result = sdf.parse(d);
      } catch (Exception e) {
        // not a date
      }
    }
    return result;    
  }
  
  private double parsePercent(String d) {
    double result = 0.0;
    try {
      result = Double.parseDouble(d);
    } catch (Exception e) {
      // not a double
    }
    return result;
  }
  
}

class BenchmarkSeries {
  private Date returnDate;
  private double returnPercent;
  
  public BenchmarkSeries(Date returnDate, double returnPercent) {
    this.returnDate = returnDate;
    this.returnPercent = returnPercent;
  }
  
  public Date getReturnDate() {
    return this.returnDate;
  }
  
  public double getReturnPercent() {
    return this.returnPercent;
  }
}

