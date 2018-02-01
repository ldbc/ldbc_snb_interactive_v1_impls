#!/bin/bash

java -cp target/postgres-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client \
  -db com.ldbc.impls.workloads.ldbc.snb.jdbc.bi.PostgresBiDb \
  -P postgres-create_validation_parameters.properties
