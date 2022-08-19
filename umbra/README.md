# LDBC SNB Interactive Umbra implementation

[Umbra](https://umbra-db.com/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the Umbra database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* `libpq5` 
* the `psycopg` Python library: `scripts/install-dependencies.sh`
* enough free space in the directory `${UMBRA_DATA_DIR}` (its default value is specified in `scripts/vars.sh`)

## Generating and loading the data set

### Generating the data set

The Umbra implementation uses the same format as the [PostgreSQL implementation](../postgres/README.md#generating-and-loading-the-data-set).

## Configuration

The default configuration of the database (e.g. database name, user, password) is set in the `scripts/vars.sh` file.

### Loading the data set

1. Set the `${UMBRA_CSV_DIR}` environment variable.

    * To use a locally generated data set, set the `${LDBC_SNB_DATAGEN_DIR}` and `${SF}` environment variables and run:

        ```bash
        export UMBRA_CSV_DIR=${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/graphs/csv/bi/composite-merged-fk/
        ```

        Or, simply run:

        ```bash
        . scripts/use-datagen-data-set.sh
        ```

    * To download and use the sample data set, run:

        ```bash
        scripts/get-sample-data-set.sh
        . scripts/use-sample-data-set.sh
        ```

2. To start the DBMS, create a database and load the data, run:

    ```bash
    scripts/load-in-one-step.sh
    ```

### Running the benchmark

3. To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

    ```bash
    driver/create-validation-parameters.sh
    driver/validate.sh
    driver/benchmark.sh
    ```

:warning: The default workload contains updates which change the state of the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.
