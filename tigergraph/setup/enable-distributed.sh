#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

sed -i 's/CREATE OR REPLACE QUERY/CREATE OR REPLACE DISTRIBUTED QUERY/' ../queries/interactiveComplex*.gsql
sed -i 's/CREATE OR REPLACE QUERY/CREATE OR REPLACE DISTRIBUTED QUERY/' ../queries/interactiveShort*.gsql