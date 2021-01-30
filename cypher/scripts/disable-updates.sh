#!/bin/bash

set -e

sed -i 's/\(ldbc.snb.interactive.LdbcUpdate.*\)_enable=true/\1_enable=false/' interactive-*.properties
