# LDBC SNB PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Start the database

```bash
export POSTGRES_DATA_DIR=`pwd`/../../ldbc_snb_data_converter/data/csv-composite-merged-fk/
./start.sh
./load.sh
```



<!-- deprecated -->

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

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVMergeForeign` serializer classes of the [Datagen](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
generator.scaleFactor:0.003
generator.mode:interactive
serializer.format:CsvMergeForeign
```

### Loading the data set

To run the load script, go the `load-scripts` directory, set the `PG_` environment variables (optional) and issue the following command.
For special loading options, see below.

```bash
export PG_CSV_DIR=
export PG_DB_NAME=
export PG_USER=
export PG_PORT=
scripts/load.sh
```

The `load.sh` script (re)generates PostgreSQL-specific CSV files for posts and comments, if any of the following holds:

 - they don't exist
 - the source CSV is newer than the generated one
 - the user forces to do so by setting the environment variable `PG_FORCE_REGENERATE=yes` (see below for special loading options.)

The `load.sh` has default options that loads the dataset in the generator's directory to the `ldbcsf1` database with your current user. If these fit your needs, just run the script as `scripts/load.sh`.

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
#### Special loading options

In order to allow for easier experimenting, PostgreSQL converter (`load.sh`) accept a few special options using environment variables.
Most probably you won't need to touch them unless you are experimenting.

 - `PG_FORCE_REGENERATE`: you can force the loader to (re)generate PostgreSQL-specific CSV files for posts and comments. Possible values are:
    - `yes`: (re)generate PostgreSQL-specific CSV files for posts and comments.
    - `no`: (re)generate only PostgreSQL-specific CSV files if they don't exist of older than their source files. For performance reasons, **this is the default**.

 - `PG_CREATE_MESSAGE_FILE`: control creating a unified `message` data file of posts and comments. Possible values:
    - `no`: don't create message file, as we traditionally did. **This is the default.**
    - `create`: create message file, with no guarantee on being sorted
    - `sort_by_date`:  create message file, sorted by creation date.
       **Note:** when you switch messages file to be sorted, you need to use `PG_FORCE_REGENERATE=yes`, orherwise regenerating depends whether the source files have changed.

 - `PG_LOAD_TO_DB`: controls whether we want to do or skip database loading phase. Possible values are:
    - `load`: loads to the database, as one might expect. **This is the default.**
    - `skip`: skip loading to the database. Use this to generate only CSV files.
