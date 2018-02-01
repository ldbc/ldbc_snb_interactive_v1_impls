#!/bin/bash

java -cp target/sparql-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiDb \
  -P sparql-create_validation_parameters.properties
