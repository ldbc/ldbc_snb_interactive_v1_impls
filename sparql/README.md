# LDBC SNB SPARQL implementation

[SPARQL 1.1](https://www.w3.org/TR/sparql11-query/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Loading the data set

TODO

See the [`load-scripts`](load-scripts/) directory on how to load the data set.

## Running the implementation

First, follow the steps in the parent directory's [README](../README.md) to set up the environment.

To create the validation data set, run:

```bash
java -cp target/sparql-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.SparqlBiDb \
  -P sparql-create_validation_parameters.properties
```

To validate the database, run:

```bash
java -cp target/sparql-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.SparqlBiDb \
  -P sparql-validate_db.properties
```

To execute the benchmark, run:

```bash
java -cp target/sparql-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.SparqlBiDb \
  -P sparql-benchmark.properties
```
