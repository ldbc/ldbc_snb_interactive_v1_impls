#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DO
    \$do\$
    BEGIN
    IF EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE  rolname = '${POSTGRES_USER}') THEN
        RAISE NOTICE 'Role ${POSTGRES_USER} already exists. Skipping.';
    ELSE
        BEGIN   -- nested block
            CREATE ROLE ${POSTGRES_USER} LOGIN PASSWORD '${POSTGRES_PASSWORD}';
        EXCEPTION
            WHEN duplicate_object THEN
                RAISE NOTICE 'Role ${POSTGRES_USER} was just created by a concurrent transaction. Skipping.';
        END;
    END IF;
	GRANT ALL PRIVILEGES ON DATABASE ${POSTGRES_DB} TO ${POSTGRES_USER};
    END
    \$do\$;
EOSQL