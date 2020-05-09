#!/bin/bash

java -cp target/postgres-0.4.0-SNAPSHOT.jar com.ldbc.driver.Client -dm CREATE_VALIDATION_PARAMS -P bi-create-validation-parameters.properties
