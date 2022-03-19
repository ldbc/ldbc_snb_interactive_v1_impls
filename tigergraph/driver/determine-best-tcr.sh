#!/bin/bash

# Script to determine the best TCR (total_compression_ratio) value.

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

../driver/determine-best-tcr-common.sh driver/benchmark.properties 0.001 0.01 4
