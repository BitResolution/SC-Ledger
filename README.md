SC-Ledger
==========

**ARCHIVED** - A tool for processing flat file based stock data

Building The Application
------------------------

You must have:
* Maven 2.2.0 or greater
* Java 6 or higher

Tne project can be built by executing mvn:package this produces an binary distribution 'sc-ledger-<version>-dist.zip'.
Unzipping the distribution zip will create a folder containing the executable jar and associated files.

Alternatively from your IDE run the main method in the Bootstrap class with an appropriate argument (see Running The
Application below).

Running The Application
-----------------------

The application works by executing a groovy script containing commands in a pseudo-DSL (internal DSL), the script
location is provided as an argument to the application.

Step by step:
* make the folder extracted from the distribution zip the present working directory
* execute 'java -jar sc-ledger.jar <scriptfile>

where <scriptfile> is the path to the script. The application comes with one script based on the specifications of
the programming challenge, which is located in the scripts directory. To run the application with this script therefore
simply execute

java -jar sc-ledger.jar scripts/test-questions.groovy

If you are running the application via your IDE simply use the script location as the argument to the main method.

Improvements
------------

* Complete the questions fully, especially summarising stock positions
* Fix the errors parsing certain entry lines in the report files
* Formatting the output of responses, e.g. table rather than a list of results
* Improve the logging - both quantity and quality need increasing
* The modelling/encapsulation of the Entries of a Report, e.g. a Stock class
* Probably as a consequence of the above the data structure used to hold the Reports in the Ledger would need refactoring,
probably to also include a more efficient query by dates
* Perhaps embedded a fast small footprint database to perform queries - the data seems relational and they are designed for
such a purpose
* Extend the DSL like feature to more expressive statments
