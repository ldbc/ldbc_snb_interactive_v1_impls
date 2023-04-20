# Pre-generated data sets

## Streaming decompression

To download and decompress the data sets on-the-fly, make sure you have `curl` and [`zstd`](https://facebook.github.io/zstd/) installed, then run:

```bash
export DATASET_URL=...
curl --silent --fail ${DATASET_URL} | tar -xv --use-compress-program=unzstd
```

For multi-file data sets, first download them. Then, to recombine and decompress, run:

```bash
cat <data-set-filename>.tar.zst* | tar -xv --use-compress-program=unzstd
```

This command works on both standalone files (`.tar.zst`) and chunked ones (`.tar.zst.XXX`).


## Data sets links

### Merged FK

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf1-merged-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf3-merged-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf10-merged-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf30-merged-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf100-merged-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf300-merged-fk.tar.zst

### Projected FK

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf1-projected-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf3-projected-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf10-projected-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf30-projected-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf100-projected-fk.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-out-sf300-projected-fk.tar.zst

### Raw (up to SF30)

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/bi-sf1-raw.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/bi-sf3-raw.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/bi-sf10-raw.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/bi-sf30-raw.tar.zst

### Update streams

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf1.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf3.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf10.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf30.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf100.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf300.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf1000.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf3000.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive-updates/interactive-updates-sf10000.tar.zst

### Factor tables

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf1.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf3.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf10.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf30.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf100.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf300.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf1000.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf3000.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/bi-pre-audit/factors/factors-sf10000.tar.zst

### Parameters (deprecated)

* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf1.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf3.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf10.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf30.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf100.tar.zst
* https://pub-383410a98aef4cb686f0c7601eddd25f.r2.dev/interactive/snb-interactive-parameters-sf300.tar.zst
