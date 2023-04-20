#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

VALIDATE_PROPERTIES_FILE=${1:-driver/validate.properties}

java -cp target/postgres-2.0.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client -P ${VALIDATE_PROPERTIES_FILE}
