package main.java;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.*;

public class FundDataSource {

  public static ArrayList<String> getData (String type, String mode) {
    if (mode.equals("test")) {
      return loadTest(type);
    } else {
      return loadFile(FundProperties.getProperty("inputDirectory",".") + "/" + FundProperties.getProperty(type + "FileName",""));
    }
  }
  
  public static PrintWriter saveData (String type, String mode) {
    if (mode.equals("test")) {
      return writeTest();
    } else {
      return writeFile(FundProperties.getProperty("outputDirectory",".") + "/" + FundProperties.getProperty(type + "FileName",""));
    }
  }
  
  private static PrintWriter writeTest() {
    PrintWriter result = new PrintWriter(System.out);
    return result;
  }

  private static PrintWriter writeFile(String fileName) {
    PrintWriter result = null;
    Path path = FileSystems.getDefault().getPath(fileName);

    try {
      result = new PrintWriter(Files.newBufferedWriter(path, java.nio.charset.StandardCharsets.UTF_8));
    } catch (IOException e) {
      // Export error
    }
    return result;
  }

  private static ArrayList<String> loadFile(String fileName) {
    ArrayList<String> result = null;

    Path path = FileSystems.getDefault().getPath(".", fileName);
    if (Files.exists(path)) {
      try {
        result = (ArrayList<String>) Files.readAllLines(path, Charset.defaultCharset() );
      } catch (IOException e) {
        System.out.println("Could not read load file: " + fileName);
      }
    } else {
      File f = new File("./");
      System.out.println("Load file not found: " + fileName + " looking from " + f.getAbsolutePath());
    }
    if (result.size() > 0 && FundProperties.getProperty("hasHeader","false").equals("true")) {
      result.remove(0);
    }

    return result;
  }

  private static ArrayList<String> loadTest(String fileName) {
    ArrayList<String> result = new ArrayList<String>();

    switch (fileName) {
    case "fund":
      result.add("fund1,fund 1,bm1");
      result.add("fund2,fund 2,bm2");
      result.add("fund3,fund 3,bm3");
      break;
 
    case "benchmark":
      result.add("bm1,bench 1");
      result.add("bm2,bench 2");
      break;
  
    case "fundReturnSeries":
      result.add("fund1,30/06/2016,1.107845");
      result.add("fund1,31/07/2016,0.707635");
      result.add("fund1,31/08/2016,0.420467");
      result.add("fund1,30/09/2016,-0.232219");
      result.add("fund1,31/10/2016,-1.317753");
      result.add("fund1,30/11/2016,-1.470981");
      break;
      
    case "benchmarkReturnSeries":
      result.add("bm1,31/05/2016,-0.04215");
      result.add("bm1,30/06/2016,1.309476");
      result.add("bm1,31/07/2016,0.730010");
      result.add("bm1,31/08/2016,0.420060");
      result.add("bm1,30/09/2016,-0.225320");
      result.add("bm1,31/10/2016,-1.292895");
      result.add("bm1,30/11/2016,-1.460981");
      break;
    
    }
    return result;

  }
  
  
}
