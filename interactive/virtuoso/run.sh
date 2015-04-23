#!/bin/bash

java -Xmx8g -cp /2d1/sib30/driver/jeeves-0.2-SNAPSHOT.jar:/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/java/virtuoso/target/virtuoso-0.0.1-SNAPSHOT.jar:/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/java/virtuoso/virtjdbc4_lib/virtjdbc4.jar com.ldbc.driver.Client -db com.ldbc.driver.workloads.ldbc.snb.interactive.db.VirtuosoDb -P ./ldbc_snb_interactive.properties -P ./ldbc_driver_default.properties -P virtuoso_configuration.properties -P /2d6/mirkoOutputDir/300sf_csv/updates/updateStream.properties -rl
