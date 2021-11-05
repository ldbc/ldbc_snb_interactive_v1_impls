![LDBC_LOGO](https://raw.githubusercontent.com/wiki/ldbc/ldbc_snb_datagen_hadoop/images/ldbc-logo.png)
# LDBC SNB Interactive workload implementations

Reference implementations for the Interactive workload of the LDBC Social Network Benchmark ([specification](https://ldbcouncil.org/ldbc_snb_docs/)).
To get the reference implementations for the BI workload, visit the [`ldbc_snb_bi` repository](https://github.com/ldbc/ldbc_snb_bi/).

:warning: The goal of the implementations in this to serve as reference implementations which other implementations can cross-validated against. Therefore, our primary objective was readability and not absolute performance when formulating the queries.

## Directory layout

Some configuration files and scripts use [relative paths to address the data generator's directory](https://github.com/ldbc/ldbc_snb_interactive/search?q=ldbc_snb_datagen_hadoop). Hence, it is recommended to clone the LDBC Data Generator and the LDBC implementations repositories next to each other and keep their original directory names. For example:

* `ldbc`
  * [`ldbc_snb_datagen_hadoop`](https://github.com/ldbc/ldbc_snb_datagen_hadoop)
  * [`ldbc_snb_driver`](https://github.com/ldbc/ldbc_snb_driver)
  * [`ldbc_snb_interactive`](https://github.com/ldbc/ldbc_snb_interactive/)

## Implementations

Each project has its own README:

* [Cypher (Neo4j) implementation](cypher/)
* [SQL (PostgreSQL) implementation](postgres/)
* [Sparql (GraphDB) implementation](graphdb/)

The [Sparksee implementation](https://github.com/DAMA-UPC/ldbc-sparksee) is maintained in a separate repository.

## Queries

The queries in this repository are work-in-progress. If possible, please cross-validate your queries against multiple implementations.

### Interactive

#### Complex reads

| query          | [01](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-01.pdf) | [02](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-02.pdf) | [03](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-03.pdf) | [04](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-04.pdf) | [05](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-05.pdf) | [06](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-06.pdf) | [07](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-07.pdf) | [08](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-08.pdf) | [09](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-09.pdf) | [10](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-10.pdf) | [11](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-11.pdf) | [12](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-12.pdf) | [13](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-13.pdf) | [14](https://ldbcouncil.org/ldbc_snb_docs/interactive-complex-read-14.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-complex-1.cypher) | [02](cypher/queries/interactive-complex-2.cypher) | [03](cypher/queries/interactive-complex-3.cypher) | [04](cypher/queries/interactive-complex-4.cypher) | [05](cypher/queries/interactive-complex-5.cypher) | [06](cypher/queries/interactive-complex-6.cypher) | [07](cypher/queries/interactive-complex-7.cypher) | [08](cypher/queries/interactive-complex-8.cypher) | [09](cypher/queries/interactive-complex-9.cypher) | [10](cypher/queries/interactive-complex-10.cypher) | [11](cypher/queries/interactive-complex-11.cypher) | [12](cypher/queries/interactive-complex-12.cypher) | [13](cypher/queries/interactive-complex-13.cypher) | [14](cypher/queries/interactive-complex-14.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-complex-1.sql)  | [02](postgres/queries/interactive-complex-2.sql)  | [03](postgres/queries/interactive-complex-3.sql)  | [04](postgres/queries/interactive-complex-4.sql)  | [05](postgres/queries/interactive-complex-5.sql)  | [06](postgres/queries/interactive-complex-6.sql)  | [07](postgres/queries/interactive-complex-7.sql)  | [08](postgres/queries/interactive-complex-8.sql)  | [09](postgres/queries/interactive-complex-9.sql)  | [10](postgres/queries/interactive-complex-10.sql)  | [11](postgres/queries/interactive-complex-11.sql)  | [12](postgres/queries/interactive-complex-12.sql)  | [13](postgres/queries/interactive-complex-13.sql)  | [14](postgres/queries/interactive-complex-14.sql)  |
| Graphdb (Sparql) | [01](graphdb/queries/interactive-complex-1.rq)    | [02](graphdb/queries/interactive-complex-2.rq)    | [03](graphdb/queries/interactive-complex-3.rq)    | [04](graphdb/queries/interactive-complex-4.rq)    | [05](graphdb/queries/interactive-complex-5.rq)    | [06](graphdb/queries/interactive-complex-6.rq)    | [07](graphdb/queries/interactive-complex-7.rq)    | [08](graphdb/queries/interactive-complex-8.rq)    | [09](graphdb/queries/interactive-complex-9.rq)    | [10](graphdb/queries/interactive-complex-10.rq)    | [11](graphdb/queries/interactive-complex-11.rq)    | [12](graphdb/queries/interactive-complex-12.rq)    | [13](graphdb/queries/interactive-complex-13.rq)    | [14](graphdb/queries/interactive-complex-14.rq)    |
#### Short reads

| query          | [01](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-01.pdf) | [02](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-02.pdf) | [03](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-03.pdf) | [04](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-04.pdf) | [05](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-05.pdf) | [06](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-06.pdf) | [07](https://ldbcouncil.org/ldbc_snb_docs/interactive-short-read-07.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-short-1.cypher) | [02](cypher/queries/interactive-short-2.cypher) | [03](cypher/queries/interactive-short-3.cypher) | [04](cypher/queries/interactive-short-4.cypher) | [05](cypher/queries/interactive-short-5.cypher) | [06](cypher/queries/interactive-short-6.cypher) | [07](cypher/queries/interactive-short-7.cypher) |
| PostgreSQL     | [01](postgres/queries/interactive-short-1.sql)  | [02](postgres/queries/interactive-short-2.sql)  | [03](postgres/queries/interactive-short-3.sql)  | [04](postgres/queries/interactive-short-4.sql)  | [05](postgres/queries/interactive-short-5.sql)  | [06](postgres/queries/interactive-short-6.sql)  | [07](postgres/queries/interactive-short-7.sql)  |
| Graphdb (Sparql) | [01](graphdb/queries/interactive-short-1.rq)    | [02](graphdb/queries/interactive-short-2.rq)    | [03](graphdb/queries/interactive-short-3.rq)    | [04](graphdb/queries/interactive-short-4.rq)    | [05](graphdb/queries/interactive-short-5.rq)    | [06](graphdb/queries/interactive-short-6.rq)    | [07](graphdb/queries/interactive-short-7.rq)    |

#### Inserts

| query          | [01](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-01.pdf) | [02](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-02.pdf) | [03](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-03.pdf) | [04](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-04.pdf) | [05](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-05.pdf) | [06](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-06.pdf) | [07](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-07.pdf) | [08](https://ldbcouncil.org/ldbc_snb_docs/interactive-insert-08.pdf) |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- |
| Neo4j (Cypher) | [01](cypher/queries/interactive-update-1.cypher) | [02](cypher/queries/interactive-update-2.cypher) | [03](cypher/queries/interactive-update-3.cypher) | [04](cypher/queries/interactive-update-4.cypher) | [05](cypher/queries/interactive-update-5.cypher) | [06](cypher/queries/interactive-update-6.cypher) | [07](cypher/queries/interactive-update-7.cypher) | [08](cypher/queries/interactive-update-8.cypher) |
| PostgreSQL     | [01/p](postgres/queries/interactive-update-1-add-person.sql) [01/c](postgres/queries/interactive-update-1-add-person-companies.sql) [01/e](postgres/queries/interactive-update-1-add-person-emails.sql) [01/l](postgres/queries/interactive-update-1-add-person-languages.sql) [01/t](postgres/queries/interactive-update-1-add-person-tags.sql) [01/u](postgres/queries/interactive-update-1-add-person-universities.sql) | [02](postgres/queries/interactive-update-2.sql) | [03](postgres/queries/interactive-update-3.sql) | [04/f](postgres/queries/interactive-update-4-add-forum.sql) [04/t](postgres/queries/interactive-update-4-add-forum-tags.sql) | [05](postgres/queries/interactive-update-5.sql) | [06/p](postgres/queries/interactive-update-6-add-post.sql) [06/t](postgres/queries/interactive-update-6-add-post-tags.sql) | [07/c](postgres/queries/interactive-update-7-add-comment.sql) [07/t](postgres/queries/interactive-update-7-add-comment-tags.sql) | [08](postgres/queries/interactive-update-8.sql) |
| Graphdb (Sparql) | [01/p](graphdb/queries/interactive-update-1-add-person.rq) [01/c](graphdb/queries/interactive-update-1-add-person-companies.rq) [01/e](graphdb/queries/interactive-update-1-add-person-emails.rq) [01/l](graphdb/queries/interactive-update-1-add-person-languages.rq) [01/t](graphdb/queries/interactive-update-1-add-person-tags.rq) [01/u](graphdb/queries/interactive-update-1-add-person-universities.rq) | [02](graphdb/queries/interactive-update-2.rq) | [03](graphdb/queries/interactive-update-3.rq) | [04/f](graphdb/queries/interactive-update-4-add-forum.rq) [04/t](graphdb/queries/interactive-update-4-add-forum-tags.rq) | [05](graphdb/queries/interactive-update-5.rq) | [06/p](graphdb/queries/interactive-update-6-add-post.rq) [06/t](graphdb/queries/interactive-update-6-add-post-tags.rq) | [07/c](graphdb/queries/interactive-update-7-add-comment.rq) [07/t](graphdb/queries/interactive-update-7-add-comment-tags.rq) | [08](graphdb/queries/interactive-update-8.rq) |

## User's guide

1. Grab the driver source code from:

   ```bash
   git clone https://github.com/ldbc/ldbc_snb_driver
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

3. For each implementation, it is possible to (1) create validation parameters, (2) validate against an existing validation parameters, and (3) run the benchmark. In the directory of the implemenetation, set the parameters according to your system configuration in the `.properties` file and run the driver with one of the following scripts:

   ```bash
   driver/create-validation-parameters.sh
   driver/validate.sh
   driver/benchmark.sh
   ```

:warning: *Note that if the default workload contains updates which are persisted in the database. Therefore, the database needs to be re-loaded between steps – otherwise repeated updates would insert duplicate entries.*

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
