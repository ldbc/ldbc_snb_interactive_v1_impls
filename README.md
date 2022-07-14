![LDBC logo](ldbc-logo.png)
# LDBC SNB Interactive workload implementations

[![Build Status](https://circleci.com/gh/ldbc/ldbc_snb_interactive_impls.svg?style=svg)](https://circleci.com/gh/ldbc/ldbc_snb_interactive_impls)

:warning:
The Interactive workload is currently being renewed to accommodate new features such as deletions and larger scale factors.
If you are looking for a stable, auditable version, use one of the old versions:
[v0.3.6](https://github.com/ldbc/ldbc_snb_interactive_impls/releases/tag/0.3.6) or
[v1.0.0](https://github.com/ldbc/ldbc_snb_interactive_impls/releases/tag/1.0.0).

Reference implementations of the LDBC Social Network Benchmark's Interactive workload ([paper](https://homepages.cwi.nl/~boncz/snb-challenge/snb-sigmod.pdf), [specification on GitHub pages](https://ldbcouncil.org/ldbc_snb_docs/), [specification on arXiv](https://arxiv.org/pdf/2001.02299.pdf)).

To get started with the LDBC SNB benchmarks, check out our introductory presentation: [The LDBC Social Network Benchmark](https://docs.google.com/presentation/d/1p-nuHarSOKCldZ9iEz__6_V3sJ5kbGWlzZHusudW_Cc/) ([PDF](https://ldbcouncil.org/docs/presentations/ldbc-snb-2021-12.pdf)).

## Notes

:warning: There are some quirks to using this repository:

* The goal of the implementations in this repository is to serve as **reference implementations** which other implementations can cross-validated against. Therefore, our primary objective was readability and not absolute performance when formulating the queries.

* The default workload contains updates which change the state of the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.

## Implementations

We provide two reference implementations:

* [Neo4j (Cypher) implementation](cypher/README.md)
* [PostgreSQL (SQL) implementation](postgres/README.md)
* [Microsoft SQL Server (Transact-SQL) implementation](mssql/README.md)

Additional implementations -- currently only supported in the [old version (v1.0.0) of SNB Interactive](https://github.com/ldbc/ldbc_snb_interactive_impls/releases/tag/1.0.0):

* [DuckDB (SQL) implementation](duckdb/README.md)
* [TigerGraph (GSQL) implementation](tigergraph/README.md)
* [Umbra (SQL) implementation](umbra/README.md)

For detailed instructions, consult the READMEs of the projects.

To build a subset of the projects, use Maven profiles, e.g. to build the PostgreSQL implementation, run:

```bash
mvn clean package -DskipTests -Ppostgres
```

## User's guide

### Building the project

To build the project, run:

```bash
scripts/build.sh
```

### Inputs

The benchmark framework relies on the following inputs produced by the [SNB Datagen's new (Spark) version](https://github.com/ldbc/ldbc_snb_datagen_spark/).

Currently, the initial data set, update streams, and parameters can generated with the following command:

```bash
export SF=
export LDBC_SNB_DATAGEN_DIR=
export LDBC_SNB_DATAGEN_MAX_MEM=
export PLATFORM_VERSION=
export DATAGEN_VERSION=
export LDBC_SNB_DRIVER_DIR=
scripts/generate-all.sh
```

<!--
* **Initial data set:** the SNB graph in CSV format (`social_network/{static,dynamic}`)
* **Update streams:** the input for the update operations (`social_network/updateStream_*.csv`)
* **Substitution parameters:** the input parameters for the complex queries. It is produced by the Datagen (`substitution_parameters/`)
-->

### Driver modes

For each implementation, it is possible to perform to perform the run in one of the [SNB driver's](https://github.com/ldbc/ldbc_snb_interactive_driver) three modes.
All of these runs should be started with the initial data set loaded to the database.

1. Create validation parameters with the `driver/create-validation-parameters.sh` script.

    * **Inputs:**
        * The query substitution parameters are taken from the directory set in `ldbc.snb.interactive.parameters_dir` configuration property.
        * The update streams are the files from the `inserts` and `deletes` directories in the directory `ldbc.snb.interactive.updates_dir` configuration property.
        * For this mode, the query frequencies are set to a uniform `1` value to ensure the best average test coverage. [TODO]
    * **Output:** The results will be stored in the validation parameters file (e.g. `validation_params.json`) file set in the `validate_database` configuration property.
    * **Parallelism:** The execution must be single-threaded to ensure a deterministic order of operations.

2. Validate against existing validation parameters with the `driver/validate.sh` script.

    * **Input:**
        * The query substitution parameters are taken from the validation parameters file (e.g. `validation_params.json`) file set in the `validate_database` configuration property.
        * The update operations are also based on the content of the validation parameters file.
    * **Output:**
        * The validation either passes of fails.
        * The per query results of the validation are printed to the console.
        * If the validation failed, the results are saved to the `validation_params-failed-expected.json` and `validation_params-failed-actual.json` files.
    * **Parallelism:** The execution must be single-threaded to ensure a deterministic order of operations.

3. Run the benchmark with the `driver/benchmark.sh` script.

    * **Inputs:**
        * The query substitution parameters are taken from the directory set in `ldbc.snb.interactive.parameters_dir` configuration property.
        * The update streams are the files from the `inserts` and `deletes` directories in the directory `ldbc.snb.interactive.updates_dir` configuration property.
        * The goal of the benchmark is the achieve the best (lowest possible) `time_compression_ratio` value while ensuring that the 95% on-time requirement is kept (i.e. 95% of the queries can be started within 1 second of their scheduled time). If your benchmark run returns "failed schedule audit", increase this number (which lowers the time compression rate) until it passes.
        * Set the `thread_count` property to the size of the thread pool for read operations.
        * For audited benchmarks, ensure that the `warmup` and `operation_count` properties are set so that the warmup and benchmark phases last for 30+ minutes and 2+ hours, respectively.
    * **Output:**
        * Passed or failed the "schedule audit" (the 95% on-time requirement).
        * The throughput achieved in the run (operations/second).
        * The detailed results of the benchmark are printed to the console and saved in the `results/` directory.
    * **Parallelism:** Multi-threaded execution is recommended to achieve the best result.

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

To generate the benchmark data sets, use the [Spark-based LDBC SNB Datagen](https://github.com/ldbc/ldbc_snb_datagen_spark/). Detailed instructions are given for each tool.

### Pre-generated data sets

Pre-generated data sets are currently not available.

## Preparing for an audited run

:warning: Audited runs are currently only possible with the old version. The new version of Interactive (with deletes and larger SFs) will be released in Q4 2022.

Implementations of the Interactive workload can be audited by a certified LDBC auditor.
The [Auditing Policies chapter](https://ldbcouncil.org/ldbc_snb_docs/ldbc-snb-specification.pdf#chapter.7) of the specification describes the auditing process and the required artifacts.

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
