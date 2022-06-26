#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

rm -rf factors/*
wget -q https://ldbcouncil.org/ldbc_snb_datagen_spark/social-network-sf0.003-bi-factors.zip
unzip -q social-network-sf0.003-bi-factors.zip
cp -r social-network-sf0.003-bi-factors/factors/csv/raw/composite-merged-fk/* factors/
