# LDBC SNB Cypher implementation

[Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).
Note that some BI queries are not expressed using pure Cypher, instead, they make use of the [APOC](https://neo4j.com/labs/) and [Graph Data Science](https://neo4j.com/product/graph-data-science-library/) Neo4j libraries.

## Loading the data in Neo4j

The Neo4j instance is run in Docker. To initialize the environment variables, use:

```bash
. scripts/environment-variables-default.sh
```

To load a data set other than the example data set, you might want to adjust the following variables:

```bash
export NEO4J_CSV_DIR=/path/to/the/directory/social_network/
export NEO4J_CSV_POSTFIX=_0_0.csv
```

```bash
scripts/load-in-one-step.sh
```

This script replaces the headers in the input CSVs, load them, starts Neo4j, and creates indices.

## Loading the example data set

Transform the example data set in the [data converter](https://github.com/ldbc/ldbc_snb_data_converter) repository, then rename it:

```bash
./rename.sh
```

In this repository, run
```bash
. scripts/environment-variables-default.sh
export NEO4J_CSV_DIR=${DATA_CONVERTER_DIR}/ldbc_snb_data_converter/data/csv-composite-projected-fk-legacy-filenames
export NEO4J_CSV_POSTFIX=.csv
scripts/load-in-one-step.sh
```
