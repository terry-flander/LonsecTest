# Lonsec Program Test -- Terry Flander

The application OutperformanceReport is designed to take as its input fund and benchmark return series for selected funds over a period of months and produce a report comparing the actual return of each against a selected benchmark. From this comparison a performance excess is calculated. Additionally, funds are ranked by their actual returns on a monthly basis

Source information from the input files is persisted in FundReturnSeries which serves as the source for the output report. Additional calculations could be added to this class which would make them available to OutperformanceReport or other similar reports.

The data source has also been abstracted into FundDataSource which in addition to providing access to the external CSV files, provides an alternative source of internal data. This class could be extended to source data from external resources such as data bases, application APIs , or Soap/REST requests.

Please download and read Outperformance Report - Introduction.pdf for a description of the applicaiton, as well as installation, configuration and execution instructions.

## Quick start
Install
* Download from github
* Unpack in work directory
* cd to &lt;work&gt;/LonsecTest-master/Lonsec

Build
* mvn clean package

Run
* mvn exec:java

This will run the OutperformanceReport main class and produce an output CSV the target directory using the provided test files. All test files and the output file will be located in:

&lt;work&gt;/LonsecTest-master/Lonsec/target/classes/TestData

