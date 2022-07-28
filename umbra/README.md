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

## Get the container

The Umbra container is available upon request from TU Munich's Database group. Load it to Docker as follows:

```bash
curl ... | docker load
```

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

### Running the benchmark

3. To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

    ```bash
    driver/create-validation-parameters.sh
    driver/validate.sh
    driver/benchmark.sh
    ```

:warning: The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.
