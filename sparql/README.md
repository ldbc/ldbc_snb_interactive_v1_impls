# LDBC SNB SPARQL implementation

[SPARQL 1.1](https://www.w3.org/TR/sparql11-query/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Loading the data set

### Generating the data set

Use the `Turtle` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.TurtlePersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.TurtleInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.TurtlePersonActivitySerializer
```

### Loading the data

Set the `RDF_DATA_DIR` environment variable and run the load script:

```
export RDF_DATA_DIR=/path/to/the/ttl/files
./load-to-blazegraph.sh
```
