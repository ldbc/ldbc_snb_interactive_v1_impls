![LDBC logo](ldbc-logo.png)
# LDBC SNB Interactive v2 workload implementations

[![Build Status](https://circleci.com/gh/ldbc/ldbc_snb_interactive_v1_impls.svg?style=svg)](https://circleci.com/gh/ldbc/ldbc_snb_interactive_v1_impls)

This repository contains reference implementations of the LDBC Social Network Benchmark's Interactive v2 workload. See details on the benchmark, see the [SIGMOD 2015 paper](https://ldbcouncil.org/docs/papers/ldbc-snb-interactive-sigmod-2015.pdf), [specification on GitHub Pages](https://ldbcouncil.org/ldbc_snb_docs/), and [specification on arXiv](https://arxiv.org/pdf/2001.02299.pdf).

To get started with the LDBC SNB benchmarks, check out our introductory presentation: [The LDBC Social Network Benchmark](https://docs.google.com/presentation/d/1NilxSrKQnFq4WzWMY2-OodZQ2TEksKzKBmgB20C_0Nw/) ([PDF](https://ldbcouncil.org/docs/presentations/ldbc-snb-2022-11.pdf)).

:warning:
This workload is still under design. If you are looking for a stable, auditable version, use the [Interactive v1 workload](https://github.com/ldbc/ldbc_snb_interactive_v1_impls).

## Notes

:warning: Please keep in mind the following when using this repository.

* The goal of the implementations in this repository is to serve as **reference implementations** which other implementations can cross-validated against. Therefore, our primary objective was readability and not absolute performance when formulating the queries.

* The default workload contains updates which change the state of the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.

## Implementations

We provide two reference implementations:

* [Neo4j (Cypher) implementation](cypher/README.md)
* [PostgreSQL (SQL) implementation](postgres/README.md)

Additional implementations:

* [Microsoft SQL Server (Transact-SQL) implementation](mssql/README.md)
* [Umbra (SQL) implementation](umbra/README.md)

For detailed instructions, consult the READMEs of the projects.

## User's guide

### Building the project
This project uses Java 17.

To build the entire project, run:

```bash
scripts/build.sh
```

To build a subset of the projects, e.g. to build the PostgreSQL implementation, run its individual build script:

```bash
postgres/scripts/build.sh
```

### Inputs

The benchmark framework relies on the following inputs produced by the [SNB Datagen's new (Spark) version](https://github.com/ldbc/ldbc_snb_datagen_spark/).

Currently, the initial data set, update streams, and parameters can generated with the following command:

```bash
export SF= #The scale factor to generate
export LDBC_SNB_DATAGEN_DIR= # Path to the LDBC SNB datagen directory
export LDBC_SNB_DATAGEN_MAX_MEM= #Maximum memory the datagen could use, e.g. 16G
export LDBC_SNB_DRIVER_DIR= # Path to the LDBC SNB driver directory
export DATA_INPUT_TYPE=parquet
# If using the Docker Datagen version, set the env variable:
export USE_DATAGEN_DOCKER=true

scripts/generate-all.sh
```

### Pre-generate data sets

[Pre-generated SF1-SF300 data sets](snb-interactive-pre-generated-data-sets.md) are available.

### Loading the data

Select the system to be tested, e.g. [PostgreSQL](postgres/).
Load the data set as described in the README file of the selected system.
For most systems, this involves setting an environment variable to the correct location and invoking the `scripts/load-in-one-step.sh` script.

### Driver modes

For each implementation, it is possible to perform the run in one of the [SNB driver's](https://github.com/ldbc/ldbc_snb_interactive_driver) three modes.
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
        * The goal of the benchmark is to achieve the best (lowest possible) `time_compression_ratio` value while ensuring that the 95% on-time requirement is kept (i.e. 95% of the queries can be started within 1 second of their scheduled time). If your benchmark run returns "failed schedule audit", increase this number (which lowers the time compression rate) until it passes.
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
1. Add the required glue code to the Java driver that allows parameterized execution of queries and operators.
1. Implement the complex and short reads queries (21 in total).
1. Implement the insert and delete operations (16 in total).
1. Test the implementation against the reference implementations using various scale factors.
1. Optimize the implementation.

## Preparing for an audited run

Implementations of the Interactive workload can be audited by a certified LDBC auditor.
The [Auditing Policies chapter of the specification](https://ldbcouncil.org/ldbc_snb_docs/ldbc-snb-specification.pdf) describes the auditing process and the required artifacts.

If you plan to get your system audited, please reach to the [LDBC Steering Committee](https://ldbcouncil.org/organizational-members/).

:warning: Audited runs are currently only possible with the [v1 version](https://github.com/ldbc/ldbc_snb_interactive_v1_impls).
