#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

# get both factors and temporal entities for the sample data set

scripts/get-sample-factors.sh
scripts/get-sample-temporal.sh
