#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

sed -i 's/\(ldbc.snb.interactive.LdbcInsert.*_enable\s*=\s*\)true/\1false/' *.properties
sed -i 's/\(ldbc.snb.interactive.LdbcDelete.*_enable\s*=\s*\)true/\1false/' *.properties
