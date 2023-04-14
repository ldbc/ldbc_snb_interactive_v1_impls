# LDBC SNB DuckDB implementation

[DuckDB](https://duckdb.org/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

Grab DuckDB:

```bash
scripts/get.sh
```

## Generating the data set

The data sets need to be generated before loading it to the database. No preprocessing is required. To generate the data sets for DuckDB, use the same settings as for PostgreSQL, i.e. the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop)'s `CsvMergeForeign` serializer classes.

## Running the benchmark

Set the following environment variable based on your data source:

```bash
export DUCKDB_CSV_DIR=`pwd`/../postgres/test-data
```

### Loading the data set

Load the data set as follows:

```bash
scripts/load.sh
```

### Running the benchmark driver

The instructions below explain how to run the benchmark driver in one of the three modes (create validation parameters, validate, benchmark). For more details on the driver modes, check the ["Driver modes" section of the main README](../README.md#driver-modes).

#### Create validation parameters

1. Edit the `driver/benchmark.properties` file. Make sure that the `ldbc.snb.interactive.scale_factor`, `ldbc.snb.interactive.updates_dir`, `ldbc.snb.interactive.parameters_dir` properties are set correctly and are in sync.

2. Run the script:

    ```bash
    driver/create-validation-parameters.sh
    ```

#### Validate

1. Edit the `driver/validate.properties` file. Make sure that the `validate_database` property points to the file you would like to validate against.

2. Run the script:

    ```bash
    driver/validate.sh
    ```

#### Benchmark

1. Edit the `driver/benchmark.properties` file. Make sure that the `ldbc.snb.interactive.scale_factor`, `ldbc.snb.interactive.updates_dir`, and `ldbc.snb.interactive.parameters_dir` properties are set correctly and are in sync.

2. Run the script:

    ```bash
    driver/benchmark.sh
    ```

#### Reload between runs

:warning: The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.
