# Microsoft SQL Server implementation

[SQL Server](https://www.microsoft.com/en-us/sql-server) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the SQL Server database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `${MSSQL_DATA_DIR}`

To build, use the script: `scripts/build.sh`


### docker-compose

Alternatively, a docker-compose specification is available to start the SQL Server container and a container loading the data. This requires `docker-compose` installed on the host machine. Running SQL Server and loading the data can be done by executing:

```bash
docker-compose build && docker-compose up
```

The default environment variables are loaded from `.env`. Change the `MSSQL_CSV_DIR` to point to point to the data set, e.g.

```bash
MSSQL_CSV_DIR=`pwd`/social-network-sf1-bi-composite-merged-fk/
```

To persist the data by storing the database outside a Docker volume, uncomment the following lines in the `docker-compose.yml` file:

```yaml
- type: bind
source: ${MSSQL_DATA_DIR}
target: /var/opt/mssql/data
- type: bind
source: ${MSSQL_DATA_LOGS}
target: /var/opt/mssql/log
- type: bind
source: ${MSSQL_DATA_SECRETS}
target: /var/opt/mssql/secrets
```

## Generating and loading the data set

### Generating the data set

This SQL Server implementation uses the `composite-merged-fk` CSV layout, with headers and without quoted fields. To generate data that confirms this requirement, run Datagen without any layout or formatting arguments (`--explode-*` or `--format-options`).

In Datagen's directory (`ldbc_snb_datagen_spark`), issue the following commands. We assume that the Datagen project [is](https://github.com/ldbc/ldbc_snb_datagen_spark) built and `sbt` is available.

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
    --mode bi \
    --output-dir out-sf${SF}
```


### Configuration

Before starting the SQL Server Docker instance, change the `MSSQL_CSV_DIR` found in `.env` file to the path where the dataset is located. E.g.:

```bash
MSSQL_CSV_DIR=/data/ldbc-data/social_network-csv_merge_foreign-sf1
```

By default, the dataset is loaded again when the docker container is restarted. To prevent reloading, set the `MSSQL_RECREATE_DB` variable to `False`. E.g.:

```bash
MSSQL_RECREATE_DB=False
```

Make sure the following folders are created relative to the `docker-compose.yml`:

```bash
scratch/data
scratch/logs
scratch/secrets
```

To run the benchmark, change the following properties in `driver/benchmark.properties`:

- `thread_count`: amount of threads to use

- `ldbc.snb.interactive.parameters_dir`: path to the folder with the substitution parameters
- `ldbc.snb.interactive.updates_dir`: path to the folder with the updatestreams. Make sure the update streams corresponds to the `thread_count`.

- `ldbc.snb.interactive.scale_factor`: the scale factor to use (must be the same as the substitution parameters and update streams)

To validate the benchmark, change the following properties in `driver/validate.properties`:

- `validate_database`: The validation parameter csv-file to use
- `ldbc.snb.interactive.parameters_dir`: path to the folder with the substitution parameters

### Loading the data set

The dataset is loaded automatically using the db-loader container. To start the SQL Server container and load the data, `docker-compose` is used:

1. `docker-compose build`, to build the db-loader container
2. `docker-compose up` to start the SQL Server container and the db-loader container

### Running the benchmark

To run the benchmark run the following command:

`driver/benchmark.sh`

### Validate results

To validate the results, run the following command:

`driver/validate.sh`
