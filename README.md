# LDBC SNB implementations

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

## Implementations

Each project has its own README:

* [PostgreSQL implementation](postgres/)
* [Cypher (Neo4j driver) implementation](cypher/)

## Status

The queries in this repository are work-in-progress.

| query                 | [01](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-01.yaml) | [02](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-02.yaml) | [03](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-03.yaml) | [04](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-04.yaml) | [05](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-05.yaml) | [06](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-06.yaml) | [07](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-07.yaml) | [08](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-08.yaml) | [09](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-09.yaml) | [10](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-10.yaml) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [OK](cypher/queries/bi-1.cypher) | [OK](cypher/queries/bi-2.cypher) | [WIP](cypher/queries/bi-3.cypher) | [?](cypher/queries/bi-4.cypher) | [OK](cypher/queries/bi-5.cypher) | [WIP](cypher/queries/bi-6.cypher) | [WIP](cypher/queries/bi-7.cypher) | [WIP](cypher/queries/bi-8.cypher) | [WIP](cypher/queries/bi-9.cypher) | [WIP](cypher/queries/bi-10.cypher) |
| PostgreSQL     | [OK](postgres/queries/bi/query1.sql) | [OK](postgres/queries/bi/query2.sql) | [WIP](postgres/queries/bi/query3.sql) | [?](postgres/queries/bi/query4.sql) | [OK](postgres/queries/bi/query5.sql) | [WIP](postgres/queries/bi/query6.sql) | [WIP](postgres/queries/bi/query7.sql) | [WIP](postgres/queries/bi/query8.sql) | [WIP](postgres/queries/bi/query9.sql) | [WIP](postgres/queries/bi/query10.sql) |
| Sparksee       | OK | OK | WIP | ? | OK | WIP | WIP | WIP | WIP | WIP |

| query                 | [11](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-11.yaml) | [12](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-12.yaml) | [13](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-13.yaml) | [14](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-14.yaml) | [15](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-15.yaml) | [16](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-16.yaml) | [17](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-17.yaml) | [18](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-18.yaml) | [19](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-19.yaml) | [20](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-20.yaml) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [WIP](cypher/queries/bi-11.cypher) | [WIP](cypher/queries/bi-12.cypher) | [WIP](cypher/queries/bi-13.cypher) | [WIP](cypher/queries/bi-14.cypher) | [WIP](cypher/queries/bi-15.cypher) | [WIP](cypher/queries/bi-16.cypher) | [WIP](cypher/queries/bi-17.cypher) | [WIP](cypher/queries/bi-18.cypher) | [WIP](cypher/queries/bi-19.cypher) | [WIP](cypher/queries/bi-20.cypher) |
| PostgreSQL     | [missing](postgres/queries/bi/query11.sql) | [WIP](postgres/queries/bi/query12.sql) | [WIP](postgres/queries/bi/query13.sql) | [WIP](postgres/queries/bi/query14.sql) | [WIP](postgres/queries/bi/query15.sql) | [WIP](postgres/queries/bi/query16.sql) | [WIP](postgres/queries/bi/query17.sql) | [WIP](postgres/queries/bi/query18.sql) | [WIP](postgres/queries/bi/query19.sql) | [WIP](postgres/queries/bi/query20.sql) |
| Sparksee       | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP |

| query                 | [21](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-21.yaml) | [22](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-22.yaml) | [23](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-23.yaml) | [24](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-24.yaml) | [25](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-25.yaml) |
| --------------------- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [WIP](cypher/queries/bi-21.cypher) | [WIP](cypher/queries/bi-22.cypher) | [WIP](cypher/queries/bi-23.cypher) | [WIP](cypher/queries/bi-24.cypher) | [WIP](cypher/queries/bi-25.cypher) |
| PostgreSQL     | [WIP](postgres/queries/bi/query21.sql) | [WIP](postgres/queries/bi/query22.sql) | [WIP](postgres/queries/bi/query23.sql) | [WIP](postgres/queries/bi/query24.sql) | [missing](postgres/queries/bi/query25.sql) |
| Sparksee       | WIP | WIP | WIP | WIP | WIP |


## User's guide

Download and install the LDBC SNB driver:

You can find the detailed information about this task here: <https://github.com/ldbc/ldbc_snb_driver>

After that, you can install the JAR file of ldbc_snb_driver to the local Maven repository. Go to the root directory of ldbc_snb_driver, and run:

```bash
mvn clean install -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7 -DskipTests
```

## Running the driver.

:warning: **TODO** :warning: This section is deprecated.

* Compile Db class for the JDBC database:

    Run:

    ```bash
    mvn clean package
    ```

* Running the driver against Postgres:

    You have to update the paths in the `run.sh` script, and use it for
    running the benchmark.  Before that, please update the 3 configuration
    files with your options. This process is explained here:
    <https://github.com/ldbc/ldbc_snb_driver/wiki>
