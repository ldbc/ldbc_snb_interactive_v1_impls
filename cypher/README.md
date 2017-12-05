# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation for the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs) page.

See the [`load-scripts`](load-scripts/) directory on how to load the data set.

## Generating the validation data set

1. Grab the driver source code from: https://github.com/ldbc/ldbc_snb_driver
2. Install the driver artifact to the local Maven repository:

   ```bash
   mvn clean install -DskipTests
   ```

3. Go to the LDBC implementations directory (i.e. the parent of this directory) and generate that shaded JAR file:

   ```bash
   mvn clean package -DskipTests
   ```

4. Set the parameters according to your system configuration and generate the validation data set:

   ```bash
   java -cp target/cypher-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb -P readwrite_cypher--ldbc_driver_config--validation_parameter_creation.properties
   ```
