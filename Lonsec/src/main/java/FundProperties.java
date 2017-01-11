package main.java;

import java.io.IOException;
import java.util.Properties;

public class FundProperties {

  static Properties prop;
  static FundProperties instance;
  static String propertiesFileName = "../../fund.properties";
  
  public static FundProperties getInstance() {
    if (instance==null) {
      instance = new FundProperties();
    }
    return instance;
  }

  public FundProperties() {
      
    try {
      prop = new Properties();
      prop.load(getClass().getResourceAsStream(propertiesFileName));
    } catch (IOException e) {
     // some problem loading properties file
      System.out.println("FundProperties: Unable to load Properties file.");
    }
  }
  
  public static String getProperty(String propertyName, String defaultValue) {
    String result = null;
    if (prop!=null) {
      result = prop.getProperty(propertyName);
    } else {
      result = defaultValue;
    }
    return result;
  }
  
}
