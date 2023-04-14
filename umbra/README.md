# LDBC SNB Interactive Umbra implementation

[Umbra](https://umbra-db.com/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the Umbra database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* `libpg5`
* the `psycopg` Python library: `scripts/install-dependencies.sh`
* enough free space in the directory `${UMBRA_DATABASE_DIR}` (its default value is specified in `scripts/vars.sh`)

The default configuration of the database (e.g. database name, user, password) is set in the `scripts/vars.sh` file.

### docker-compose

Alternatively, a docker-compose available to start the Umbra container and a container loading the data. This requires `docker-compose` installed on the host machine. Running Umbra and loading the data can be done by executing:

```bash
docker-compose build && docker-compose up
```

The default environment variables are loaded from `.env`. Change the `UMBRA_CSV_DIR` to point to point to the data set, e.g.

```bash
UMBRA_CSV_DIR=`pwd`/test-data/
```

## Getting the container image

The Umbra Docker container image is available upon request from TU Munich's Database group. Load it to Docker as follows:

```bash
curl ... | docker load
```

## Running the benchmark

### Loading the data set

Umbra uses the same data format at PostgreSQL (`CsvMergeForeign` serializer / `social_network-csv_merge_foreign-sf*` data sets).

1. Set the `${UMBRA_CSV_DIR}` environment variable to point to the data set, e.g.:

    ```bash
    export UMBRA_CSV_DIR=`pwd`/../postgres/test-data/
    ```

2. To start the DBMS, create a database and load the data, run:

    ```bash
    scripts/load-in-one-step.sh
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
