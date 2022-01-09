# LDBC SNB Tygrysek implementation

This directory contains the [Tygrysek/GSQL](<<TODO>>) implementation of the Interactive workload of the [LDBC SNB benchmark](https://github.com/ldbc/ldbc_snb_docs).



## Setup
      TBD

## Obtaining and loading the data set
In this section, we will describe how to obtain the data set and load it into the database.

The data can be generated using the data generator tool or downloaded from the [LDBC SNB benchmark data repository](


https://storage.googleapis.com/ldbc_small/sf1.tar.gz
gs://ldbc_interactive/composite_projectecd/sf1.tar.zst

https://storage.googleapis.com/ldbc_interactive/composite_projectecd/sf1.tar.zst

https://storage.googleapis.com/ldbc_small/sf1.tar.gz



gs://ldbc_small/sf100.tar.gz HTTP link. https://storage.googleapis.com/ldbc_small/sf100.tar.gz

### Generating the data set

### Preprocessing and loading



## Running the benchmark

To run the scripts of benchmark framework, edit the `driver/{create-validation-parameters,validate,benchmark}.properties` files,
then run their script, one of:

```bash
driver/create-validation-parameters.sh
driver/validate.sh
driver/benchmark.sh
```

> **Warning:** Note that if the default workload contains updates which are persisted in the database. Therefore, the database needs to be re-loaded between steps – otherwise repeated updates would insert duplicate entries.*
