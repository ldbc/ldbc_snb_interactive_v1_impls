## Import the CSV files to Neo4j/ingraph

The data set needs to be generated and preprocessed before loading it to the database.

### Generating the data set

Use the `CSVCompositeSerializer` classes of the  [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositePersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositeInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositePersonActivitySerializer
```

Set the `$NEO4J_HOME` and the following environment variables appropriately:

```bash
export NEO4J_DATA_DIR=/path/do/the/csv/files
export DB_DIR=$NEO4J_HOME/data/databases/graph.db
export POSTFIX=_0_0.csv
```

### Preprocessing

The CSV files require a bit of preprocessing:

* replace headers with Neo4j-compatible ones
* replace labels (e.g. change `city` to `City`)
* convert date and datetime formats

The following script takes care of those steps:

```bash
./convert-csvs.sh
```

### Delete your database and load the SNB CSVs

Be careful -- this deletes all data in your database, imports the SNB data set and restarts the database.

```bash
./delete-neo4j-database.sh
./import-to-neo4j.sh
./restart-neo4j.sh
```

### All-in-one

If you know what you're doing, you can run all scripts with a single command:

```bash
./force-load.sh
```

## Misc

*Reminder.* Regex for extracting relationship names (tested in Atom):

`(\w+)_(\w+)_(\w+)` -> `--relationship:$2 $1_$2_$3`
