# LDBC SNB PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment for executing this benchmark is as follows: the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the PostgreSQL database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `$POSTGRES_DATABASE_DIR` (its default value is specified in `scripts/vars.sh`)

The default configuration of the database (e.g. database name, user, password) is set in the `scripts/vars.sh` file.

## Loading the data and running the benchmark

1. Set the `POSTGRES_CSV_DIR` environment variable to point to the data set, e.g.:

    ```bash
    export POSTGRES_CSV_DIR=`pwd`/test-data/
    ```

2. To start the DBMS, create a database and load the data, run:

    ```bash
    scripts/start.sh
    scripts/create-db.sh
    scripts/load.sh
    ```

    Note that the `load.sh` (re)generates PostgreSQL-specific CSV files for comments (`-postgres.csv`), if either 
    * they do no exist,
    * the source CSV is newer than the generated one, or
    * the user forces to do so by setting the environment variable `POSTGRES_FORCE_REGENERATE=yes`

3. To run the scripts of benchmark framework, edit the `interactive-{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

    ```bash
    ./interactive-benchmark.sh
    ./interactive-create-validation-parameters.sh
    ./interactive-validate.sh
    ```
