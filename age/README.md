# Apache AGE — LDBC SNB Interactive v1 Implementation

Reference implementation of the LDBC Social Network Benchmark Interactive workload for
[Apache AGE](https://age.apache.org/) (PostgreSQL graph extension, version 1.6.0+).

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

These operations must be disabled in validation runs (already set in `driver/validate.properties`):
```properties
ldbc.snb.interactive.LdbcQuery13_enable=false
ldbc.snb.interactive.LdbcQuery14_enable=false
```

## Prerequisites

- JDK 11+, Maven 3.6+
- PostgreSQL with Apache AGE 1.6.0+ extension loaded
- Python 3.8+ with `psycopg2-binary` (auto-installed by `load-test-data.sh`)
- `psql` and `pg_dump`/`pg_restore` CLI tools

## Build

```bash
cd ~/repositories/ldbc_snb_interactive_v1_impls

# Build common + AGE modules
mvn install -pl common -am -DskipTests
cd age && mvn clean package -DskipTests

# Artifact: age/target/age-1.2.0-SNAPSHOT.jar
```

## Configuration

All scripts require a `CONNECTION_STRING` environment variable:

```bash
export CONNECTION_STRING="postgresql://user:pass@host:5432/dbname"
```

Edit `driver/validate.properties` and `driver/benchmark.properties` with:
- `age_endpoint` — `host:port/dbname`
- `age_user` / `age_password`
- `age_graph_name` — defaults to `ldbc_snb`
- `ldbc.snb.interactive.parameters_dir` / `updates_dir` — paths to substitution params and update streams

## Data Loading

### Test data (SF0.003 — for validation)

The test data is copied from the `cypher/test-data/` directory (already in the repo) and loaded via a Python script:

```bash
cd age

# Copies cypher/test-data/* into age/test-data/, loads into AGE, creates indexes,
# runs VACUUM ANALYZE, and takes an initial snapshot.
bash scripts/load-test-data.sh
```

This script auto-creates a `.venv` with `psycopg2-binary` on first run.

### Production data (SF1+)

For larger scale factors, load from pre-generated LDBC vanilla CSVs:

```bash
export CONNECTION_STRING="postgresql://user:pass@host:5432/dbname"
bash scripts/load-data.sh --sf 3   # or --sf 1, --sf 10, etc.
```

## Snapshot and Restore

IU operations mutate the graph. Snapshot before the first run and restore before re-runs:

```bash
export CONNECTION_STRING="postgresql://user:pass@host:5432/dbname"

# Before first run (also done automatically by load-test-data.sh)
bash scripts/snapshot-database.sh

# Before each subsequent run
bash scripts/restore-database.sh
```

The restore script handles the AGE-specific OID mismatch that occurs when `pg_restore` creates a
new schema OID — it patches `ag_catalog.ag_graph` and `ag_catalog.ag_label` to match.

## Validation

### Local end-to-end (recommended)

```bash
cd age

# First run: load data, generate params, restore, then validate
bash scripts/run-local-validation.sh --load

# Subsequent runs (skip load if data already loaded):
bash scripts/run-local-validation.sh
```

This script:
1. Generates `test-data/validation_params.csv` using the current implementation
2. Restores the snapshot (resets IU mutations from step 1)
3. Runs `validate_database` against the generated params

### Manual

Edit `driver/validate.properties` with your connection details, then:

```bash
cd age
java -cp target/age-1.2.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client \
  -P driver/validate.properties
```

## Benchmark

Edit `driver/benchmark.properties` with your connection details and paths, then:

```bash
cd age
java -cp target/age-1.2.0-SNAPSHOT.jar org.ldbcouncil.snb.driver.Client \
  -P driver/benchmark.properties
```

Reload between runs:
```bash
bash scripts/restore-database.sh
```

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

## Thread Safety

The current implementation uses a single JDBC connection synchronized on all handler calls.
For `thread_count > 1`, replace `AgeDbConnectionState` with a HikariCP connection pool
(pool size = `thread_count`) to eliminate lock contention.

## AGE-Specific Technical Notes

### Date representation

All dates are stored and compared as **epoch milliseconds (bigint)**. The `AgeConverter`
converts `java.util.Date` → `Long.toString(date.getTime())`. Queries use numeric comparisons:
```sql
WHERE msg.creationDate >= $startDate AND msg.creationDate < $endDate
```

### Pattern predicates not supported

Apache AGE 1.6.0 does not support pattern expressions in `WHERE` or `CASE WHEN`:
```cypher
-- NOT supported in AGE:
WHERE NOT (p)-[:KNOWS]-(friend)
CASE WHEN (a)-[:REL]->(b) THEN ...
```
These are rewritten using `OPTIONAL MATCH` + null checks, or explicit MATCH + property filters.

### `agtype` aggregation

PostgreSQL has no native `sum(agtype)`. Numeric agtype columns are cast before aggregation:
```sql
SUM(postCount::text::bigint)
```

### Graph schema

Organisations are stored as `University` and `Company` nodes (not a generic `Organisation` label).
KNOWS edges are stored bidirectionally (IU8 creates both `p1→p2` and `p2→p1`) because read
queries use directed patterns `(p)-[:KNOWS]->(friend)`.

### Query structure

Each `.sql` file begins with `SET search_path = ag_catalog, public;` followed by one or more
`SELECT * FROM cypher(...)` calls. The `executeTwoPartSql` helper splits at the first `;` to
execute the `SET search_path` preamble separately before the Cypher query.
