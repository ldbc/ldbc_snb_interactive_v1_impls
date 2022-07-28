#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

sed -i 's/\(ldbc.snb.interactive.LdbcUpdate.*\)_enable=false/\1_enable=true/' *.properties
