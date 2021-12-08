#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

CREATE_VALIDATION_PARAMETERS_PROPERTIES_FILE=${1:-driver/create-validation-parameters.properties}

java -cp target/umbra-0.3.6-SNAPSHOT.jar com.ldbc.driver.Client -P ${CREATE_VALIDATION_PARAMETERS_PROPERTIES_FILE}
