# LDBC SNB GraphDB/SPARQL implementation

This directory contains the [GraphDB](https://www.ontotext.com/products/graphdb/) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment for executing this benchmark is as follows: the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the GraphDB database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `GRAPHDB_CONTAINER_ROOT` (its default value is specified in `scripts/vars.sh`)

## Generating and loading the data set

### Using pre-generated data sets

From the pre-generated data sets in the [SURF/CWI data repository](https://hdl.handle.net/11112/e6e00558-a2c3-9214-473e-04a16de09bf8), use the ones named `social_network_ttl_sf*`.

### Generating the data set

The data sets need to be generated and preprocessed before loading it to the database. To generate such data sets, use the `TurtleDynamicActivitySerializer` serializer classes of the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop):

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-ttl.ini`](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-ttl.ini) file of the Datagen repository. For small loading experiments, you can use scale factor 0.1, i.e. `snb.interactive.0.1`.

> The result of the execution will generate three .ttl files `social_network_activity_0_0.ttl`, `social_network_person_0_0.ttl` and `social_network_static_0_0.ttl`

## Running the benchmark

### Preprocessing and loading

Change the following environment variables based on your data source.

1. Set the `GRAPHDB_IMPORT_TTL_DIR` environment variable to point to the generated data set. Its default value points to the example data set under the `test-data` directory:

```bash
export GRAPHDB_IMPORT_TTL_DIR=`pwd`/test-data/
```

2. You can change the GraphDB repository configuration pointed by `GRAPHDB_REPOSITORY_CONFIG_FILE` environment variable which by default uses the example configuration in `config` directory:

```bash
export GRAPHDB_REPOSITORY_CONFIG_FILE=`pwd`/config/graphdb-repo-config.ttl
```

### Loading the data set

3. To start GraphDB and load the data, run the following scripts:

:warning: Note that this will stop the currently running (containerized) GraphDB and delete all of its data.

```bash
scripts/stop-graphdb.sh
scripts/delete-graphdb-database.sh
scripts/graphdb-importrdf.sh
scripts/start-graphdb.sh
```

> Or run all these scripts with a single command:
>  
> ```bash
>    scripts/one-step-load.sh
> ```

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
