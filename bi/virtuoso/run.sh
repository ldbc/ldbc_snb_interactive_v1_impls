#!/bin/bash

java -Xmx8g -cp /home/ldbc/ldbc_driver/target/jeeves-0.3-SNAPSHOT.jar:/home/ldbc/ldbc_snb_implementations/bi/virtuoso/java/virtuoso/target/virtuoso_bi-0.0.1-SNAPSHOT.jar:/home/ldbc/ldbc_snb_implementations/bi/virtuoso/java/virtuoso/virtjdbc4_lib/virtjdbc4.jar com.ldbc.driver.Client -db com.ldbc.driver.workloads.ldbc.snb.bi.db.VirtuosoDb -P /home/ldbc/ldbc_driver/configuration/ldbc/snb/bi/ldbc_snb_bi_SF-0001.properties -P /home/ldbc/ldbc_driver/configuration/ldbc_driver_default.properties -P virtuoso_configuration.properties -vdb ./validation_params_01.csv
