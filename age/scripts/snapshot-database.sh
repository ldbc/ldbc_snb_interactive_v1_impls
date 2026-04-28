#!/usr/bin/env bash
# Create a pg_dump snapshot of the database before the first IU run.
# Restore with restore-database.sh before re-running benchmarks.
# Usage: ./snapshot-database.sh
set -euo pipefail

: "${CONNECTION_STRING:?CONNECTION_STRING environment variable must be set}"
SNAPSHOT_FILE="${SNAPSHOT_FILE:-/tmp/ldbc_snb_snapshot.dump}"

echo "Creating snapshot -> ${SNAPSHOT_FILE}"
pg_dump --format=custom --file="${SNAPSHOT_FILE}" "$CONNECTION_STRING"
echo "Snapshot saved."
