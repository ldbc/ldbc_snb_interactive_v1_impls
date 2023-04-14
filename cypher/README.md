# LDBC SNB Interactive Neo4j/Cypher implementation

This directory contains the [Neo4j/Cypher](http://www.opencypher.org/) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the Neo4j database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `${NEO4J_CONTAINER_ROOT}` (its default value is specified in `scripts/vars.sh`)

## Configuration

The default environment variables (e.g. Neo4j version, container name, etc.) are stored in `scripts/vars.sh`. Adjust these as you see fit.

## Generating and loading the data set

### Using pre-generated data sets

From the pre-generated data sets in the [SURF/CWI data repository](https://hdl.handle.net/11112/e6e00558-a2c3-9214-473e-04a16de09bf8), use the ones named `social_network-csv_composite-longdateformatter-sf*`.

### Generating the data set

The data sets need to be generated and preprocessed before loading it to the database. To generate such data sets, use the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop)'s `CsvComposite` serializer classes with the `LongDateFormatter` date formatter:

```ini
ldbc.snb.datagen.serializer.dateFormatter:ldbc.snb.datagen.util.formatter.LongDateFormatter

ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-csv-composite-longdateformatter.ini`](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-csv-composite-longdateformatter.ini) file of the Datagen repository.

## Running the benchmark

Set the following environment variables based on your data source and where you would like to store the converted CSVs:

```bash
export NEO4J_VANILLA_CSV_DIR=`pwd`/test-data/vanilla
export NEO4J_CONVERTED_CSV_DIR=`pwd`/test-data/converted
```

### Loading the data set

To load the data set, run the following script:

```bash
scripts/load-in-one-step.sh
```

This preprocesses the CSVs in `${NEO4J_VANILLA_CSV_DIR}` and places the resulting CSVs in `${NEO4J_CONVERTED_CSV_DIR}`, stops any running Neo4j database instances, loads the database and starts it.

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

:warning: The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this. Alternatively, e.g. if you lack sudo rights, use Neo4j's built-in dump and load features through the `scripts/backup-neo4j.sh` and `scripts/restore-neo4j.sh` scripts.
