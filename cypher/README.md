# LDBC SNB Cypher implementation

[(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Starting Neo4j

To grab the Neo4j binaries and spin up a server, run:

```bash
scripts/get-neo4j.sh
. scripts/environment-variables-neo4j.sh
scripts/configure-neo4j.sh
```

To load the data, do:

```bash
scripts/get-neo4j.sh
export NEO4J_CSV_DIR=/path/to/the/directory/social_network/
export NEO4J_CSV_POSTFIX=_0_0.csv
scripts/load-in-one-step.sh
```

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CsvComposite` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
generator.scaleFactor:0.003
generator.mode:interactive
serializer.format:CsvComposite
```

An example configuration for scale factor 1 is given in the [`params-csv-composite.ini`](https://github.com/ldbc/ldbc_snb_datagen/blob/dev/params-csv-composite.ini) file of the Datagen repository.

### Preprocessing and loading
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
scripts/convert-csvs.sh
```

#### Initial password for Neo4j

```bash
scripts/configure-neo4j.sh
```

#### Delete your database and load the SNB CSVs

Be careful -- this deletes all data in your database, imports the SNB data set and restarts the database.

```bash
scripts/delete-neo4j-database.sh
scripts/import-to-neo4j.sh
scripts/restart-neo4j.sh
```

#### All-in-one loading script

If you know what you're doing, you can run all scripts with a single command:

```bash
scripts/load-in-one-step.sh
```
