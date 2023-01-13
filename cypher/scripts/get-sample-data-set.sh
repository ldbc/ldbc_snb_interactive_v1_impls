#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

rm -f social-network-sf0.003-bi-composite-projected-fk-neo4j-compressed-epoch-millis.zip
wget -q https://ldbcouncil.org/ldbc_snb_datagen_spark/social-network-sf0.003-bi-composite-projected-fk-neo4j-compressed-epoch-millis.zip
rm -rf social-network-sf0.003-bi-composite-projected-fk-neo4j-compressed-epoch-millis/
unzip -q social-network-sf0.003-bi-composite-projected-fk-neo4j-compressed-epoch-millis.zip
