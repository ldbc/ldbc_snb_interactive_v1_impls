# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVComposite` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositePersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositeInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVCompositePersonActivitySerializer
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

Note, if you are using OSX please install `gnu-sed`. 
```bash
brew install gnu-sed 
# GNU "sed" is installed as "gsed"
# to use as "sed" add a "gnubin" directory to PATH from your start-up file 
export PATH="/usr/local/opt/gnu-sed/libexec/gnubin:$PATH"
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
