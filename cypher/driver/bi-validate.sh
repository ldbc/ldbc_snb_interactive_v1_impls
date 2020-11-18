#!/bin/bash

java -cp target/cypher-0.4.0-SNAPSHOT.jar com.ldbc.driver.Client -dm VALIDATE_DATABASE -P bi-validate.properties
