# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Starting Neo4j

Run:

```
./get-neo4j.sh
./environment-variables-neo4j.sh && ./configure-neo4j.sh && neo4j-server/bin/neo4j start
```

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVComposite` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

### Preprocessing and loading

Go to the `load-scripts/` directory.

#### Preprocessing

Set the following environment variables appropriately:

```bash
export NEO4J_HOME=/path/to/the/neo4j/dir
export NEO4J_DB_DIR=$NEO4J_HOME/data/databases/graph.db
export NEO4J_DATA_DIR=/path/do/the/csv/files
export POSTFIX=_0_0.csv
```

The CSV files require a bit of preprocessing:

* replace headers with Neo4j-compatible ones
* replace labels (e.g. change `city` to `City`)
* convert date and datetime formats

The following script takes care of those steps:

```bash
./convert-csvs.sh
```

#### Delete your database and load the SNB CSVs

Be careful -- this deletes all data in your database, imports the SNB data set and restarts the database.

```bash
./delete-neo4j-database.sh
./import-to-neo4j.sh
./restart-neo4j.sh
```

#### All-in-one loading script

If you know what you're doing, you can run all scripts with a single command:

```bash
./load-in-one-step.sh
```
