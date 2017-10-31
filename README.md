# LDBC SNB implementations

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

## Implementations

Each project has its own README:

* [PostgreSQL implementation](postgres/)
* [Cypher (Neo4j driver) implementation](cypher/)

## Status

The queries in this repository are work-in-progress.

Cypher
PostgreSQL

| query                 | [01](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-01.yaml) | [02](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-02.yaml) | [03](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-03.yaml) | [04](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-04.yaml) | [05](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-05.yaml) | [06](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-06.yaml) | [07](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-07.yaml) | [08](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-08.yaml) | [09](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-09.yaml) | [10](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-10.yaml) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | OK | OK | params missing | ? | OK | | | | | |
| PostgreSQL     | | | | | | | | | | |
| Sparksee       | OK | OK | params missing | ? | OK | | | | | |

| query                 | [11](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-11.yaml) | [12](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-12.yaml) | [13](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-13.yaml) | [14](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-14.yaml) | [15](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-15.yaml) | [16](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-16.yaml) | [17](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-17.yaml) | [18](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-18.yaml) | [19](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-19.yaml) | [20](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-20.yaml) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | | | | | | | | | | |
| PostgreSQL     | | | | | | | | | | |
| Sparksee       | | | | | | | | | | |

| query                 | [21](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-21.yaml) | [22](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-22.yaml) | [23](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-23.yaml) | [24](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-24.yaml) | [25](https://github.com/ldbc/ldbc_snb_docs/tree/master/query-specifications/bi-read-25.yaml) |
| --------------------- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | | | | | |
| PostgreSQL     | | | | | |
| Sparksee       | | | | | |


## User's guide

Download and install the LDBC SNB driver:

    You can find the detailed information about this task here:
    <https://github.com/ldbc/ldbc_snb_driver>

    After that, you can install the JAR file of ldbc_snb_driver to the local
    Maven repository. Go to the root directory of ldbc_snb_driver, and run:

    ```
    mvn clean install -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7 -DskipTests
    ```

## Running the driver.

:warning: **TODO** :warning: This section is deprecated.

Compile Db class for the JDBC database:

    Run:

    ```
    mvn clean package
    ```

Running the driver against Postgres:

    You have to update the paths in the `run.sh` script, and use it for
    running the benchmark.  Before that, please update the 3 configuration
    files with your options. This process is explained here:
    <https://github.com/ldbc/ldbc_snb_driver/wiki>
