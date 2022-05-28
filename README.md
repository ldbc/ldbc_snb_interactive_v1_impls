![LDBC logo](ldbc-logo.png)
# LDBC SNB Interactive workload implementations

[![Build Status](https://circleci.com/gh/ldbc/ldbc_snb_interactive_impls.svg?style=svg)](https://circleci.com/gh/ldbc/ldbc_snb_interactive_impls)

Reference implementations of the LDBC Social Network Benchmark's Interactive workload ([paper](https://homepages.cwi.nl/~boncz/snb-challenge/snb-sigmod.pdf), [specification on GitHub pages](https://ldbcouncil.org/ldbc_snb_docs/), [specification on arXiv](https://arxiv.org/pdf/2001.02299.pdf)).

To get started with the LDBC SNB benchmarks, check out our introductory presentation: [The LDBC Social Network Benchmark](https://docs.google.com/presentation/d/1p-nuHarSOKCldZ9iEz__6_V3sJ5kbGWlzZHusudW_Cc/) ([PDF](https://ldbcouncil.org/docs/presentations/ldbc-snb-2021-12.pdf)).

## Notes

:warning: There are some quirks to using this repository:

* The goal of the implementations in this repository is to serve as **reference implementations** which other implementations can cross-validated against. Therefore, our primary objective was readability and not absolute performance when formulating the queries.

* The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.

* We expect most systems-under-test to use multi-threaded execution for their benchmark runs. **To allow running the updates on multiple threads, the update stream files need to be partitioned accordingly by the generator.** We have [pre-generated](#benchmark-data-sets) these for frequent partition numbers (1, 2, ..., 1024 and 24, 48, 96, ..., 768) and scale factors up to 1000.

## Implementations

We provide two reference implementations:

* [Neo4j (Cypher) implementation](cypher/README.md)
* [PostgreSQL (SQL) implementation](postgres/README.md)

Additional implementations:

* [DuckDB (SQL) implementation](duckdb/README.md)
* [TigerGraph (GSQL) implementation](tigergraph/README.md)
* [Umbra (SQL) implementation](umbra/README.md)

For detailed instructions, consult the READMEs of the projects.

To build a subset of the projects, use Maven profiles, e.g. to build the reference implementations, run:

```bash
mvn clean package -DskipTests -Pcypher,postgres
```

## User's guide

### Building the project

To build the project, run:

```bash
scripts/build.sh
```

### Inputs

The benchmark framework relies on the following inputs produced by the [SNB Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop/):

* **Initial data set:** the SNB graph in CSV format (`social_network/{static,dynamic}`)
* **Update streams:** the input for the update operations (`social_network/updateStream_*.csv`)
* **Substitution parameters:** the input parameters for the complex queries. It is produced by the Datagen (`substitution_parameters/`)

### Driver modes

For each implementation, it is possible to perform to perform the run in one of the [SNB driver's](https://github.com/ldbc/ldbc_snb_interactive_driver) three modes.
All three should be started withe the initial data set loaded to the database.

1. Create validation parameters with the `driver/create-validation-parameters.sh` script.

    * **Inputs:**
        * The query substitution parameters are taken from the directory set in `ldbc.snb.interactive.parameters_dir` configuration property.
        * The update streams are the `updateStream_0_0_{forum,person}.csv` files from the location set in the `ldbc.snb.interactive.updates_dir` configuration property.
        * For this mode, the query frequencies are set to a uniform `1` value to ensure the best average test coverage.
    * **Output:** The results will be stored in the validation parameters file (e.g. `validation_params.csv`) file set in the `create_validation_parameters` configuration property.
    * **Parallelism:** The execution must be single-threaded to ensure a deterministic order of operations.

2. Validate against existing validation parameters with the `driver/validate.sh` script.

    * **Input:**
        * The query substitution parameters are taken from the validation parameters file (e.g. `validation_params.csv`) file set in the `validate_database` configuration property.
        * The update operations are also based on the content of the validation parameters file.
    * **Output:**
        * The validation either passes of fails.
        * The per query results of the validation are printed to the console.
        * If the validation failed, the results are saved to the `validation_params-failed-expected.json` and `validation_params-failed-actual.json` files.
    * **Parallelism:** The execution must be single-threaded to ensure a deterministic order of operations.

3. Run the benchmark with the `driver/benchmark.sh` script.

    * **Inputs:**
        * The query substitution parameters are taken from the directory set in `ldbc.snb.interactive.parameters_dir` configuration property.
        * The update streams are the `updateStream_*_{forum,person}.csv` files from the location set in the `ldbc.snb.interactive.updates_dir` configuration property.
            * To get *2n* write threads, the framework requires *n* `updateStream_*_forum.csv` and *n* `updateStream_*_person.csv` files.
            * If you are generating the data sets from scratch, set `ldbc.snb.datagen.serializer.numUpdatePartitions` to *n* in the [data generator](https://github.com/ldbc/ldbc_snb_datagen_hadoop) to get produce these.
        * The goal of the benchmark is the achieve the best (lowest possible) `time_compression_ratio` value while ensuring that the 95% on-time requirement is kept (i.e. 95% of the queries can be started within 1 second of their scheduled time). If your benchmark run returns "failed schedule audit", increase this number (which lowers the time compression rate) until it passes.
        * Set the `thread_count` property to the size of the thread pool for read operations.
        * For audited benchmarks, ensure that the `warmup` and `operation_count` properties are set so that the warmup and benchmark phases last for 30+ minutes and 2+ hours, respectively.
    * **Output:**
        * Passed or failed the "schedule audit" (the 95% on-time requirement).
        * The throughput achieved in the run (operations/second).
        * The detailed results of the benchmark are printed to the console and saved in the `results/` directory.
    * **Parallelism:** Multi-threaded execution is recommended to achieve the best result.

For more details on validating and benchmarking, visit the [driver wiki](https://github.com/ldbc/ldbc_snb_interactive_driver/wiki).

## Developer's guide

To create a new implementation, it is recommended to use one of the existing ones: the Neo4j implementation for graph database management systems and the PostgreSQL implementation for RDBMSs.

The implementation process looks roughly as follows:

1. Create a bulk loader which loads the initial data set to the database.
2. Implement the complex and short reads queries (22 in total).
3. Implement the 7 update queries.
4. Test the implementation against the reference implementations using various scale factors.
5. Optimize the implementation.

## Data sets

### Benchmark data sets

To generate the benchmark data sets, use the [Hadoop-based LDBC SNB Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop/releases/tag/v0.3.7-SNAPSHOT).

The key configurations are the following:

* `ldbc.snb.datagen.generator.scaleFactor`: set this to `snb.interactive.${SCALE_FACTOR}` where `${SCALE_FACTOR}` is the desired scale factor
* `ldbc.snb.datagen.serializer.numUpdatePartitions`: set this to the number of write threads used in the benchmark runs
* serializers: set these to the required format, e.g. the ones starting with `CsvMergeForeign` or `CsvComposite`
  * `ldbc.snb.datagen.serializer.dynamicActivitySerializer`
  * `ldbc.snb.datagen.serializer.dynamicPersonSerializer`
  * `ldbc.snb.datagen.serializer.staticSerializer`

### Pre-generated data sets

Producing large-scale data sets requires non-trivial amounts of memory and computing resources (e.g. SF100 requires 24GB memory and takes about 4 hours to generate on a single machine).
To mitigate this, we have pregenerated data sets using 9 different serializers and the update streams using 17 different partition numbers:

* Serializers: csv_basic, csv_basic-longdateformatter, csv_composite, csv_composite-longdateformatter, csv_composite_merge_foreign, csv_composite_merge_foreign-longdateformatter, csv_merge_foreign, csv_merge_foreign-longdateformatter, ttl
* Partition numbers: 2^k (1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024) and 6×2^k (24, 48, 96, 192, 384, 768).

The data sets are available at the [SURF/CWI data repository](https://hdl.handle.net/11112/e6e00558-a2c3-9214-473e-04a16de09bf8).

### Test data set

The test data sets are placed in the `cypher/test-data/` directory for Neo4j and in the `postgres/test-data/` for the SQL systems.

To generate a data set with the same characteristics, see the [documentation on generating the test data set](test-data.md).

## Preparing for an audited run

Implementations of the Interactive workload can be audited by a certified LDBC auditor.
The [Auditing Policies chapter](http://ldbcouncil.org/ldbc_snb_docs/ldbc-snb-specification.pdf#chapter.7) of the specification describes the auditing process and the required artifacts.

### Determining the best TCR

1. Select a scale factor and configure the `driver/benchmark.properties` file as described in the [Driver modes](#driver-modes) section.
2. Load the data set with `scripts/load-in-one-step.sh`.
3. Create a backup with `scripts/backup-database.sh`.
4. Run the `driver/determine-best-tcr.sh`.
5. Once the "best TCR" value has been determined, test it with a full workload (at least 0.5h for warmup operation and at least 2h of benchmark time), and make further adjustments if necessary.

### Recommendations

We have a few recommendations for creating audited implementations. (These are not requirements – implementations are allowed to deviate from these recommendations.)

* The implementation should target a popular Linux distribution (e.g. Ubuntu LTS, CentOS, Fedora).
* Use a containerized setup, where the DBMS is running in a Docker container.
* Instead of a specific hardware, target a cloud virtual machine instance (e.g. AWS `r5d.12xlarge`). Both bare-metal and regular instances can be used for audited runs.
