
-----------------------------------
Download and install LDBC Driver:
-----------------------------------

You can find the detailed information about this task here:
https://github.com/ldbc/ldbc_driver

After that, you can install the jar file of ldbc_driver to the local
maven repository. Go to the root directory of ldbc_driver, and run:
   $ mvn clean install -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7 -DskipTests


-----------------------------------
Compile Db class for JDBC Database:
-----------------------------------

Run
$ mvn clean package


--------------------------------------
Running the driver against Postgres:
--------------------------------------

You have to update the paths in the run.sh script, and use it for
running the benchmark.  Before that, please update the 3 configuration
files with your options. This process is explained here:
https://github.com/ldbc/ldbc_driver/wiki
