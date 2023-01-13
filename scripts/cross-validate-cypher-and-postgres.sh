#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

echo "Run cross-validation between Neo4j (Cypher) and Postgres (SQL) on SF${SF}"

cd cypher
. scripts/use-datagen-data-set.sh
scripts/load-in-one-step.sh
driver/create-validation-parameters.sh driver/create-validation-parameters-sf${SF}.properties
cd ..

cp cypher/validation_params.json postgres/

cd postgres
. scripts/use-datagen-data-set.sh
scripts/load-in-one-step.sh
driver/validate.sh
cd ..
