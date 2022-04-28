#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

java -cp target/tigergraph-2.0.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client -P driver/benchmark.properties