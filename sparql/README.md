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

## Loading and setting up the database

Set up the value of the `$STARDOG_HOME` environment variable.

```bash
export STARDOG_HOME=/path/to/stardog/dir
export STARDOG_SERVER_JAVA_ARGS="-Xms16G -Xmx16G -XX:MaxDirectMemorySize=128G"
```

### Loading the data

Set up the environment variables and run the load script:

```bash
export RDF_DB=ldbcsf1
export RDF_DATA_DIR=/path/to/the/ttl/files
./load-to-stardog.sh
```

### Setting up the database

Due to the complexity of the queries, it is recommended to increase the timeout.
Also, the BI workload is currently read-only, so it makes sense to set the memory mode to `read_optimized`.
To do so, create/edit the `$STARDOG_HOME/stardog.properties` file and add the following lines.

```
query.timeout=3600s
memory.mode=read_optimized
```
