![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen/images/ldbc-logo.png)
LDBC SNB implementations
------------------------

[![Build Status](https://circleci.com/gh/ldbc/ldbc_snb_implementations.svg?style=svg)](https://circleci.com/gh/ldbc/ldbc_snb_implementations)

Implementations for the Workload components of the [LDBC Social Network Benchmark](https://ldbc.github.io/ldbc_snb_docs/).

:scroll: If you wish to cite the LDBC SNB, please refer to the [documentation repository](https://github.com/ldbc/ldbc_snb_docs#how-to-cite-ldbc-benchmarks) ([bib snippet](https://github.com/ldbc/ldbc_snb_docs/blob/dev/bib/specification.bib)).

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
  * [`ldbc_snb_driver`](https://github.com/ldbc/ldbc_snb_driver)
  * [`ldbc_snb_implementations`](https://github.com/ldbc/ldbc_snb_implementations)

## Implementations

Each project has its own README:

* [Neo4j implementation](cypher/)
* [PostgreSQL implementation](postgres/)

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

### BI

| query          | [01](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-01.pdf) | [02](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-02.pdf) | [03](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-03.pdf) | [04](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-04.pdf) | [05](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-05.pdf) | [06](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-06.pdf) | [07](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-07.pdf) | [08](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-08.pdf) | [09](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-09.pdf) | [10](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-10.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/bi-1.cypher) | [02](cypher/queries/bi-2.cypher) | [03](cypher/queries/bi-3.cypher) | [04](cypher/queries/bi-4.cypher) | [05](cypher/queries/bi-5.cypher) | [06](cypher/queries/bi-6.cypher) | [07](cypher/queries/bi-7.cypher) | [08](cypher/queries/bi-8.cypher) | [09](cypher/queries/bi-9.cypher) | [10](cypher/queries/bi-10.cypher) |
| PostgreSQL     | [01](postgres/queries/bi-1.sql)  | [02](postgres/queries/bi-2.sql)  | [03](postgres/queries/bi-3.sql)  | [04](postgres/queries/bi-4.sql)  | [05](postgres/queries/bi-5.sql)  | [06](postgres/queries/bi-6.sql)  | [07](postgres/queries/bi-7.sql)  | [08](postgres/queries/bi-8.sql)  | [09](postgres/queries/bi-9.sql)  | [10](postgres/queries/bi-10.sql)  |

| query          | [11](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-11.pdf) | [12](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-12.pdf) | [13](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-13.pdf) | [14](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-14.pdf) | [15](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-15.pdf) | [16](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-16.pdf) | [17](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-17.pdf) | [18](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-18.pdf) | [19](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-19.pdf) | [20](https://ldbc.github.io/ldbc_snb_docs_snapshot/bi-read-20.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [11](cypher/queries/bi-11.cypher) | [12](cypher/queries/bi-12.cypher) | [13](cypher/queries/bi-13.cypher) | [14](cypher/queries/bi-14.cypher) | [15](cypher/queries/bi-15.cypher) | [16](cypher/queries/bi-16.cypher) | [17](cypher/queries/bi-17.cypher) | [18](cypher/queries/bi-18.cypher) | [19](cypher/queries/bi-19.cypher) | [20](cypher/queries/bi-20.cypher) |
| PostgreSQL     | [11](postgres/queries/bi-11.sql)  | [12](postgres/queries/bi-12.sql)  | [13](postgres/queries/bi-13.sql)  | [14](postgres/queries/bi-14.sql)  | [15](postgres/queries/bi-15.sql)  | [16](postgres/queries/bi-16.sql)  | [17](postgres/queries/bi-17.sql)  | [18](postgres/queries/bi-18.sql)  | [19](postgres/queries/bi-19.sql)  | [20](postgres/queries/bi-20.sql)  |

## User's guide

1. If you are on the `dev` branch or any other unstable branche, first build and install the driver from source.

    1. Grab the driver source code from: https://github.com/ldbc/ldbc_snb_driver.
    2. Install the driver artifact to the local Maven repository:

        ```bash
        mvn clean install -DskipTests
        ```

2. Navigate to the root of this repository and build it to generate the JAR files for the implementations:

   ```bash
   ./build.sh
   ```

3. For each implementation, it is possible to (1) create validation parameters, (2) validate against an existing validation parameters, and (3) run the benchmark. Set the parameters according to your system configuration in the appropriate `.properties` file and run the driver with one of the following scripts in the `driver` directory:

   ```bash
   # BI workload
   driver/bi-create-validation-parameters.sh
   driver/bi-validate.sh
   driver/bi-benchmark.sh
   # Interactive workload - note that if the workload contains updates, the database needs to be re-loaded between steps
   driver/interactive-create-validation-parameters.sh
   driver/interactive-validate.sh
   driver/interactive-benchmark.sh
   ```

For more details, on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_driver/wiki).

### Testing

To generate small data sets for smoke tests, use scale factor 0.003 (`generator.scaleFactor:0.003`).
