# LDBC SNB Umbra implementation

[Umbra](https://umbra-db.com/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the Umbra database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* the `psycopg2` Python library: `scripts/install-dependencies.sh`
* enough free space in the directory `${UMBRA_DATABASE_DIR}` (its default value is specified in `scripts/vars.sh`)

The default configuration of the database (e.g. database name, user, password) is set in the `scripts/vars.sh` file.

## Generating and loading the data set

### Generating the data set

The data sets need to be generated and preprocessed before loading it to the database. To generate such data sets, use the `CsvMergeForeignDynamicActivitySerializer` serializer classes of the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop):

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvMergeForeignDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvMergeForeignDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvMergeForeignStaticSerializer
```

### Loading the data set

1. Set the `${UMBRA_CSV_DIR}` environment variable to point to the data set, e.g.:

    ```bash
    export UMBRA_CSV_DIR=`pwd`/../postgres/test-data/
    ```

2. To start the DBMS, create a database and load the data, run:

    ```bash
    scripts/start-container.sh
    scripts/load.sh
    scripts/start-db.sh
    ```

### Running the benchmark

3. To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

    ```bash
    driver/create-validation-parameters.sh
    driver/validate.sh
    driver/benchmark.sh
    ```

:warning: SNB data sets of **different scale factors require different configurations** for the benchmark runs. Therefore, make sure you use the correct values (update_interleave and query frequencies) based on the files provided in the [`sf-properties` directory](sf-properties/).

* The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this.