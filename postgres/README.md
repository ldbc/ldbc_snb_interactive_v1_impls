# LDBC SNB PostgreSQL implementation

[PostgreSQL](https://www.postgresql.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Configuring the database

The default configuration uses the `ldbcsf1` database.

On a typical Ubuntu install, you might want to run:

```bash
sudo -u postgres psql
```

To allow access from JDBC, you have to set a password. For example, to set the default password of `foo`, issue the following command:

```sql
ALTER ROLE usr PASSWORD 'foo';
```

If you want to create a separate user `usr` with the password `foo`, use the following commands:

```sql
CREATE USER usr PASSWORD 'foo';
ALTER ROLE usr WITH login createdb superuser;
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

To run the load script, go the `load-scripts` directory and issue the following command:

```bash
./load.sh <absolute_path_of_data_dir> <database> <your_pg_user>
```

The `load.sh` has default options that will load a small dataset to the `ldbcsf1` database with your current user. If these fit your installation, just run the script as `./load.sh`.

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
