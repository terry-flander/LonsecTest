# Lonsec Program Test -- Terry Flander

Description of solution.

## Installation

Install from github

Using MAVEN to install, cd into top of source directory:

* <installDirecory>/LonsecCodingTest

mvn clean package

The application will compile and the tests will run automatically. Success is indicated by Tests running without error. The final test is for the completed report using the test data from the coding test document. This data is built into the FundDataSource class and is used by unit tests as well. No external data is required for these tests to run.

## Configuration

The application configuration is completely external to the source code and is contained in the file resources/fund.properties. This configuration file is used by the FundProperties class and allows configuration of the following properties. These properties and their use are defined in the file itself for reference while making changes.

### Input file controls:

* inputDirectory – the directory under which all input files will be located. This directory name will be prepended to all the logical fine names described below.
* hasHeader – if set to true then first line of input files contains a header row which will be ignored. Otherwise will process all input lines.
* fundFileName – the file name of the CSV file containing the Fund data. Logical name of this file is “fund”.
* benchmarkFileName – the file name of the CSV file containing the Benchmark Data. Logical name of this file is “benchmark”.
* fundReturnSeriesFileName – the file name of the CSV file containing the Fund Return Series data. Logical name of the file is “fundReturnSeries”.
* benchmarkReturnSeriesFileName – the file name of the CSV file containing the Benchmark Return Series data. Logical name of this file is “benchmarkReturnSeries”.

### Output file controls:

* outputDirectory – the directory into which the output file will be created. This directory name will be prepended to  the logical name indicated.
outputFileName – the file which will contain the output from running this project. Logical name of this file is “output”

### Excess Lookup:

* excessLookup – a specially formatted string defining the values of the Excess Text lookup class used for formatting output. The definition is as follows:

•	Semicolon separated (;) list of comma separated (,) limit/description pairs
•	Must be in sequential order from low to high
•	Limit value of 0 is turning point for significance of limit and must be included 
•	i.e. before 0, the limit is taken to mean less than (<) that value and after 0, the limit is taken to mean greater than (>) that value. Therefore the limits are exclusive of their value.  A limit of -1 means < -1 and  a limit of 1 means > 1. The limits are assumed to be double values and so may be fractional.

# Testing

Testing using JUnit

# Running

mvn exec:java

This will run the OutperformanceReport main class and product the desired output in the target directory using the provided test files. All test files and the output file will be located in:

./target/classes/TestData

If you wish you can compare the contents of:

./target/classes/TestData/monthlyOutperformance.csv

with

./src/main/resources/TestData/monthlyOutperformance.csv

which should be identical.

