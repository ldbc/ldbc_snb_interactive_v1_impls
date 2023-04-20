#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

# drop head, select column of %memused and sort to select the top-1 value
tail -n +3 output/memory.log | cut -c45-52 | sort -nr | head -n 1
