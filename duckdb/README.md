# LDBC SNB DuckDB implementation

[DuckDB](https://duckdb.org/) implementation of the [LDBC Social Network Benchmark's Interactive workload](https://github.com/ldbc/ldbc_snb_docs).

## Setup

Grab DuckDB:

```bash
scripts/get.sh
```

## Generating and loading the data set

### Generating the data set

The data sets need to be generated before loading it to the database. No preprocessing is required. To generate such data sets, use the `CsvMergeForeignDynamicActivitySerializer` serializer classes of the [Hadoop-based Datagen](https://github.com/ldbc/ldbc_snb_datagen_hadoop):

```ini
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvMergeForeignDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvMergeForeignDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvMergeForeignStaticSerializer
```

### Loading the data set

```bash
export DUCKDB_DATA_DIR=`pwd`/../postgres/test-data
scripts/cleanup.sh
scripts/load.sh
```


### Running the benchmark

TODO.

:warning: *Each scale factor has a different configuration. For benchmark runs, make sure you use the correct update interleave value and query frequencies provided in the [`sf-properties` directory](../sf-properties)*

:warning: *Note that the default workload contains updates which are persisted in the database. Therefore, the database needs to be re-loaded between steps – otherwise repeated updates would insert duplicate entries.*
