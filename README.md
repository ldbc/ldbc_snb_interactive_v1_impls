![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
LDBC SNB implementations
------------------------

[![Build Status](https://travis-ci.org/ldbc/ldbc_snb_implementations.svg?branch=master)](https://travis-ci.org/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the LDBC Social Network Benchmark ([specification](https://ldbc.github.io/ldbc_snb_docs/)).

:warning: The most recent version of the code can be found on the `dev` branch. The `master` branch tends to be better tested and uses a stable version of the LDBC SNB driver.

:warning: Implementations in this repository are preliminary, i.e. they are unaudited and - in rare cases - do not pass validation. For details, feel free to contact us through an issue or email.

## Compatibility

The LDBC Social Network Benchmark suite is continuously maintained with improvements in the specification, the data generator, the driver, and the reference implementation.
To ensure that you are using compatible LDBC repositories, use the following table:

| project | v0.3.x | v0.4.x |
| ------- | ------ | ------ |
| [Documentation](https://github.com/ldbc/ldbc_snb_docs) | [`v0.3.3`](https://github.com/ldbc/ldbc_snb_docs/releases/tag/v0.3.3) | [`dev`](https://github.com/ldbc/ldbc_snb_docs/tree/dev) |
| [Datagen](https://github.com/ldbc/ldbc_snb_datagen) | [`v0.3.3`](https://github.com/ldbc/ldbc_snb_datagen/releases/tag/v0.3.3) | [`dev`](https://github.com/ldbc/ldbc_snb_datagen/tree/dev) |
| [Driver](https://github.com/ldbc/ldbc_snb_driver) | [`v0.3.3`](https://github.com/ldbc/ldbc_snb_driver/releases/tag/0.3.3) | [`dev`](https://github.com/ldbc/ldbc_snb_driver/tree/dev) |
| [Implementations](https://github.com/ldbc/ldbc_snb_implementations) | [`stable`](https://github.com/ldbc/ldbc_snb_implementations/tree/stable) | [`dev`](https://github.com/ldbc/ldbc_snb_implementations/tree/dev) |

The `stable` branches of the repositories correspond to the `v0.3.x`, and the `dev` branches correspond to the `v0.4.x` releases.

## Directory layout

Multiple configuration files and scripts use [relative paths to address the data generator's directory](https://github.com/ldbc/ldbc_snb_implementations/search?q=ldbc_snb_datagen). Hence, it is recommended to clone the LDBC Data Generator and the LDBC implementations repositories next to each other and keep their original directory names. For example:

* `ldbc`
  * [`ldbc_snb_datagen`](https://github.com/ldbc/ldbc_snb_datagen)
  * [`ldbc_snb_driver`](https://github.com/ldbc/ldbc_snb_driver) – **use the `dev` branch**
  * [`ldbc_snb_implementations`](https://github.com/ldbc/ldbc_snb_implementations/)

## Implementations

Each project has its own README:

* [Cypher (Neo4j driver) implementation](cypher/)
* [PostgreSQL implementation](postgres/)
* [SPARQL implementation](sparql/)

The [Sparksee implementation](https://github.com/DAMA-UPC/ldbc-sparksee) is maintained in a separate repository.

## Queries

The queries in this repository are work-in-progress. If possible, please cross-validate your queries against multiple implementations.

### Interactive

#### Complex reads

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-10.pdf) | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-complex-read-14.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-complex-1.cypher) | [02](cypher/queries/interactive-complex-2.cypher) | [03](cypher/queries/interactive-complex-3.cypher) | [04](cypher/queries/interactive-complex-4.cypher) | [05](cypher/queries/interactive-complex-5.cypher) | [06](cypher/queries/interactive-complex-6.cypher) | [07](cypher/queries/interactive-complex-7.cypher) | [08](cypher/queries/interactive-complex-8.cypher) | [09](cypher/queries/interactive-complex-9.cypher) | [10](cypher/queries/interactive-complex-10.cypher) | [11](cypher/queries/interactive-complex-11.cypher) | [12](cypher/queries/interactive-complex-12.cypher) | [13](cypher/queries/interactive-complex-13.cypher) | [14](cypher/queries/interactive-complex-14.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-complex-1.sql)  | [02](postgres/queries/interactive-complex-2.sql)  | [03](postgres/queries/interactive-complex-3.sql)  | [04](postgres/queries/interactive-complex-4.sql)  | [05](postgres/queries/interactive-complex-5.sql)  | [06](postgres/queries/interactive-complex-6.sql)  | [07](postgres/queries/interactive-complex-7.sql)  | [08](postgres/queries/interactive-complex-8.sql)  | [09](postgres/queries/interactive-complex-9.sql)  | [10](postgres/queries/interactive-complex-10.sql)  | [11](postgres/queries/interactive-complex-11.sql)  | [12](postgres/queries/interactive-complex-12.sql)  | [13](postgres/queries/interactive-complex-13.sql)  | [14](postgres/queries/interactive-complex-14.sql)  |
| SPARQL         | [01](sparql/queries/interactive-complex-1.sparql) | [02](sparql/queries/interactive-complex-2.sparql) | [03](sparql/queries/interactive-complex-3.sparql) | [04](sparql/queries/interactive-complex-4.sparql) | [05](sparql/queries/interactive-complex-5.sparql) | [06](sparql/queries/interactive-complex-6.sparql) | [07](sparql/queries/interactive-complex-7.sparql) | [08](sparql/queries/interactive-complex-8.sparql) | [09](sparql/queries/interactive-complex-9.sparql) | [10](sparql/queries/interactive-complex-10.sparql) | [11](sparql/queries/interactive-complex-11.sparql) | [12](sparql/queries/interactive-complex-12.sparql) | [13](sparql/queries/interactive-complex-13.sparql) | [14](sparql/queries/interactive-complex-14.sparql) |

#### Short reads

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-short-read-07.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-short-1.cypher) | [02](cypher/queries/interactive-short-2.cypher) | [03](cypher/queries/interactive-short-3.cypher) | [04](cypher/queries/interactive-short-4.cypher) | [05](cypher/queries/interactive-short-5.cypher) | [06](cypher/queries/interactive-short-6.cypher) | [07](cypher/queries/interactive-short-7.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-short-1.sql)  | [02](postgres/queries/interactive-short-2.sql)  | [03](postgres/queries/interactive-short-3.sql)  | [04](postgres/queries/interactive-short-4.sql)  | [05](postgres/queries/interactive-short-5.sql)  | [06](postgres/queries/interactive-short-6.sql)  | [07](postgres/queries/interactive-short-7.sql)  |
| SPARQL         | [01](sparql/queries/interactive-short-1.sparql) | [02](sparql/queries/interactive-short-2.sparql) | [03](sparql/queries/interactive-short-3.sparql) | [04](sparql/queries/interactive-short-4.sparql) | [05](sparql/queries/interactive-short-5.sparql) | [06](sparql/queries/interactive-short-6.sparql) | [07](sparql/queries/interactive-short-7.sparql) |

#### Inserts

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/interactive-insert-08.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-update-1.cypher) | [02](cypher/queries/interactive-update-2.cypher) | [03](cypher/queries/interactive-update-3.cypher) | [04](cypher/queries/interactive-update-4.cypher) | [05](cypher/queries/interactive-update-5.cypher) | [06](cypher/queries/interactive-update-6.cypher) | [07](cypher/queries/interactive-update-7.cypher) | [08](cypher/queries/interactive-update-8.cypher) |
| PostgreSQL     | [01/p](postgres/queries/interactive-update-1-add-person.sql) [01/c](postgres/queries/interactive-update-1-add-person-companies.sql) [01/e](postgres/queries/interactive-update-1-add-person-emails.sql) [01/l](postgres/queries/interactive-update-1-add-person-languages.sql) [01/t](postgres/queries/interactive-update-1-add-person-tags.sql) [01/u](postgres/queries/interactive-update-1-add-person-universities.sql) | [02](postgres/queries/interactive-update-2.sql) | [03](postgres/queries/interactive-update-3.sql) | [04/f](postgres/queries/interactive-update-4-add-forum.sql) [04/t](postgres/queries/interactive-update-4-add-forum-tags.sql) | [05](postgres/queries/interactive-update-5.sql) | [06/p](postgres/queries/interactive-update-6-add-post.sql) [06/t](postgres/queries/interactive-update-6-add-post-tags.sql) | [07/c](postgres/queries/interactive-update-7-add-comment.sql) [07/t](postgres/queries/interactive-update-7-add-comment-tags.sql) | [08](postgres/queries/interactive-update-8.sql) |
| SPARQL         | [01/p](sparql/queries/interactive-update-1-add-person.sparql) [01/c](sparql/queries/interactive-update-1-add-person-companies.sparql) [01/e](sparql/queries/interactive-update-1-add-person-emails.sparql) [01/l](sparql/queries/interactive-update-1-add-person-languages.sparql) [01/t](sparql/queries/interactive-update-1-add-person-tags.sparql) [01/u](sparql/queries/interactive-update-1-add-person-universities.sparql) | [02](sparql/queries/interactive-update-2.sparql) | [03](sparql/queries/interactive-update-3.sparql) | [04/f](sparql/queries/interactive-update-4-add-forum.sparql) [04/t](sparql/queries/interactive-update-4-add-forum-tags.sparql) | [05](sparql/queries/interactive-update-5.sparql) | [06/p](sparql/queries/interactive-update-6-add-post.sparql) [06/t](sparql/queries/interactive-update-6-add-post-tags.sparql) [06/i](sparql/queries/interactive-update-6-add-post-imagefile.sparql) [06/c](sparql/queries/interactive-update-6-add-post-content.sparql) | [07/c](sparql/queries/interactive-update-7-add-comment.sparql) [07/t](sparql/queries/interactive-update-7-add-comment-tags.sparql) | [08](sparql/queries/interactive-update-8.sparql) |

### BI

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-10.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/bi-1.cypher) | [02](cypher/queries/bi-2.cypher) | [03](cypher/queries/bi-3.cypher) | [04](cypher/queries/bi-4.cypher) | [05](cypher/queries/bi-5.cypher) | [06](cypher/queries/bi-6.cypher) | [07](cypher/queries/bi-7.cypher) | [08](cypher/queries/bi-8.cypher) | [09](cypher/queries/bi-9.cypher) | [10](cypher/queries/bi-10.cypher) |
| PostgreSQL     | [01](postgres/queries/bi-1.sql)  | [02](postgres/queries/bi-2.sql)  | [03](postgres/queries/bi-3.sql)  | [04](postgres/queries/bi-4.sql)  | [05](postgres/queries/bi-5.sql)  | [06](postgres/queries/bi-6.sql)  | [07](postgres/queries/bi-7.sql)  | [08](postgres/queries/bi-8.sql)  | [09](postgres/queries/bi-9.sql)  | [10](postgres/queries/bi-10.sql)  |
| SPARQL         | [01](sparql/queries/bi-1.sparql) | [02](sparql/queries/bi-2.sparql) | [03](sparql/queries/bi-3.sparql) | [04](sparql/queries/bi-4.sparql) | [05](sparql/queries/bi-5.sparql) | [06](sparql/queries/bi-6.sparql) | [07](sparql/queries/bi-7.sparql) | [08](sparql/queries/bi-8.sparql) | [09](sparql/queries/bi-9.sparql) | [10](sparql/queries/bi-10.sparql) |

| query          | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-14.pdf) | [15](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-15.pdf) | [16](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-16.pdf) | [17](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-17.pdf) | [18](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-18.pdf) | [19](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-19.pdf) | [20](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-20.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [11](cypher/queries/bi-11.cypher) | [12](cypher/queries/bi-12.cypher) | [13](cypher/queries/bi-13.cypher) | [14](cypher/queries/bi-14.cypher) | [15](cypher/queries/bi-15.cypher) | [16](cypher/queries/bi-16.cypher) | [17](cypher/queries/bi-17.cypher) | [18](cypher/queries/bi-18.cypher) | [19](cypher/queries/bi-19.cypher) | [20](cypher/queries/bi-20.cypher) |
| PostgreSQL     | [11](postgres/queries/bi-11.sql)  | [12](postgres/queries/bi-12.sql)  | [13](postgres/queries/bi-13.sql)  | [14](postgres/queries/bi-14.sql)  | [15](postgres/queries/bi-15.sql)  | [16](postgres/queries/bi-16.sql)  | [17](postgres/queries/bi-17.sql)  | [18](postgres/queries/bi-18.sql)  | [19](postgres/queries/bi-19.sql)  | [20](postgres/queries/bi-20.sql)  |
| SPARQL         | [11](sparql/queries/bi-11.sparql) | [12](sparql/queries/bi-12.sparql) | [13](sparql/queries/bi-13.sparql) | [14](sparql/queries/bi-14.sparql) | [15](sparql/queries/bi-15.sparql) | [16](sparql/queries/bi-16.sparql) | [17](sparql/queries/bi-17.sparql) | [18](sparql/queries/bi-18.sparql) | [19](sparql/queries/bi-19.sparql) | [20](sparql/queries/bi-20.sparql) |

| query          | [21](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-21.pdf) | [22](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-22.pdf) | [23](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-23.pdf) | [24](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-24.pdf) | [25](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-25.pdf) |
| -------------- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [21](cypher/queries/bi-21.cypher) | [22](cypher/queries/bi-22.cypher) | [23](cypher/queries/bi-23.cypher) | [24](cypher/queries/bi-24.cypher) | [25](cypher/queries/bi-25.cypher) |
| PostgreSQL     | [21](postgres/queries/bi-21.sql)  | [22](postgres/queries/bi-22.sql)  | [23](postgres/queries/bi-23.sql)  | [24](postgres/queries/bi-24.sql)  | [25](postgres/queries/bi-25.sql)  |
| SPARQL         | [21](sparql/queries/bi-21.sparql) | [22](sparql/queries/bi-22.sparql) | [23](sparql/queries/bi-23.sparql) | [24](sparql/queries/bi-24.sparql) | [25](sparql/queries/bi-25.sparql) |


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
