#!/usr/bin/env bash
# Restore the database from a snapshot, then VACUUM ANALYZE.
# Run before each benchmark re-run to reset update-mutated state.
# Usage: ./restore-database.sh
set -euo pipefail

: "${CONNECTION_STRING:?CONNECTION_STRING environment variable must be set}"
SNAPSHOT_FILE="${SNAPSHOT_FILE:-/tmp/ldbc_snb_snapshot.dump}"

echo "Restoring from snapshot: ${SNAPSHOT_FILE}"
pg_restore --clean --if-exists --no-owner --format=custom \
  --dbname="$CONNECTION_STRING" "${SNAPSHOT_FILE}"

echo "Running VACUUM ANALYZE after restore..."
bash "$(dirname "$0")/vacuum-analyze.sh"
echo "Restore complete."
