# Generating small test data tests

To generate small data sets, use scale factor 1 (SF1) with the `numPersons` and `numYears` values set according to this template.
Note that a certain number of persons (~250) is required for the parameter generation script to function correctly.

```ini
ldbc.snb.datagen.generator.scaleFactor:snb.interactive.1

ldbc.snb.datagen.generator.numPersons:250
ldbc.snb.datagen.generator.numYears:1

ldbc.snb.datagen.serializer.personSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonSerializer
ldbc.snb.datagen.serializer.invariantSerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>InvariantSerializer
ldbc.snb.datagen.serializer.personActivitySerializer:ldbc.snb.datagen.serializer.snb.interactive.<SerializerType>PersonActivitySerializer
```
