#!/usr/bin/env bash
# Load LDBC SNB data into Apache AGE via agefreighter.
# Data preprocessing and loading are outside the LDBC standard (client mandate).
# Usage: ./load-data.sh --sf <scale-factor>
set -euo pipefail

SF=""
while [[ $# -gt 0 ]]; do
  case $1 in
    --sf) SF="$2"; shift 2 ;;
    *) echo "Unknown argument: $1"; exit 1 ;;
  esac
done
: "${SF:?--sf argument required}"

GRAPHBENCH_DIR="${GRAPHBENCH_DIR:-$HOME/repositories/GraphBenchmarking}"
AGEFREIGHTER_VENV="${AGEFREIGHTER_VENV:-$HOME/repositories/agefreighter/.venv}"
CONVERTED_DIR="${GRAPHBENCH_DIR}/ldbc_snb_benchmark/converted/sf${SF}"

echo "=== Step 1: Preprocessing LDBC SF${SF} data ==="
source "${AGEFREIGHTER_VENV}/bin/activate"
python3 "${GRAPHBENCH_DIR}/ldbc_snb_benchmark/preprocess_ldbc.py" --sf "${SF}"

echo "=== Step 2: Loading into Apache AGE via agefreighter ==="
agefreighter load --config "${CONVERTED_DIR}/agefreighter_config.json"

echo "=== Step 3: Creating indexes ==="
: "${CONNECTION_STRING:?CONNECTION_STRING environment variable must be set}"
psql "$CONNECTION_STRING" -f "$(dirname "$0")/create-indexes.sql"

echo "=== Step 4: VACUUM ANALYZE ==="
bash "$(dirname "$0")/vacuum-analyze.sh"

echo "=== Data load complete for SF${SF} ==="
