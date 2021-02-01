#!/bin/bash

set -e
set -o pipefail

: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}

rm -rf ${NEO4J_DATA_DIR}
