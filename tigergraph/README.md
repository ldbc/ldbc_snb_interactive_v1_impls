# LDBC SNB TigerGraph implementation

This directory contains the [TigerGraph/GSQL](https://www.tigergraph.com/) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, 
while TigerGraph database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+

## Building the project

In order to build the TigerGraph implementation, it is advised to use the [Maven](http://maven.apache.org/) build tool from [root directory of the project](../):

```bash
mvn package -DskipTests -Ptigergraph -U
```

Please note the `-U` flag. The project uses a snapshot version of TigerGraph client, and the switch helps to ensure that this artifact gets resolved properly.

## Configuration

In order to run the benchmark, the following configuration steps need to be performed:
1. Preparing a dataset
2. Setting up TigerGraph database (starting the cluster, running all the required services)
3. Loading the data set into the database (defining data loading jobs, running them)
4. Defining the queries to be executed (defining queries, installing them into the DB)
5. Creating indices (**TBD**)

There are scripts provided in [setup](./setup) directory that can be used to perform steps 2-5 
and some helper scripts in [scripts](./scripts) to manage TigerGraph in a Docker container.

## Preparing a dataset

In this section, we will describe how to obtain the data set and load it into the database.

The data can be generated using the data generator tool or downloaded from the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop).

From the pre-generated data sets in the [SURF/CWI data repository](https://hdl.handle.net/11112/e6e00558-a2c3-9214-473e-04a16de09bf8), use the ones named ` social_network-csv_composite-longdateformatter-sf*`.

### Generating a dataset

The data sets need to be generated and preprocessed before loading it to the database.
To generate such data sets, use the [Hadoop-based Datagen's](https://github.com/ldbc/ldbc_snb_datagen_hadoop) `CsvComposite` serializer classes (with the default date formatter):

```ini
ldbc.snb.datagen.serializer.dateFormatter:ldbc.snb.datagen.util.formatter.LongDateFormatter
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

Please note, that the loading procedure assumes that person data about languages and emails are combined into a person record.
An example configuration for scale factor 1 is given in the [`params-csv-composite-longdateformatter.ini`](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-csv-composite-longdateformatter.ini) file of the Datagen repository.

### Preprocessing and loading

TigerGraph uses a mechanism called "loading jobs" for data import.
There are three loading job defined for this purpose:
* [load_static_1.gsql](./setup/load_static_1.gsql)
* [load_static_2.gsql](./setup/load_static_2.gsql)
* [load_dynamic_1.gsql](./setup/load_dynamic_1.gsql)

[setup.sh](./setup/setup.sh) contains the commands to run the loading jobs. It takes two arguments:
* path to data
* path to queries

Please, note that the loading procedure assumes that the data files are present on the TigerGraph machine.
They need to be uploaded there in advance (or mounted as a volume).

### Exemplary setup procedure

This section explains how to set up the database for the benchmarking **using Docker** containers.

The instruction assumes that you are starting in the `tigergraph` subfolder of the project root directory (`ldbc_snb_interactive_v1_impls/tigergraph`).

#### Prepare the dataset

Set the following environment variables based on your data source (the example below uses the test data set SF-0.003):

```bash
export TIGERGRAPH_DATA_DIR=`pwd`/test-data/social_network
```

## Running the benchmark

#### Start the database and load the data
:warning: Be careful -- this stops the currently running (containerized) TigerGraph database and deletes all of its data.

To start the database, run the following [script](./scripts/start.sh):

```bash
./scripts/stop.sh # if you have an existing TG database
# wait several seconds for docker to reset 
./scripts/start.sh
```

It will start a single node TigerGraph database and all required services. Note the license in the container is a trial license supporting at most 100GB data. For benchmarks on SF-100 and larger, you need to obtain a license after running `start.sh`. We have an example command in the end of `start.sh`.

To set up the database, run the following [script](./scripts/setup.sh):

```bash
./scripts/setup.sh
```

This step may take a while (several minutes), as it is responsible for defining the queries, loading jobs, loading the data and installing the queries. After the data is ready, you can explore the graph using TigerGraph GraphStudio in the browser: `http://localhost:14240/`. By default, the docker terminal can be accessed via `ssh tigergraph@localhost -p 14022` with password tigergraph, or using Docker command `docker exec --user tigergraph -it snb-interactive-tigergraph bash`.

The above scripts can be executed with a single command:
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
