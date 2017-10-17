# LDBC SNB PostgreSQL implementation

## Configuring the database

The default configuration uses the `ldbcsf1` database.

On a typical Ubuntu install, you might want to run:

```bash
sudo -u postgres psql
```

To allow access from JDBC, you have to set a password. For example, to set the default password of `foo`, issue the following command:

```
ALTER ROLE usr PASSWORD 'foo';
```

If you want to create a separate user `usr` with the password `foo`, use the following commands:

```
CREATE USER usr PASSWORD 'foo';
ALTER ROLE usr WITH login createdb superuser;
```

## Generaring test models

The load script expect models generated with the `CSVMergeForeign` serializers.

An example `params.ini` configuration for testing:

```
ldbc.snb.datagen.generator.scaleFactor:snb.interactive.1

ldbc.snb.datagen.generator.numPersons:50
ldbc.snb.datagen.generator.numYears:1
ldbc.snb.datagen.generator.startYear:2010

ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignPersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignInvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.CSVMergeForeignPersonActivitySerializer

ldbc.snb.datagen.generator.numThreads:1
ldbc.snb.datagen.serializer.outputDir:./test_data/
```

## Loading the data

Run the load script:

```bash
./load.sh <absolute_path_of_data_dir> <database> <your_pg_user>
```

The `load.sh` has default options. If these fit your installation, you can run it without any arguments (`./load.sh`).

If you get _Permission denied_ errors, change the permissions of your home directory to 755 - but please make sure you understand its implications first:

```bash
chmod 755 ~
```

To play around with the data, join PostgreSQL and switch to the database:

```sql
postgres=# \c ldbcsf1
You are now connected to database "ldbcsf1" as user "postgres".
ldbcsf1=# select count(*) from person;
# ...
```
