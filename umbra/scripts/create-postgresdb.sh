#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$UMBRA_USER" --dbname "$UMBRA_DB" <<-EOSQL
    DO
    \$do\$
    BEGIN
    IF EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE  rolname = '${UMBRA_USER}') THEN
        RAISE NOTICE 'Role ${UMBRA_USER} already exists. Skipping.';
    ELSE
        BEGIN   -- nested block
            CREATE ROLE ${UMBRA_USER} LOGIN PASSWORD '${UMBRA_PASSWORD}';
        EXCEPTION
            WHEN duplicate_object THEN
                RAISE NOTICE 'Role ${UMBRA_USER} was just created by a concurrent transaction. Skipping.';
        END;
    END IF;
	GRANT ALL PRIVILEGES ON DATABASE ${UMBRA_DB} TO ${UMBRA_USER};
    END
    \$do\$;
EOSQL