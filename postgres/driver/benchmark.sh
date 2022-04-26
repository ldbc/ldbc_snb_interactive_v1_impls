#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

BENCHMARK_PROPERTIES_FILE=${1:-driver/benchmark.properties}

java -cp target/postgres-0.3.7-SNAPSHOT.jar org.ldbcouncil.driver.Client -P ${BENCHMARK_PROPERTIES_FILE}
