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

Set the environment variables and run the load script:

```
export RDF_DB=ldbcsf1
export RDF_DATA_DIR=/path/to/the/ttl/files
./load-to-stardog.sh
```

### Troubleshooting

Problem: when running the bundled JAR file, Java throws the following error.

```
Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.SecurityException: Invalid signature file digest for Manifest main attributes
	at sun.security.util.SignatureFileVerifier.processImpl(SignatureFileVerifier.java:330)
```

Solution: remove the signature files.

```bash
zip -d target/sparql-0.0.1-SNAPSHOT.jar META-INF/*.RSA META-INF/*.DSA META-INF/*.SF
```
