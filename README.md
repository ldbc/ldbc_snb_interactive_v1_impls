![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen_hadoop/images/ldbc-logo.png)
# LDBC SNB Interactive workload implementations

Reference implementations for the Interactive workload of the LDBC Social Network Benchmark ([specification](https://ldbcouncil.org/ldbc_snb_docs/)).
To get the reference implementations for the BI workload, visit the [`ldbc_snb_bi` repository](https://github.com/ldbc/ldbc_snb_bi/).

## Warnings

Some important notes for using this repository:

:warning: The goal of the implementations in this repository is to serve as **reference implementations** which other implementations can cross-validated against. Therefore, our primary objective was readability and not absolute performance when formulating the queries.

:warning: SNB data sets of **different scale factors require different configurations** for the benchmark runs. Therefore, make sure you use the correct update interleave value and query frequencies provided in the [`sf-properties` directory](sf-properties/)*

:warning: The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Otherwise, repeated updates would insert duplicate entries.
## Implementations

Each implementation has its own README:

* [Neo4j (Cypher) implementation](cypher/)
* [PostgreSQL (SQL) implementation](postgres/)

The [Sparksee implementation](https://github.com/DAMA-UPC/ldbc-sparksee) is maintained in a separate repository.

## Test data sets

See the [documentation on generating the test data set](test-data.md).

## User's guide

1. Run the build script to generate the JAR files for the implementations:

   ```bash
   ./build.sh
   ```

2. For each implementation, it is possible to (1) create validation parameters, (2) validate against an existing validation parameters, and (3) run the benchmark. In the directory of the implemenetation, set the parameters according to your system configuration and [scale factor](sf-properties/) in the `.properties` file and run the driver with one of the following scripts:

   ```bash
   driver/create-validation-parameters.sh
   driver/validate.sh
   driver/benchmark.sh
   ```

For more details, on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_driver/wiki).

