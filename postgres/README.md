# LDBC SNB Interactive PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the PostgreSQL database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* `libpq5` 
* the `psycopg` Python library: `scripts/install-dependencies.sh`
* enough free space in the directory `${POSTGRES_DATA_DIR}` (its default value is specified in `scripts/vars.sh`)

### docker-compose

Alternatively, a docker-compose specification is available to start the PostgreSQL container and a container loading the data. This requires `docker-compose` installed on the host machine. Running Postgres and loading the data can be done by executing:

```bash
docker-compose build && docker-compose up
```

The default environment variables are loaded from `.env`. Change the `POSTGRES_CSV_DIR` to point to point to the data set, e.g.

```bash
POSTGRES_CSV_DIR=`pwd`/social-network-sf0.003-bi-composite-merged-fk/
```

To persist the data by storing the database outside a Docker volume, uncomment the following lines in the `docker-compose.yml` file:

```yaml
- type: bind
  source: ${POSTGRES_DATA_DIR}
  target: /var/lib/postgresql/data
```

## Generating and loading the data set

### Generating the data set

The PostgreSQL `composite-merged-fk` CSV layout, with headers and without quoted fields.
To generate data that confirms this requirement, run Datagen without any layout or formatting arguments (`--explode-*` or `--format-options`).

In Datagen's directory (`ldbc_snb_datagen_spark`), issue the following commands. We assume that the Datagen project is built and the `${PLATFORM_VERSION}`, `${DATAGEN_VERSION}` environment variables are set correctly.

```bash
export SF=desired_scale_factor
export LDBC_SNB_DATAGEN_MAX_MEM=available_memory
```

```bash
rm -rf out-sf${SF}/
tools/run.py \
    --cores $(nproc) \
    --memory ${LDBC_SNB_DATAGEN_MAX_MEM} \
    ./target/ldbc_snb_datagen_${PLATFORM_VERSION}-${DATAGEN_VERSION}.jar \
    -- \
    --format csv \
    --scale-factor ${SF} \
    --mode bi \
    --output-dir out-sf${SF}
```

## Configuration

The default configuration of the database (e.g. database name, user, password) is set in the `scripts/vars.sh` file.

### Loading the data set

1. Set the `${POSTGRES_CSV_DIR}` environment variable to point to the data set, e.g.:

    ```bash
    export POSTGRES_CSV_DIR=`pwd`/test-data/
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
