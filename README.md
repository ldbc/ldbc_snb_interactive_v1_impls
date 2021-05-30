![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
# LDBC SNB Interactive workload implementations

Implementations for the Interactive workload of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

This repository contains reference implementations for the two SNB workload:

* `stable` contains implementations of the Interactive workload using Java clients and the [LDBC driver](https://github.com/ldbc/ldbc_snb_driver/). It uses data sets and parameters produced by the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen/tree/stable). It is tested and had been used for audited benchmark runs.
* `dev` contains implementations of the work-in-progress BI workload using Python clients and a lightweight driver (due to the simpler workflow of the workload). It uses data sets and parameters produced by the [Spark-based Datagen](https://github.com/ldbc/ldbc_snb_datagen). This implementation is experimental with frequent breaking changes.

:warning: Implementations in this repository are preliminary, i.e. they are unaudited and - in rare cases - do not pass validation. For details, feel free to contact us through an issue or email.

## Directory layout

Some configuration files and scripts use [relative paths to address the data generator's directory](https://github.com/ldbc/ldbc_snb_implementations/search?q=ldbc_snb_datagen). Hence, it is recommended to clone the LDBC Data Generator and the LDBC implementations repositories next to each other and keep their original directory names. For example:

* `ldbc`
  * [`ldbc_snb_datagen`](https://github.com/ldbc/ldbc_snb_datagen)
  * [`ldbc_snb_driver`](https://github.com/ldbc/ldbc_snb_driver)
  * [`ldbc_snb_implementations`](https://github.com/ldbc/ldbc_snb_implementations/)

## Implementations

Each project has its own README:

* [Cypher (Neo4j driver) implementation](cypher/)
* [PostgreSQL implementation](postgres/)

The [Sparksee implementation](https://github.com/DAMA-UPC/ldbc-sparksee) is maintained in a separate repository.

## Queries

The queries in this repository are work-in-progress. If possible, please cross-validate your queries against multiple implementations.

### Interactive

#### Complex reads

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-10.pdf) | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-14.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-complex-1.cypher) | [02](cypher/queries/interactive-complex-2.cypher) | [03](cypher/queries/interactive-complex-3.cypher) | [04](cypher/queries/interactive-complex-4.cypher) | [05](cypher/queries/interactive-complex-5.cypher) | [06](cypher/queries/interactive-complex-6.cypher) | [07](cypher/queries/interactive-complex-7.cypher) | [08](cypher/queries/interactive-complex-8.cypher) | [09](cypher/queries/interactive-complex-9.cypher) | [10](cypher/queries/interactive-complex-10.cypher) | [11](cypher/queries/interactive-complex-11.cypher) | [12](cypher/queries/interactive-complex-12.cypher) | [13](cypher/queries/interactive-complex-13.cypher) | [14](cypher/queries/interactive-complex-14.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-complex-1.sql)  | [02](postgres/queries/interactive-complex-2.sql)  | [03](postgres/queries/interactive-complex-3.sql)  | [04](postgres/queries/interactive-complex-4.sql)  | [05](postgres/queries/interactive-complex-5.sql)  | [06](postgres/queries/interactive-complex-6.sql)  | [07](postgres/queries/interactive-complex-7.sql)  | [08](postgres/queries/interactive-complex-8.sql)  | [09](postgres/queries/interactive-complex-9.sql)  | [10](postgres/queries/interactive-complex-10.sql)  | [11](postgres/queries/interactive-complex-11.sql)  | [12](postgres/queries/interactive-complex-12.sql)  | [13](postgres/queries/interactive-complex-13.sql)  | [14](postgres/queries/interactive-complex-14.sql)  |

#### Short reads

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-07.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-short-1.cypher) | [02](cypher/queries/interactive-short-2.cypher) | [03](cypher/queries/interactive-short-3.cypher) | [04](cypher/queries/interactive-short-4.cypher) | [05](cypher/queries/interactive-short-5.cypher) | [06](cypher/queries/interactive-short-6.cypher) | [07](cypher/queries/interactive-short-7.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-short-1.sql)  | [02](postgres/queries/interactive-short-2.sql)  | [03](postgres/queries/interactive-short-3.sql)  | [04](postgres/queries/interactive-short-4.sql)  | [05](postgres/queries/interactive-short-5.sql)  | [06](postgres/queries/interactive-short-6.sql)  | [07](postgres/queries/interactive-short-7.sql)  |

#### Inserts

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-08.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-update-1.cypher) | [02](cypher/queries/interactive-update-2.cypher) | [03](cypher/queries/interactive-update-3.cypher) | [04](cypher/queries/interactive-update-4.cypher) | [05](cypher/queries/interactive-update-5.cypher) | [06](cypher/queries/interactive-update-6.cypher) | [07](cypher/queries/interactive-update-7.cypher) | [08](cypher/queries/interactive-update-8.cypher) |
| PostgreSQL     | [01/p](postgres/queries/interactive-update-1-add-person.sql) [01/c](postgres/queries/interactive-update-1-add-person-companies.sql) [01/e](postgres/queries/interactive-update-1-add-person-emails.sql) [01/l](postgres/queries/interactive-update-1-add-person-languages.sql) [01/t](postgres/queries/interactive-update-1-add-person-tags.sql) [01/u](postgres/queries/interactive-update-1-add-person-universities.sql) | [02](postgres/queries/interactive-update-2.sql) | [03](postgres/queries/interactive-update-3.sql) | [04/f](postgres/queries/interactive-update-4-add-forum.sql) [04/t](postgres/queries/interactive-update-4-add-forum-tags.sql) | [05](postgres/queries/interactive-update-5.sql) | [06/p](postgres/queries/interactive-update-6-add-post.sql) [06/t](postgres/queries/interactive-update-6-add-post-tags.sql) | [07/c](postgres/queries/interactive-update-7-add-comment.sql) [07/t](postgres/queries/interactive-update-7-add-comment-tags.sql) | [08](postgres/queries/interactive-update-8.sql) |

## User's guide

1. Grab the `stable` driver source code from:

   ```bash
   git clone --branch stable https://github.com/ldbc/ldbc_snb_driver
   ```

1. Install the driver artifact to the local Maven repository:

   ```bash
   cd ldbc_snb_driver
   mvn clean install -DskipTests
   ```

2. Navigate to the root of this repository and build it to generate the JAR files for the implementations:

   ```bash
   ./build.sh
   ```

3. For each implementation, it is possible to (1) create validation parameters, (2) validate against an existing validation parameters, and (3) run the benchmark. Set the parameters according to your system configuration in the appropriate `.properties` file and run the driver with one of the following scripts:

   ```bash
   # BI workload
   ./bi-create-validation-parameters.sh
   ./bi-validate.sh
   ./bi-benchmark.sh
   # Interactive workload - note that if the workload contains updates, the database needs to be re-loaded between steps
   ./interactive-create-validation-parameters.sh
   ./interactive-validate.sh
   ./interactive-benchmark.sh
   ```

For more details, on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_driver/wiki).

### Generating small test data tests

To generate small data sets, use scale factor 1 (SF1) with the persons and years set according to this template. Note that a certain number of persons (~250) is required for the parameter generation script to function correctly.

```ini
ldbc.snb.datagen.generator.scaleFactor:snb.interactive.1

ldbc.snb.datagen.generator.numPersons:250
ldbc.snb.datagen.generator.numYears:1
ldbc.snb.datagen.generator.numThreads:1

ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>InvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonActivitySerializer
```
