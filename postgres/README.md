# LDBC SNB Interactive PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the PostgreSQL database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* the `psycopg2` Python library: `scripts/install-dependencies.sh`
* enough free space in the directory `${POSTGRES_DATA_DIR}` (its default value is specified in `scripts/vars.sh`)

## Generating and loading the data set

### Generating the data set

The data sets need to be generated before loading it to the database. No preprocessing is required. To generate data sets for Postgres, use the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop)'s `CsvMergeForeign` serializer classes:

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvMergeForeignDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvMergeForeignDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvMergeForeignStaticSerializer
```

### Configuration

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

:warning: SNB data sets of **different scale factors require different configurations** for the benchmark runs. Therefore, make sure you use the correct values (update_interleave and query frequencies) based on the files provided in the [`sf-properties/` directory](sf-properties/).

* The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.
