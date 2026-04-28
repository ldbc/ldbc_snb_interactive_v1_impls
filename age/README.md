# Apache AGE — LDBC SNB Interactive v1 Implementation

Reference implementation of the LDBC Social Network Benchmark Interactive workload for
[Apache AGE](https://age.apache.org/) (PostgreSQL graph extension).

## Overview

| Category | Count | Status |
|---|---|---|
| Interactive Complex (IC) | 12 / 14 | ✅ Implemented |
| IC13 / IC14 | 2 / 14 | ⚠️ Degraded (see below) |
| Interactive Short (IS) | 7 / 7 | ✅ Implemented |
| Interactive Update (IU) | 8 / 8 | ✅ Implemented |

### IC13 / IC14 Limitation

Apache AGE does not support `shortestPath()` or `allShortestPaths()`. These two operations are
handled by degraded-but-spec-compliant stubs:

- **IC13**: Always returns `-1` (the LDBC spec sentinel for "no path exists").
- **IC14**: Always returns an empty list.

These operations must be disabled in validation runs:
```properties
LdbcQuery13.enable=false
LdbcQuery14.enable=false
```

## Build

Requires JDK 11+ and Maven 3.6+.

```bash
cd ~/repositories/ldbc_snb_interactive_v1_impls

# Build common + AGE modules
mvn install -pl common -am -DskipTests
cd age && mvn clean package -DskipTests

# Artifact: age/target/age-1.2.0-SNAPSHOT.jar
```

## Data Loading

Data loading uses [agefreighter](https://github.com/rioriost/agefreighter) — outside the LDBC
standard per client mandate.

```bash
# Set connection string
export CONNECTION_STRING="postgresql://user:pass@host:5432/dbname"

# Load SF3
bash age/scripts/load-data.sh --sf 3
```

The `load-data.sh` script:
1. Preprocesses raw LDBC CSV data via `GraphBenchmarking/ldbc_snb_benchmark/preprocess_ldbc.py`
2. Loads the graph via `agefreighter`
3. Creates B-tree indexes (`create-indexes.sql`)
4. Runs `VACUUM ANALYZE`

## Snapshot and Restore

IU operations mutate the graph. Snapshot before the first run and restore before re-runs:

```bash
export CONNECTION_STRING="postgresql://user:pass@host:5432/dbname"

# Before first run
bash age/scripts/snapshot-database.sh

# Before each subsequent run
bash age/scripts/restore-database.sh
```

## Validation

```bash
cd age
java -cp target/age-1.2.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client \
  -db  org.ldbcouncil.snb.impls.workloads.age.interactive.AgeInteractiveDb \
  -w   org.ldbcouncil.snb.impls.workloads.interactive.LdbcSnbInteractiveWorkload \
  -vdb test-data/validation_params.csv \
  -P   driver/validate.properties
```

## Benchmark

Edit `driver/benchmark.properties` with your connection details and paths, then:

```bash
cd age
java -cp target/age-1.2.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client \
  -db org.ldbcouncil.snb.impls.workloads.age.interactive.AgeInteractiveDb \
  -w  org.ldbcouncil.snb.impls.workloads.interactive.LdbcSnbInteractiveWorkload \
  -P  driver/benchmark.properties
```

## Thread Safety

The current implementation uses a single JDBC connection synchronized on all handler calls.
For `thread_count > 1`, replace `AgeDbConnectionState` with a HikariCP connection pool
(pool size = `thread_count`) to eliminate lock contention.

## Architecture

```
AgeInteractiveDb (entry point — registers all 29 handlers)
  └── AgeDb (connection setup, 27 inner handler classes)
        ├── AgeDbConnectionState  (JDBC + LOAD 'age' + search_path)
        ├── AgeQueryStore         (loads .sql files, epoch-ms dates, $graphName substitution)
        ├── AgeConverter          (agtype ↔ Java read/write)
        └── operationhandlers/
              ├── AgeListOperationHandler      (IC1-12, IS2/3/7)
              ├── AgeSingletonOperationHandler (IS1/4/5/6)
              ├── AgeUpdateOperationHandler    (IU1-8)
              ├── AgeIC13OperationHandler      (degraded stub)
              └── AgeIC14OperationHandler      (degraded stub)
```
