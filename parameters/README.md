# Parameters

This directory contains the query substitution parameters (`interactive-*.csv`)

The headers of the parameters are specified as a pipe-separated CSV file. It makes use the syntax of the [Neo4j CSV import tool](https://neo4j.com/docs/operations-manual/4.2/tools/neo4j-admin-import/#import-tool-header-format) to specify the type of each parameter. For example, the header

```
date:DATE|lengthThreshold:INT|languages:STRING[]
```

indicates that there are 3 parameters, `date` (a date value), `lengthThreshold` (a 64-bit integer), and `languages` (a string array).

The expected headers for each `interactive-*.csv` files are specified in the `headers.csv` file.
