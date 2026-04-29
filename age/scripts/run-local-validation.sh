#!/usr/bin/env bash
# End-to-end local validation against the Docker AGE instance on localhost:5432.
# Run from the age/ directory after building the JAR.
#
# Steps:
#   1. (Optional) Load test data: pass --load to trigger load-test-data.sh
#   2. Generate validation_params.csv using our implementation
#   3. Restore snapshot (resets IU mutations from step 2)
#   4. Run validate_database against the generated params
#
# Usage:
#   cd age
#   bash scripts/run-local-validation.sh [--load]
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
AGE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
JAR="${AGE_DIR}/target/age-1.2.0-SNAPSHOT.jar"

export CONNECTION_STRING="postgresql://postgres:postgres@localhost:5432/postgres"

LOCAL_VALIDATE="${AGE_DIR}/driver/validate-local.properties"
LOCAL_CREATE_PARAMS="${AGE_DIR}/driver/create-validation-parameters-local.properties"

# ---- Build local properties from templates -----------------------------------
fill_local_props() {
  local src="$1" dst="$2"
  sed \
    -e 's|age_endpoint=.*|age_endpoint=localhost:5432/postgres|' \
    -e 's|age_user=.*|age_user=postgres|' \
    -e 's|age_password=.*|age_password=postgres|' \
    "$src" > "$dst"
}

fill_local_props "${AGE_DIR}/driver/validate.properties" "${LOCAL_VALIDATE}"
fill_local_props "${AGE_DIR}/driver/create-validation-parameters.properties" "${LOCAL_CREATE_PARAMS}"

# ---- Optionally load test data -----------------------------------------------
if [[ "${1:-}" == "--load" ]]; then
  echo "=== Loading test data ==="
  bash "${SCRIPT_DIR}/load-test-data.sh"
fi

# ---- Check JAR exists --------------------------------------------------------
if [[ ! -f "${JAR}" ]]; then
  echo "ERROR: JAR not found at ${JAR}. Run: mvn clean package -DskipTests" >&2
  exit 1
fi

cd "${AGE_DIR}"

# ---- Step 1: Generate validation_params.csv ----------------------------------
echo ""
echo "=== Generating validation_params.csv ==="
java -cp "${JAR}" org.ldbcouncil.snb.driver.Client \
  -P "${LOCAL_CREATE_PARAMS}"

# ---- Step 2: Restore snapshot (reset IU mutations) ---------------------------
echo ""
echo "=== Restoring snapshot ==="
bash scripts/restore-database.sh

# ---- Step 3: Validate --------------------------------------------------------
echo ""
echo "=== Running validation ==="
java -cp "${JAR}" org.ldbcouncil.snb.driver.Client \
  -P "${LOCAL_VALIDATE}"

echo ""
echo "=== Validation complete ==="
