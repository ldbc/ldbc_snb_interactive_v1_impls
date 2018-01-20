![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
LDBC SNB implementations
------------------------

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

:warning: Implementations in this repository are work-in-progress and are neither validated nor audited. Therefore, they should not be considered as official implementations until they are published in a peer-reviewed paper (planned for Q2 2018).

## Implementations

Each project has its own README:

* [Cypher (Neo4j driver) implementation](cypher/)
* [PostgreSQL implementation](postgres/)
* [SPARQL implementation](sparql/)

## Status

The queries in this repository are work-in-progress.

| query                 | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-10.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/bi-1.cypher) | [02](cypher/queries/bi-2.cypher) | [03](cypher/queries/bi-3.cypher) | [04](cypher/queries/bi-4.cypher) | [05](cypher/queries/bi-5.cypher) | [06](cypher/queries/bi-6.cypher) | [07](cypher/queries/bi-7.cypher) | [08](cypher/queries/bi-8.cypher) | [09](cypher/queries/bi-9.cypher) | [10](cypher/queries/bi-10.cypher) |
| PostgreSQL     | [01](postgres/queries/bi/query1.sql) | [02](postgres/queries/bi/query2.sql) | [03](postgres/queries/bi/query3.sql) | [04](postgres/queries/bi/query4.sql) | [05](postgres/queries/bi/query5.sql) | [06](postgres/queries/bi/query6.sql) | [07](postgres/queries/bi/query7.sql) | [08](postgres/queries/bi/query8.sql) | [09](postgres/queries/bi/query9.sql) | [10](postgres/queries/bi/query10.sql) |
| Sparksee       |  |  |  |  |  |  |  |  |  |  |
| SPARQL         | [01](sparql/queries/bi-1.sparql) | [02](sparql/queries/bi-2.sparql) | [03](sparql/queries/bi-3.sparql) | [04](sparql/queries/bi-4.sparql) | [05](sparql/queries/bi-5.sparql) | [06](sparql/queries/bi-6.sparql) | [07](sparql/queries/bi-7.sparql) | [08](sparql/queries/bi-8.sparql) | [09](sparql/queries/bi-9.sparql) | [10](sparql/queries/bi-10.sparql) |

| query                 | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-14.pdf) | [15](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-15.pdf) | [16](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-16.pdf) | [17](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-17.pdf) | [18](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-18.pdf) | [19](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-19.pdf) | [20](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-20.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [11](cypher/queries/bi-11.cypher) | [12](cypher/queries/bi-12.cypher) | [13](cypher/queries/bi-13.cypher) | [14](cypher/queries/bi-14.cypher) | [15](cypher/queries/bi-15.cypher) | [16](cypher/queries/bi-16.cypher) | [17](cypher/queries/bi-17.cypher) | [18](cypher/queries/bi-18.cypher) | [19](cypher/queries/bi-19.cypher) | [20](cypher/queries/bi-20.cypher) |
| PostgreSQL     | [11](postgres/queries/bi/query11.sql) | [12](postgres/queries/bi/query12.sql) | [13](postgres/queries/bi/query13.sql) | [14](postgres/queries/bi/query14.sql) | [15](postgres/queries/bi/query15.sql) | [16](postgres/queries/bi/query16.sql) | [17](postgres/queries/bi/query17.sql) | [18](postgres/queries/bi/query18.sql) | [19](postgres/queries/bi/query19.sql) | [20](postgres/queries/bi/query20.sql) |
| Sparksee       |  |  |  |  |  |  |  |  |  |  |
| SPARQL         | [11](sparql/queries/bi-11.sparql) | [12](sparql/queries/bi-12.sparql) | [13](sparql/queries/bi-13.sparql) | [14](sparql/queries/bi-14.sparql) | [15](sparql/queries/bi-15.sparql) | [16](sparql/queries/bi-16.sparql) | [17](sparql/queries/bi-17.sparql) | [18](sparql/queries/bi-18.sparql) | [19](sparql/queries/bi-19.sparql) | [20](sparql/queries/bi-20.sparql) |

| query                 | [21](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-21.pdf) | [22](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-22.pdf) | [23](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-23.pdf) | [24](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-24.pdf) | [25](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-25.pdf) |
| --------------------- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [21](cypher/queries/bi-21.cypher) | [22](cypher/queries/bi-22.cypher) | [23](cypher/queries/bi-23.cypher) | [24](cypher/queries/bi-24.cypher) | [25](cypher/queries/bi-25.cypher) |
| PostgreSQL     | [21](postgres/queries/bi/query21.sql) | [22](postgres/queries/bi/query22.sql) | [23](postgres/queries/bi/query23.sql) | [24](postgres/queries/bi/query24.sql) | [25](postgres/queries/bi/query25.sql) |
| Sparksee       |  |  |  |  |  |
| SPARQL         | [21](sparql/queries/bi-21.sparql) | [22](sparql/queries/bi-22.sparql) | [23](sparql/queries/bi-23.sparql) | [24](sparql/queries/bi-24.sparql) | [25](sparql/queries/bi-25.sparql) |


## User's guide

Download and install the LDBC SNB driver:

You can find the detailed information about this task here: <https://github.com/ldbc/ldbc_snb_driver>

After that, you can install the JAR file of ldbc_snb_driver to the local Maven repository. Go to the root directory of ldbc_snb_driver, and run:

```bash
mvn clean install -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7 -DskipTests
```

## Generating the validation data set

1. Grab the driver source code from: https://github.com/ldbc/ldbc_snb_driver
2. Install the driver artifact to the local Maven repository:

   ```bash
   mvn clean install -DskipTests
   ```

3. Navigate to the root of this repository and generate the shaded JAR files for the implementations:

   ```bash
   mvn clean package -DskipTests
   ```

4. Set the parameters according to your system configuration and run the driver in validation generation mode. For more details, refer to the README of the implementation-specific subprojects.

For more details, on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_driver/wiki).
