# Microsoft SQL Server implementation

[SQL Server](https://www.microsoft.com/en-us/sql-server) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

The recommended environment is that the benchmark scripts (Bash) and the LDBC driver (Java 8) run on the host machine, while the SQL Server database runs in a Docker container. Therefore, the requirements are as follows:

* Bash
* Java 8
* Docker 19+
* enough free space in the directory `${MSSQL_DATA_DIR}`

## Generating and loading the data set

### Generating the data set

The data sets need to be generated before loading it to the database. No preprocessing is required. To generate data sets, use the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop)'s `CsvMergeForeign` serializer classes:

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvMergeForeignDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvMergeForeignDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvMergeForeignStaticSerializer
```

Pre-generated data sets are available in the [SURF repository](https://github.com/ldbc/data-sets-surf-repository).

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
