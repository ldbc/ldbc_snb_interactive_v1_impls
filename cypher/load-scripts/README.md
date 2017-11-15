## Import the CSV files to Neo4j/ingraph

The data set needs to be generated and preprocessed before loading it to the database.

### Generating the data set

Use the standard `CSVSerializer` classes of the LDBC SNB [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVPersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVPersonActivitySerializer
```

Set the `$NEO4J_HOME` and the following environment variables appropriately:

```bash
export DATA_DIR=/path/do/the/csv/files
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

Be careful -- this deletes all data in your database.

```bash
./delete-neo4j-database.sh
./import-to-neo4j.sh
```

Restart Neo4j.

## Stuff currently ignored

We ignore some files as they are difficult to load and none of the queries need them.

### Property files

These files store lists in a normalized form:

* `person_speaks_language`

  ```
  Person.id	language
  8796093022220	es
  8796093022220	en
  2199023255591	ru
  2199023255591	en
  ```

* `person_email_emailaddress`

  ```
  Person.id	email
  8796093022220	Jose8796093022220@gmail.com
  8796093022220	Jose8796093022220@gmx.com
  2199023255591	Alexei2199023255591@gmail.com
  2199023255591	Alexei2199023255591@zoho.com
  ```

## Misc

*Reminder.* Regex for extracting relationship names (Atom):

`(\w+)_(\w+)_(\w+)` -> `--relationship:$2 $1_$2_$3`
