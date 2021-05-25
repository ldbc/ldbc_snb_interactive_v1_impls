#!/bin/bash

set -e
set -o pipefail

sed -i 's/\(ldbc.snb.interactive.LdbcUpdate.*\)_enable=true/\1_enable=false/' interactive-*.properties
