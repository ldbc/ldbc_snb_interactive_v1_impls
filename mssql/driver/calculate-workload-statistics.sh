#!/usr/bin/env bash
set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

STATISTICS_PROPERTIES_FILE=${1:-driver/statistics.properties}

java -cp target/mssql-2.0.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client -P ${STATISTICS_PROPERTIES_FILE}
