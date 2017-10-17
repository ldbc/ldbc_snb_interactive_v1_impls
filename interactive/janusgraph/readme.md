# Janusgraph implementation


## Usage
* The implementation depends upon the ldbc driver. You should build and package it as a jar  and install it to your local maven repo with the following coordinates:
```
     <groupId>com.ldbc.driver</groupId>
     <artifactId>jeeves</artifactId>
     <version>0.3-SNAPSHOT</version>
```

So far, only the importer is implemented. The importer is found at class

```
com.ldbc.snb.janusgraph.importer.Main
```
The importer accepts the following parameters:
* --numThreads/-n The number of threads to use during the loading process
* --transactionSize/-s The size of a reading file transaction (number of rows read per reading task)
* --dataset/-d Path to the folder with the dataset to import. The timestamps of such dataset must be in long format using the ldbc.snb.datagen.serializer.formatter.LongDataFormatter
* --backend-config/-c The path to the file configuring the backend. A sample file can be found at resources/bdb.conf

