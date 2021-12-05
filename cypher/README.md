# LDBC SNB Neo4j/Cypher implementation

This directory contains the [Neo4j/Cypher](http://www.opencypher.org/) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment for executing this benchmark is as follows: the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the Neo4j database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `$NEO4J_CONTAINER_ROOT` (its default value is specified in `scripts/environment-variables-default.sh`)

## Initializing environment variables

To set and list the default envinment variables, runs:

```bash
. scripts/environment-variables-default.sh
env | grep ^NEO4J_
```

## Generating and loading the data set

### Generating the data set

The data sets need to be generated and preprocessed before loading it to the database. To generate such data sets, use the `CsvComposite` serializer classes of the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop):

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-csv-composite.ini`](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-csv-composite.ini) file of the Datagen repository. For small loading experiments, we recommend using scale factor 0.1, i.e. `snb.interactive.0.1`.

### Preprocessing and loading

Set the following environment variables based on your data source and where you would like to store the converted CSVs:

```bash
export NEO4J_VANILLA_CSV_DIR=`pwd`/test-data/vanilla
export NEO4J_CONVERTED_CSV_DIR=`pwd`/test-data/converted
```

#### Preprocessing

The CSV files produced by Datagen require a bit of preprocessing:

* headers should be replaced with Neo4j-compatible ones (e.g. `:START_ID(Person)|:END_ID(Comment)|creationDate:DATETIME`)
* the first letter of labels should be changed to uppercase (e.g. change `city` to `City`)

The following script performs these changes on the files in `$NEO4J_VANILLA_CSV_DIR` and places the resulting files in `$NEO4J_CONVERTED_CSV_DIR`:

```bash
scripts/convert-csvs.sh
```

#### Load the data set

2. To preprocess, load and index the data, run the following sequence of commands:

:warning: Be careful -- this stops the currently running (containerized) Neo4j database and deletes all of its data.

```bash
scripts/convert-csvs.sh
scripts/stop-neo4j.sh
scripts/delete-neo4j-database.sh
scripts/import-to-neo4j.sh
scripts/start-neo4j.sh
scripts/create-indices.sh
```

3. You can run all these scripts with a single command:

```bash
scripts/load-in-one-step.sh
```

## Running the benchmark

4. To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

```bash
driver/create-validation-parameters.sh
driver/validate.sh
driver/benchmark.sh
```

:warning: SNB data sets of **different scale factors require different configurations** for the benchmark runs. Therefore, make sure you use the correct values (update_interleave and query frequencies) based on the files provided in the [`sf-properties` directory](sf-properties/).

:warning: The default workload contains updates which are persisted in the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Otherwise, repeated updates would insert duplicate entries.
