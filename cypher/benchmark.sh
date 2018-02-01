#!/bin/bash

java -cp target/cypher-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb \
  -P cypher-benchmark.properties
