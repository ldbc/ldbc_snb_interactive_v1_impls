# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Starting Neo4j

Run:

```bash
. ./environment-variables-neo4j.sh
./get-neo4j.sh
./configure-neo4j.sh
```

To load the data, do:
```
. ./environment-variables-neo4j.sh
export NEO4J_CSV_DIR=/path/to/the/directory/social_network/
export NEO4J_CSV_POSTFIX=_0_0.csv
cd load-scripts
./load-in-one-step.sh
```

To start the database, invoke:

```bash
./start-neo4j.sh
```

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVComposite` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-csv-composite.ini`](https://github.com/ldbc/ldbc_snb_datagen/blob/master/params-csv-composite.ini) file of the DATAGEN repository. For small loading experiments, we recommend using scale factor 0.1, i.e. `snb.interactive.0.1`.

### Preprocessing and loading

Go to the `load-scripts/` directory.

#### Preprocessing

Set the Neo4j following environment variables appropriately. Once you got the configuration right, you might want to save these variables for later:

```bash
env | grep ^NEO4J_
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
