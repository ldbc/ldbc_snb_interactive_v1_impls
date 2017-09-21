For example, if you would like to create a user `usr` with a password `pwd`:

```
CREATE USER usr PASSWORD 'pwd';
ALTER ROLE usr WITH login createdb superuser;
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
