![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
LDBC SNB implementations
------------------------

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

:warning: Implementations in this repository are work-in-progress and are neither validated nor audited. Therefore, they should not be considered as official implementations until they are published in a peer-reviewed paper (planned for Q2 2018).

## Implementations

Each project has its own README:

* [PostgreSQL implementation](postgres/)
* [Cypher (Neo4j driver) implementation](cypher/)

## Status

The queries in this repository are work-in-progress.

| query                 | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-10.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [OK](cypher/queries/bi-1.cypher) | [OK](cypher/queries/bi-2.cypher) | [WIP](cypher/queries/bi-3.cypher) | [?](cypher/queries/bi-4.cypher) | [OK](cypher/queries/bi-5.cypher) | [WIP](cypher/queries/bi-6.cypher) | [WIP](cypher/queries/bi-7.cypher) | [WIP](cypher/queries/bi-8.cypher) | [WIP](cypher/queries/bi-9.cypher) | [WIP](cypher/queries/bi-10.cypher) |
| PostgreSQL     | [OK](postgres/queries/bi/query1.sql) | [OK](postgres/queries/bi/query2.sql) | [WIP](postgres/queries/bi/query3.sql) | [?](postgres/queries/bi/query4.sql) | [OK](postgres/queries/bi/query5.sql) | [WIP](postgres/queries/bi/query6.sql) | [WIP](postgres/queries/bi/query7.sql) | [WIP](postgres/queries/bi/query8.sql) | [WIP](postgres/queries/bi/query9.sql) | [WIP](postgres/queries/bi/query10.sql) |
| Sparksee       | OK | OK | WIP | ? | OK | WIP | WIP | WIP | WIP | WIP |

| query                 | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-14.pdf) | [15](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-15.pdf) | [16](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-16.pdf) | [17](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-17.pdf) | [18](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-18.pdf) | [19](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-19.pdf) | [20](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-20.pdf) |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [WIP](cypher/queries/bi-11.cypher) | [WIP](cypher/queries/bi-12.cypher) | [WIP](cypher/queries/bi-13.cypher) | [WIP](cypher/queries/bi-14.cypher) | [WIP](cypher/queries/bi-15.cypher) | [WIP](cypher/queries/bi-16.cypher) | [WIP](cypher/queries/bi-17.cypher) | [WIP](cypher/queries/bi-18.cypher) | [WIP](cypher/queries/bi-19.cypher) | [WIP](cypher/queries/bi-20.cypher) |
| PostgreSQL     | [missing](postgres/queries/bi/query11.sql) | [WIP](postgres/queries/bi/query12.sql) | [WIP](postgres/queries/bi/query13.sql) | [WIP](postgres/queries/bi/query14.sql) | [WIP](postgres/queries/bi/query15.sql) | [WIP](postgres/queries/bi/query16.sql) | [WIP](postgres/queries/bi/query17.sql) | [WIP](postgres/queries/bi/query18.sql) | [WIP](postgres/queries/bi/query19.sql) | [WIP](postgres/queries/bi/query20.sql) |
| Sparksee       | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP | WIP |

| query                 | [21](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-21.pdf) | [22](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-22.pdf) | [23](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-23.pdf) | [24](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-24.pdf) | [25](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-25.pdf) |
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
