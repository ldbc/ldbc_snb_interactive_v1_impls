# LDBC SNB PostgreSQL implementation

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

## Generating small test graphs for PostgreSQL

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

## Loading the data to PostgreSQL

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

## Generating the validation data set

1. Follow the steps in the parent directory's README to set up the environment.

2. Generate the validation data set with the following parameters:

   ```bash
   java -cp target/postgres-0.0.1-SNAPSHOT.jar com.ldbc.driver.Client -db  com.ldbc.impls.workloads.ldbc.snb.jdbc.bi.PostgresBiDb -P readwrite_postgres--ldbc_driver_config--validation_parameter_creation.properties
   ```
