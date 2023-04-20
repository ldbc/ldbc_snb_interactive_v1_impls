#!/usr/bin/env bash

set -eu

cd ${LDBC_SNB_DATAGEN_DIR}

export ZSTD_NBTHREADS=`nproc`

for VARIANT in projected-fk merged-fk; do
    echo ${VARIANT}
    rm -rf compression-workdir/out-sf${SF}
    mkdir -p compression-workdir/out-sf${SF}/graphs/csv/bi/composite-${VARIANT}
    cp -r out-sf${SF}/graphs/csv/bi/composite-${VARIANT}/* compression-workdir/out-sf${SF}/graphs/csv/bi/composite-${VARIANT}

    cd compression-workdir
    time tar --use-compress-program=zstdmt -cf snb-out-sf${SF}-${VARIANT}.tar.zst out-sf${SF}/
    cd ..
done
