#!/usr/bin/env bash

set -eu

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

export ZSTD_NBTHREADS=`nproc`

echo Parameters
time tar --use-compress-program=zstdmt -cf snb-interactive-parameters-sf${SF}.tar.zst parameters-sf${SF}/

echo Update-streams
time tar --use-compress-program=zstdmt -cf snb-interactive-update-streams-sf${SF}.tar.zst update-streams-sf${SF}/
