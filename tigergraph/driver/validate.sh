#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

java -cp target/tigergraph-1.0.0.jar org.ldbcouncil.snb.driver.Client -P driver/validate.properties
