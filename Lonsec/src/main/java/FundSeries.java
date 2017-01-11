package main.java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FundSeries {

  private String fundCode;
  private String fundName;
  private Date returnDate;
  private double returnPercent;
  private double benchmarkReturnPercent;
  private double excess;
  private int rank;
  private static ArrayList<ExcessLookup> lookup;
  
  public FundSeries (String fundCode, Date returnDate, double returnPercent) {
    this.fundCode = fundCode;
    this.returnDate = returnDate;
    this.returnPercent = returnPercent;
  }
  
  public void setFundName(String fundName) {
    this.fundName = fundName;
  }
  
  public void setBenchmarkReturnPercent(double benchmarkReturnPercent) {
    this.benchmarkReturnPercent = benchmarkReturnPercent;
    this.excess = this.returnPercent - this.benchmarkReturnPercent;
  }
  
  public void setExcess(double excess) {
    this.excess = excess;
  }
  
  public void setRank(int rank) {
    this.rank = rank;
  }
  
  public String getFundCode() {
    return this.fundCode;
  }
  
  public String getFundName() {
    return this.fundName;
  }
  
  public Date getReturnDate() {
    return this.returnDate;
  }
  
  public String getFormattedReturnDate() {
    return formatDate(this.returnDate);
  }
  
  public double getReturnPercent() {
    return this.returnPercent;
  }
  
  public double getExcess() {
    return this.excess;
  }
  
  public int getRank() {
    return this.rank;
  }
  
  public String getLabel() {
    return lookupLabel(getExcess());
  }
  
  public FundSeries(String loadText) {
    String[] s = loadText.split(",");
    Date returnDate = null;
    double returnPercent = 0;
    if (s.length < 3) {
      // error: input row does not contain three values
    } else {
      returnDate = parseDate(s[1]);
      returnPercent = parsePercent(s[2]);
      if (returnDate != null) {
        this.fundCode = s[0];
        this.returnDate = returnDate;
        this.returnPercent = returnPercent;
      }
    }
  }
  
  public String toString() {
    return this.fundCode + "," + this.fundName + "," + getFormattedReturnDate() + "," 
      + this.returnPercent + "," + this.benchmarkReturnPercent+ "," + this.excess + "," + getLabel() + "," + this.rank;
  }

  public static HashMap<String, FundSeries> getFundSeries(String mode) {
    ArrayList<String> data = FundDataSource.getData("fundReturnSeries",mode);
    HashMap<String,FundSeries> result = new HashMap<String,FundSeries>();
    for (int i=0;i<data.size();i++) {
      FundSeries fs = new FundSeries(data.get(i));
      if (fs.getFundCode()!=null) {
        result.put(fs.getFundCode()+fs.getFormattedReturnDate(),fs);
      }
    }
    return result;    
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
  
  private static String lookupLabel(double excess) {
    String result = null;
    if (lookup==null) {
      String[] l = FundProperties.getProperty("excessLookup","").split(";");
      lookup = new ArrayList<ExcessLookup>();
      for (int i=0;i<l.length;i++) {
        lookup.add(new ExcessLookup(l[i]));
      }
    }
    boolean lt = true;
    for (int i = 0; i<lookup.size() && result==null;i++) {
      if (lookup.get(i).getExcess()==0) {
        lt = false;
      } else {
        if (lt && excess < lookup.get(i).getExcess()) {
          result = lookup.get(i).getLabel();
        } else if (!lt && excess > lookup.get(i).getExcess()) {
          result = lookup.get(i).getLabel();
        }
      }
    }
    return result!=null?result:"";
  }
  
  private String formatDate(Date d) {
    String result = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
      result = sdf.format(d);
    } catch (Exception e) {
      // not a date
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

class ExcessLookup {
  private double excess;
  private String label;
  
  public ExcessLookup (double excess, String label) {
    this.excess = excess;
    this.label = label;
  }

  public ExcessLookup (String row) {
    String[] ex = row.split(",");
    try {
      this.excess = Double.parseDouble(ex[0]);
      this.label = ex[1];
    } catch (Exception e) {
      this.label = "Error: " + row;
    }
  }

  public double getExcess() {
    return this.excess;
  }
  
  public String getLabel() {
    return this.label;
  }
  
}