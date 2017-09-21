# LDBC SNB Postgres implementation

The default configuration uses the `ldbcsf1` database.

For example, if you would like to create a user `usr` with a password `pwd`.

On a typical Ubuntu install, you might want to run:

```bash
sudo -u postgres psql
```

Create your user and grant the required privileges and set the password (e.g. `foo`):

```
CREATE USER ldbc PASSWORD 'ldbc';
ALTER ROLE ldbc WITH login createdb superuser;
```

Use the following `params.ini` configuration for testing:

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

Run the load script:

```bash
./load.sh ldbcsf1 <data_dir> <your_pg_user> <your_pg_password>
```

If you get _Permission denied_ errors, change the permissions of your home directory to 755 - but please make sure you understand its implications first:

```bash
chmod 755 ~
```
