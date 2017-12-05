# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation for the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs) page.

See the [`load-scripts`](load-scripts/) directory on how to load the data set.

## Generating the validation data set

1. Follow the steps in the parent directory's README to set up the environment.

2. Generate the validation data set with the following parameters:

   ```bash
   java -cp target/cypher-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb -P readwrite_cypher--ldbc_driver_config--validation_parameter_creation.properties
   ```
