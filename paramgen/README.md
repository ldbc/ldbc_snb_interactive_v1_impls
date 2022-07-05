# Parameter generation

---
WIP
---

The paramgen implements [parameter curation](https://research.vu.nl/en/publications/parameter-curation-for-benchmark-queries) to ensure predictable performance results that (mostly) correspond to a normal distribution.

## Getting started

1. Install dependencies:

    ```bash
    scripts/install-dependencies.sh
    ```

1. **Generating the factors with the Datagen:** In Datagen's directory (`ldbc_snb_datagen_spark`), issue the following commands. We assume that the Datagen project is built and the `${PLATFORM_VERSION}`, `${DATAGEN_VERSION}` environment variables are set correctly.

    ```bash
    export SF=desired_scale_factor
    export LDBC_SNB_DATAGEN_MAX_MEM=available_memory
    ```

    ```bash
    rm -rf out-sf${SF}/{factors,graphs/parquet/raw}
    tools/run.py \
        --cores $(nproc) \
        --memory ${LDBC_SNB_DATAGEN_MAX_MEM} \
        ./target/ldbc_snb_datagen_${PLATFORM_VERSION}-${DATAGEN_VERSION}.jar \
        -- \
        --format csv \
        --scale-factor ${SF} \
        --mode raw \
        --output-dir out-sf${SF} \
        --generate-factors
    ```

1. **Obtaining the factors:** Cleanup the `factors/` directory and move the factor directories from `out-sf${SF}/factors/csv/raw/composite-merged-fk/` (`cityPairsNumFriends/`, `personDisjointEmployerPairs/`, etc.) to the `factors/` directory. Assuming that your `${LDBC_SNB_DATAGEN_DIR}` and `${SF}` environment variables are set, run:

    ```bash
    rm -rf factors/*
    cp -r ${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/factors/csv/raw/composite-merged-fk/* factors/
    ```

    Or, simply run:

    ```bash
    scripts/get-factors.sh
    ```

    To download and use the factors of the sample data set, run:

    ```bash
    scripts/get-sample-factors.sh
    ```

1. To run the parameter generator, issue:

    ```bash
    scripts/paramgen.sh
    ```

1. The parameters will be placed in the `../parameters/` directory.
