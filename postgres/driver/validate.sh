#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

java -cp target/postgres-0.3.6-SNAPSHOT.jar com.ldbc.driver.Client -P driver/validate.properties
