![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
LDBC SNB implementations
------------------------

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

:warning: Implementations in this repository are not audited.

## Implementations

Each project has its own README.

* [Cypher (Neo4j driver) implementation](cypher/)
* [PostgreSQL implementation](postgres/)
* [SPARQL implementation](sparql/)

The [Sparksee implementation](https://github.com/DAMA-UPC/ldbc-sparksee) is maintained in a separate repository.

## Status

The queries in this repository are work-in-progress.

| query                 | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-10.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries-opencypher/bi-1.cypher) | [02](cypher/queries-opencypher/bi-2.cypher) | [03](cypher/queries-opencypher/bi-3.cypher) | [04](cypher/queries-opencypher/bi-4.cypher) | [05](cypher/queries-opencypher/bi-5.cypher) | [06](cypher/queries-opencypher/bi-6.cypher) | [07](cypher/queries-opencypher/bi-7.cypher) | [08](cypher/queries-opencypher/bi-8.cypher) | [09](cypher/queries-opencypher/bi-9.cypher) | [10](cypher/queries-opencypher/bi-10.cypher) |
| PostgreSQL     | [01](postgres/queries/bi/query1.sql) | [02](postgres/queries/bi/query2.sql) | [03](postgres/queries/bi/query3.sql) | [04](postgres/queries/bi/query4.sql) | [05](postgres/queries/bi/query5.sql) | [06](postgres/queries/bi/query6.sql) | [07](postgres/queries/bi/query7.sql) | [08](postgres/queries/bi/query8.sql) | [09](postgres/queries/bi/query9.sql) | [10](postgres/queries/bi/query10.sql) |
| SPARQL         | [01](sparql/queries/bi-1.sparql) | [02](sparql/queries/bi-2.sparql) | [03](sparql/queries/bi-3.sparql) | [04](sparql/queries/bi-4.sparql) | [05](sparql/queries/bi-5.sparql) | [06](sparql/queries/bi-6.sparql) | [07](sparql/queries/bi-7.sparql) | [08](sparql/queries/bi-8.sparql) | [09](sparql/queries/bi-9.sparql) | [10](sparql/queries/bi-10.sparql) |

| query                 | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-14.pdf) | [15](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-15.pdf) | [16](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-16.pdf) | [17](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-17.pdf) | [18](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-18.pdf) | [19](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-19.pdf) | [20](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-20.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [11](cypher/queries-opencypher/bi-11.cypher) | [12](cypher/queries-opencypher/bi-12.cypher) | [13](cypher/queries-opencypher/bi-13.cypher) | [14](cypher/queries-opencypher/bi-14.cypher) | [15](cypher/queries-opencypher/bi-15.cypher) | [16](cypher/queries-opencypher/bi-16.cypher) | [17](cypher/queries-opencypher/bi-17.cypher) | [18](cypher/queries-opencypher/bi-18.cypher) | [19](cypher/queries-opencypher/bi-19.cypher) | [20](cypher/queries-opencypher/bi-20.cypher) |
| PostgreSQL     | [11](postgres/queries/bi/query11.sql) | [12](postgres/queries/bi/query12.sql) | [13](postgres/queries/bi/query13.sql) | [14](postgres/queries/bi/query14.sql) | [15](postgres/queries/bi/query15.sql) | [16](postgres/queries/bi/query16.sql) | [17](postgres/queries/bi/query17.sql) | [18](postgres/queries/bi/query18.sql) | [19](postgres/queries/bi/query19.sql) | [20](postgres/queries/bi/query20.sql) |
| SPARQL         | [11](sparql/queries/bi-11.sparql) | [12](sparql/queries/bi-12.sparql) | [13](sparql/queries/bi-13.sparql) | [14](sparql/queries/bi-14.sparql) | [15](sparql/queries/bi-15.sparql) | [16](sparql/queries/bi-16.sparql) | [17](sparql/queries/bi-17.sparql) | [18](sparql/queries/bi-18.sparql) | [19](sparql/queries/bi-19.sparql) | [20](sparql/queries/bi-20.sparql) |

| query                 | [21](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-21.pdf) | [22](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-22.pdf) | [23](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-23.pdf) | [24](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-24.pdf) | [25](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-25.pdf) |
| --------------------- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [21](cypher/queries-opencypher/bi-21.cypher) | [22](cypher/queries-opencypher/bi-22.cypher) | [23](cypher/queries-opencypher/bi-23.cypher) | [24](cypher/queries-opencypher/bi-24.cypher) | [25](cypher/queries-opencypher/bi-25.cypher) |
| PostgreSQL     | [21](postgres/queries/bi/query21.sql) | [22](postgres/queries/bi/query22.sql) | [23](postgres/queries/bi/query23.sql) | [24](postgres/queries/bi/query24.sql) | [25](postgres/queries/bi/query25.sql) |
| SPARQL         | [21](sparql/queries/bi-21.sparql) | [22](sparql/queries/bi-22.sparql) | [23](sparql/queries/bi-23.sparql) | [24](sparql/queries/bi-24.sparql) | [25](sparql/queries/bi-25.sparql) |


## User's guide

1. Grab the driver source code from: https://github.com/ldbc/ldbc_snb_driver.
2. Install the driver artifact to the local Maven repository:

   ```bash
   mvn clean install -DskipTests
   ```

3. Navigate to the root of this repository and generate the JAR files for the implementations:

   ```bash
   mvn clean package -DskipTests
   ```

4. For each implementation, it is possible to (1) create validation parameters, (2) validate against an existing validation parameters, and (3) run the benchmark. Set the parameters according to your system configuration in the appropriate `.properties` file and run the driver with one of the following scripts:

   ```bash
   # BI workload
   ./bi-create-validation-parameters.sh
   ./bi-validate.sh
   ./bi-benchmark.sh
   # Interactive workload
   ./interactive-create-validation-parameters.sh
   ./interactive-validate.sh
   ./interactive-benchmark.sh
   ```

For more details, on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_driver/wiki).

### Generating small test data tests

To generate small data sets, use scale factor 1 (SF1) with the persons and years set according to this template:

```
ldbc.snb.datagen.generator.scaleFactor:snb.interactive.1

ldbc.snb.datagen.generator.numPersons:50
ldbc.snb.datagen.generator.numYears:1
ldbc.snb.datagen.generator.startYear:2010

ldbc.snb.datagen.generator.numThreads:1
ldbc.snb.datagen.serializer.outputDir:./test_data/

ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>InvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonActivitySerializer
```
