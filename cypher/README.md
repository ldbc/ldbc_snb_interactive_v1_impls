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

## Generating the data set

The Neo4j implementation expects the data to be in `composite-projected-fk` CSV layout, without headers and with quoted fields.
To generate data that confirms this requirement, run Datagen with the `--explode-edges` and the `--format-options header=false,quoteAll=true` options.
This implementation also supports compressed data sets, both for the initial load and for batches. To generate compressed data sets, include `compression=gzip` in the Datagen's `--format-options`. The scripts in this repository change between compressed and uncompressed representations.

(Rationale: Files should not have headers as these are provided separately in the `headers/` directory and quoting the fields in the CSV is required to [preserve trailing spaces](https://neo4j.com/docs/operations-manual/4.3/tools/neo4j-admin-import/#import-tool-header-format).)

In Datagen's directory (`ldbc_snb_datagen_spark`), issue the following commands. We assume that the Datagen project is built and `sbt` is available.

```bash
export SF=desired_scale_factor
export LDBC_SNB_DATAGEN_MAX_MEM=available_memory
export LDBC_SNB_DATAGEN_JAR=$(sbt -batch -error 'print assembly / assemblyOutputPath')
```

```bash
rm -rf out-sf${SF}/graphs/parquet/raw
tools/run.py \
    --cores $(nproc) \
    --memory ${LDBC_SNB_DATAGEN_MAX_MEM} \
    -- \
    --format csv \
    --scale-factor ${SF} \
    --explode-edges \
    --mode bi \
    --output-dir out-sf${SF}/ \
    --epoch-millis \
    --format-options header=false,quoteAll=true,compression=gzip
```

## Loading the data

1. Set the `${NEO4J_CSV_DIR}` environment variable.

    * To use a locally generated data set, set the `${LDBC_SNB_DATAGEN_DIR}` and `${SF}` environment variables and run:

        ```bash
        export NEO4J_CSV_DIR=${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/graphs/csv/bi/composite-projected-fk/
        ```

        Or, simply run:

        ```bash
        . scripts/use-datagen-data-set.sh
        ```

    * To download and use the sample data set, run:

        ```bash
        scripts/get-sample-data-set.sh
        . scripts/use-sample-data-set.sh
        ```

1. Load the data:

    ```bash
    scripts/load-in-one-step.sh
    ```

1. The substitution parameters should be generated using the [`paramgen`](../paramgen).

## Running the benchmark

To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files, then run their script, one of:

```bash
driver/create-validation-parameters.sh
driver/validate.sh
driver/benchmark.sh
```

:warning: The default workload contains updates which change the state of the database. Therefore, **the database needs to be reloaded or restored from backup before each run**. Use the provided `scripts/backup-database.sh` and `scripts/restore-database.sh` scripts to achieve this. Alternatively, e.g. if you lack sudo rights, use Neo4j's built-in dump and load features through the `scripts/backup-neo4j.sh` and `scripts/restore-neo4j.sh` scripts.
