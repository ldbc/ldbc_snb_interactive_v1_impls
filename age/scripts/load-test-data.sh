#!/usr/bin/env bash
# Copy LDBC SNB test data from the local cypher/ module and load it into an AGE graph.
# Run from the age/ directory.
# Usage: bash scripts/load-test-data.sh
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
AGE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
REPO_ROOT="$(cd "${AGE_DIR}/.." && pwd)"
SRC="${REPO_ROOT}/cypher/test-data"
DEST="${AGE_DIR}/test-data"

: "${CONNECTION_STRING:=postgresql://postgres:postgres@localhost:5432/postgres}"

echo "=== Copying test data from cypher/test-data ==="
mkdir -p \
  "${DEST}/vanilla/static" \
  "${DEST}/vanilla/dynamic" \
  "${DEST}/substitution_parameters" \
  "${DEST}/update_streams"

cp -r "${SRC}/vanilla/static/."          "${DEST}/vanilla/static/"
cp -r "${SRC}/vanilla/dynamic/."         "${DEST}/vanilla/dynamic/"
cp -r "${SRC}/substitution_parameters/." "${DEST}/substitution_parameters/"
cp -r "${SRC}/update_streams/."          "${DEST}/update_streams/"
echo "  Done."

echo ""
echo "=== Loading data into AGE graph ==="
VENV="${AGE_DIR}/.venv"
if [[ ! -x "${VENV}/bin/python3" ]]; then
  echo "Creating venv and installing psycopg2-binary..."
  python3 -m venv "${VENV}"
  "${VENV}/bin/pip" install -q psycopg2-binary
fi
"${VENV}/bin/python3" "${SCRIPT_DIR}/load-test-data.py" "${DEST}/vanilla" "${CONNECTION_STRING}"

echo ""
echo "=== Creating indexes ==="
psql "${CONNECTION_STRING}" -f "${SCRIPT_DIR}/create-indexes.sql"

echo ""
echo "=== Running VACUUM ANALYZE ==="
bash "${SCRIPT_DIR}/vacuum-analyze.sh"

echo ""
echo "=== Snapshotting database ==="
bash "${SCRIPT_DIR}/snapshot-database.sh"

echo "=== Test data load complete ==="
