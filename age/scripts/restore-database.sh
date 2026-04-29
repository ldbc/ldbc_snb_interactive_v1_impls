#!/usr/bin/env bash
# Restore the database from a snapshot, then VACUUM ANALYZE.
# Run before each benchmark re-run to reset update-mutated state.
# Usage: ./restore-database.sh
set -euo pipefail

: "${CONNECTION_STRING:?CONNECTION_STRING environment variable must be set}"
SNAPSHOT_FILE="${SNAPSHOT_FILE:-/tmp/ldbc_snb_snapshot.dump}"

echo "Restoring from snapshot: ${SNAPSHOT_FILE}"
# AGE stores graphid = schema OID in ag_graph/ag_label.
# We must clean out all graph metadata before restoring, then fix up the OID
# after pg_restore creates a new schema with a fresh OID.
psql "$CONNECTION_STRING" -c "LOAD 'age'; SET search_path = ag_catalog, '\$user', public; SELECT drop_graph('ldbc_snb', true);" 2>/dev/null || true
psql "$CONNECTION_STRING" -c "DROP SCHEMA IF EXISTS ldbc_snb CASCADE;" 2>/dev/null || true

# Restore schema + data (ag_catalog schema CREATE will warn but continue)
pg_restore --no-owner --format=custom \
  --dbname="$CONNECTION_STRING" "${SNAPSHOT_FILE}" 2>/dev/null || true

# Fix OID mismatch: pg_restore creates the ldbc_snb schema with a fresh OID, but
# the dump's COPY data for ag_graph/ag_label carries the original OID.  Both
# tables need their FK triggers disabled so we can update them consistently.
psql "$CONNECTION_STRING" <<'FIXSQL'
DO $$
DECLARE
  old_oid  oid;
  real_oid oid;
BEGIN
  SELECT graphid INTO old_oid  FROM ag_catalog.ag_graph WHERE name = 'ldbc_snb';
  SELECT oid     INTO real_oid FROM pg_catalog.pg_namespace WHERE nspname = 'ldbc_snb';
  IF old_oid IS DISTINCT FROM real_oid THEN
    ALTER TABLE ag_catalog.ag_label DISABLE TRIGGER ALL;
    ALTER TABLE ag_catalog.ag_graph DISABLE TRIGGER ALL;
    UPDATE ag_catalog.ag_graph SET graphid = real_oid WHERE name    = 'ldbc_snb';
    UPDATE ag_catalog.ag_label SET graph   = real_oid WHERE graph   = old_oid;
    ALTER TABLE ag_catalog.ag_graph ENABLE TRIGGER ALL;
    ALTER TABLE ag_catalog.ag_label ENABLE TRIGGER ALL;
  END IF;
END $$;
FIXSQL

echo "Running VACUUM ANALYZE after restore..."
bash "$(dirname "$0")/vacuum-analyze.sh"
echo "Restore complete."
