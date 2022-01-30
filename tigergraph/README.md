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

Please, note the `-U` flag. The project uses a snapshot version of TigerGraph client, and the switch helps to ensure that this artifact gets resolved properly.


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
There are also some pre-generated data sets available in the [LDBC SNB benchmark data repository]() **TODO: add link.

### Generating a dataset
The data sets need to be generated and preprocessed before loading it to the database.
To generate such data sets, use the [Hadoop-based Datagen's](https://github.com/ldbc/ldbc_snb_datagen_hadoop) `CsvComposite` serializer classes with the `LongDateFormatter` date formatter:

```ini
ldbc.snb.datagen.serializer.dateFormatter:ldbc.snb.datagen.util.formatter.LongDateFormatter

ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

    Please note, that the loading procedure assumes that person data about langages and emails are cobined into a person record.
    Please, use serializers like in [the follwing example](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-csv-composite-longdateformatter.ini).
    Serializers like `CsvBasicDynamicPersonSerializer` produce separate files for persons' emails and languages.

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

The instruction assumes that you are starting in the `tigergraph` subfolder of the project root directory (`ldbc_snb_interactive/tigergraph`).

#### Prepare the dataset
Generate or download the dataset. Place it under `./tigergraph/scratch/data` directory.
After that, `tigergraph/scratch/data/social_network` should hold `dynamic` and `static` datasets.

#### Start the database
To start the database, run the following [script](./scripts/start.sh):
```
./scripts/start.sh
```

It will start a single node TigerGraph database and all required services.

You can verify the readiness of TigerGraph by visiting it's console in the browser: `http://localhost:14240/`

#### Load the data
To set up the database, run the following [script](./scripts/setup.sh):
```
./scripts/setup.sh
```
It leverages the fact, that the TigerGraph container has _scratch/data_, _setup_ and _queries_ directories mounted as volumes.
(The configuration is stored in [vars.sh](./scripts/vars.sh).)

This step may take a while (several minutes), as it is responsible for defining the queries, loading jobs, loading the data
and installing (optimizing and compiling on the server) the queries.

## Running the benchmark

To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files,
then run their script, one of:

```bash
driver/create-validation-parameters.sh
driver/validate.sh
driver/benchmark.sh
```

:warning: SNB data sets of **different scale factors require different configurations** for the benchmark runs. Therefore, make sure you use the correct values (update_interleave and query frequencies) based on the files provided in the [`sf-properties/` directory](../sf-properties).

> **Warning:** Note that if the default workload contains updates which are persisted in the database. Therefore, the database needs to be re-loaded between steps – otherwise repeated updates would insert duplicate entries.*

