package main.java;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class OutperformanceReport {

  public static void main(String[] args) {
    
    String mode = args!=null&&args.length>0?args[0]:"file";
    
    // Initialise Configuration singleton
    FundProperties.getInstance();
    
    // Load Funds Table
    HashMap<String,Fund> funds = Fund.getFundTable(mode);
    
    // Load Benchmark Table including Benchmark Return Series
    HashMap<String,Benchmark> benchmarks = Benchmark.getBenchmarkTable(mode);
    
    // Load Fund Return Series
    HashMap<String,FundSeries> fundSeriesI = FundSeries.getFundSeries(mode);
    
    // Move HashMap into ArrayList for Processing and Output
    ArrayList<FundSeries> fundSeries = new ArrayList<FundSeries>(fundSeriesI.values());
    for (int i=0;i<fundSeries.size(); i++) {
      FundSeries fs = fundSeries.get(i);
      String benchmarkCode = null;
      
      // Lookup Fund and add Fund Name;
      if (funds.containsKey(fs.getFundCode())) {
        fs.setFundName(funds.get(fs.getFundCode()).getFundName());
        benchmarkCode = funds.get(fs.getFundCode()).getBenchmarkCode();
      }
      
      // Use Fund Benchmark to lookup Benchmark for this Date
      if (benchmarkCode!=null && benchmarks.containsKey(benchmarkCode)) {
        fs.setBenchmarkReturnPercent(benchmarks.get(benchmarkCode).getBenchmarkReturnByDate(fs.getReturnDate()));
      }
    }
    
    // Sort for Ranking and Date output
    java.util.Collections.sort(fundSeries, new Comparator<FundSeries>() {
      @Override
      public int compare(FundSeries p1, FundSeries p2) {
        return p2.getReturnDate().compareTo(p1.getReturnDate())!=0?
            p2.getReturnDate().compareTo(p1.getReturnDate()):
            p1.getExcess()>p2.getExcess()?-1:1;
      }});
    
    // Set Rank For each Date
    Date lastDate = null;
    int rank = 0;
    for (int i=0;i<fundSeries.size();i++) {
      FundSeries fs = fundSeries.get(i);
      if (lastDate==null || fs.getReturnDate().equals(lastDate)) {
        rank++;
      } else {
        rank = 1;
      }
      fs.setRank(rank);
      lastDate = fs.getReturnDate();
    }

    saveResults(fundSeries, "output", mode);
  }
  
  public static void saveResults(ArrayList<FundSeries> fundSeries, String type, String mode) {
    // Output Results
    PrintWriter pw = null;
    try {
      pw = FundDataSource.saveData(type,mode);
      pw.println("Fund Code,Fund Name,Date,Excess,Out Performance,Return,Rank");
      for (int i=0;i<fundSeries.size();i++) {
        FundSeries fs = fundSeries.get(i);
        pw.println(fs.getFundCode() + ","
            + fs.getFundName() + ","
            + fs.getFormattedReturnDate() + ","
            + formatPercent(fs.getExcess(),2) + ","
            + fs.getLabel() + ","
            + formatPercent(fs.getReturnPercent(),2) + ","
            + fs.getRank()
            );
      }
      pw.close();
    } catch (Exception e) {
      // report exception
    }
    
  }
  
  public static String formatPercent(double value, int places) {
    return String.format("%.2f",round(value ,places));
  }
    

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_DOWN);
    return bd.doubleValue();
  }
 
}
