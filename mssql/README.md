# Microsoft SQL Server implementation

Implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs) in [Microsoft SQL Server](https://www.microsoft.com/en-us/sql-server) using its [SQL Graph](https://learn.microsoft.com/en-us/sql/relational-databases/graphs/sql-graph-architecture) feature.

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 11) run on the host machine, while the SQL Server database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 11
* Docker 19+
* enough free space in the directory `${MSSQL_DATA_DIR}`

To build, use the script: `scripts/build.sh`. This implementation is tested using the docker container of SQL Server 2019.

## Configuration

Before running the SQL Server implementation, the following files must be edited:

- `.env`: file containing the environment variables used in the `docker-compose.yml`
- `docker-compose.yml`: edit if data must be stored persistent
- `driver/benchmark.properties`: properties file used in execute benchmark mode
- `driver/validate.properties`: properties file used in validation mode
- `driver/create-validation-parameters.properties`: properties file used in create validation parameter mode



1. Change the `MSSQL_CSV_DIR` found in `.env` file to the path where the dataset is located, e.g.:

   ```properties
   MSSQL_CSV_DIR=`pwd`/social-network-sf1-bi-composite-merged-fk/
   ```

   By default, the dataset is loaded again and existing databases & tables dropped when the docker container is restarted. To prevent reloading, set the `MSSQL_RECREATE` variable to `False`, e.g.:

   ```properties
   MSSQL_RECREATE=False
   ```

2. To persist the data by storing the database outside a Docker volume, uncomment the following lines in the `docker-compose.yml` file:

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

   The environment variables used in the `source` variables need to be set in the `.env` file to the folder to store the data, logs and secrets, e.g.:

   ```properties
   MSSQL_DATA_DIR=./scratch/data/
   MSSQL_DATA_LOGS=./scratch/logs/
   MSSQL_DATA_SECRETS=./scratch/secrets
   ```

3. To execute the benchmark, change the following properties in `driver/benchmark.properties`:

   - `thread_count`: amount of threads to use
   - `ldbc.snb.interactive.parameters_dir`: path to the folder with the substitution parameters
   - `ldbc.snb.interactive.updates_dir`: path to the folder with the updatestreams. Make sure the update streams corresponds to the `thread_count`.
   - `ldbc.snb.interactive.scale_factor`: the scale factor to use (must be the same as the substitution parameters and update streams)

4.  To validate the benchmark, change the following properties in `driver/validate.properties`:

   - `validate_database`: Path to the validation parameter CSV-file to use
   - `ldbc.snb.interactive.parameters_dir`: path to the folder with the substitution parameters

5. To create validation parameters, change the following properties in `driver/create-validation-parameters.properties`:
   - `ldbc.snb.interactive.parameters_dir`: path to the folder with the substitution parameters
   - `ldbc.snb.interactive.updates_dir`: path to the folder with the updatestreams. Make sure the update streams corresponds to the `thread_count`.
   - `ldbc.snb.interactive.scale_factor`: the scale factor to use (must be the same as the substitution parameters and update streams)

## Generating and loading the data set

### Generating the data set

This SQL Server implementation uses the `composite-merged-fk` CSV layout, with headers and without quoted fields. To generate data that confirms this requirement, run Datagen without any layout or formatting arguments (`--explode-*` or `--format-options`).

In Datagen's directory (`ldbc_snb_datagen_spark`), issue the following commands. We assume that the [Datagen project](https://github.com/ldbc/ldbc_snb_datagen_spark) is built and `sbt` is available.

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



### Loading the data set

The dataset is loaded automatically using the db-loader container. To start the SQL Server container and load the data, `docker-compose` is used:

1. `docker-compose build`, to build the db-loader container
2. `docker-compose up` to start the SQL Server container and the db-loader container



## Execution

- To run the benchmark run the following command:

  ```bash
  driver/benchmark.sh
  ```

- To validate the results, run the following command:

  ```bash
  driver/validate.sh
  ```

- To create validation parameters, run the command:

  ```bash
  driver/create-validation-parameters.sh
  ```

  
