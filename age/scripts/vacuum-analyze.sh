#!/usr/bin/env bash
# Run VACUUM ANALYZE after data load or snapshot restore.
# Usage: ./vacuum-analyze.sh
set -euo pipefail

: "${CONNECTION_STRING:?CONNECTION_STRING environment variable must be set}"

echo "Running VACUUM ANALYZE..."
psql "$CONNECTION_STRING" -c "VACUUM (ANALYZE, VERBOSE);"
echo "Done."
