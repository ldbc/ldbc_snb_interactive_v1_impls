# LDBC SNB GraphDB implementation

This directory contains the [GraphDB](https://www.ontotext.com/products/graphdb/) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment for executing this benchmark is as follows: the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the GraphDB database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `GRAPHDB_CONTAINER_ROOT` (its default value is specified in `scripts/environment-variables-default.sh`)

## Generating and loading the data set

### Generating the data set

The data sets need to be generated and preprocessed before loading it to the database. To generate such data sets, use the `TurtleDynamicActivitySerializer` serializer classes of the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop):

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.turtle.TurtleStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-ttl.ini`](https://github.com/ldbc/ldbc_snb_datagen_hadoop/blob/main/params-ttl.ini) file of the Datagen repository. For small loading experiments, you can use scale factor 0.1, i.e. `snb.interactive.0.1`.

> The result of the execution will generate three .ttl files `social_network_activity_0_0.ttl`, `social_network_person_0_0.ttl` and `social_network_static_0_0.ttl`

### Initializing environment variables

To set and list the default environment variables, run:

```bash
. scripts/environment-variables-default.sh
env | grep ^GRAPHDB_
```

After that you need to change the following environment variables based on your data source.

1. Set the `GRAPHDB_IMPORT_TTL_DIR` environment variable to point to the generated data set. Its default value points to the example data set under the `test-data` directory:

```bash
export GRAPHDB_IMPORT_TTL_DIR=`pwd`/test-data/
```

2. You can change the GraphDB repository configuration pointed by `GRAPHDB_REPOSITORY_CONFIG_FILE` environment variable which by default uses the example configuration in `config` directory:

```bash
export GRAPHDB_REPOSITORY_CONFIG_FILE=`pwd`/config/graphdb-repo-config.ttl
```

### Running GraphDB in a Docker container

Check [Docker Hub Images](https://hub.docker.com/r/ontotext/graphdb/) for information on how to use the images. Note that to use GraphDB EE or SE docker images, you will need a license.

Currently, there are no public images for GraphDB Free and you will have to build one following the above instructions:

- Register on the Ontotext website for the GraphDB Free edition. Download the zip distribution and set `GRAPHDB_DIST_ZIP_FOLDER_PATH` environment variable to point to `graphdb-${GRAPHDB_VERSION}-ee-dist.zip` folder.

```bash
export GRAPHDB_DIST_ZIP_FOLDER_PATH=
```

- To build the docker image, run:

```bash
scripts/build-graphdb-free-image.sh
```

### Loading the data set

3. To start GraphDB and load the data, run the following scripts:

:warning: Note that this will stop the currently running (containerized) GraphDB and delete all of its data.

```bash
scripts/stop-graphdb.sh
scripts/delete-graphdb-database.sh
scripts/graphdb-loadrdf.sh
scripts/start-graphdb.sh
```

> Or run all these scripts with a single command:
>  
> ```bash
>    scripts/one-step-load.sh
> ```

## Running the benchmark

4. To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

```bash
driver/create-validation-parameters.sh
driver/validate.sh

 ```

:warning: *Note that if the default workload contains updates which are persisted in the database. Therefore, the database needs to be re-loaded between steps – otherwise repeated updates would insert duplicate entries.*



