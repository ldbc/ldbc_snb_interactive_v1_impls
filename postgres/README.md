# LDBC SNB PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Configuring the database

The default configuration uses the `ldbcsf1` database.

On a typical Ubuntu install, you might want to run:

```bash
sudo -u postgres psql
```

To allow access from JDBC, you have to set a password. For example, to set the default password of user `postgres` to `foo`, issue the following command:

```sql
ALTER ROLE postgres PASSWORD 'foo';
```

If you want to create a separate user `usr` with the password `foo`, use the following commands:

```sql
CREATE USER usr PASSWORD 'foo';
ALTER ROLE usr WITH login createdb superuser;
```

## Allow access from all local users

Alternatively, you can allow any local users to access the database.

:warning: Warning: never do this on a production system.

Edit the `pg_hba.conf` file and add the following line:

```
local all postgres trust
# local all postgres peer
```

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVMergeForeign` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignPersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignPersonActivitySerializer
```

### Loading the data set

To run the load script, go the `load-scripts` directory, set the `PG_` environment variables (optional) and issue the following command:

```bash
export PG_DATA_DIR=
export PG_DB_NAME=
export PG_USER=
export PG_FORCE_REGENERATE=
export PG_PORT=
./load.sh
```

The `load.sh` (re)generates PostgreSQL-specific CSV files for posts and comments, if either 

 - they don't exist
 - the source CSV is newer than the generated one
 - the user forces to do so by setting the environment variable PG_FORCE_REGENERATE=yes

Most probably you won't need to touch this.

The `load.sh` has default options that loads the dataset in the generator's directory to the `ldbcsf1` database with your current user. If these fit your needs, just run the script as `./load.sh`.

If you get _Permission denied_ errors, change the permissions of your home directory to 755 - but please make sure you understand its implications first:

```bash
chmod 755 ~
```

To play around with the data, join PostgreSQL and switch to the database:

```sql
postgres=# \c ldbcsf1
You are now connected to database "ldbcsf1" as user "postgres".
ldbcsf1=# SELECT count(*) FROM person;
# ...
```
