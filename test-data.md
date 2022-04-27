# Generating small test data tests

To generate small data sets, use scale factor 1 (SF1) with the `numPersons` and `numYears` values set according to this template.
Note that a certain number of persons (~250) is required for the parameter generation script to function correctly.

```ini
ldbc.snb.datagen.generator.scaleFactor:snb.interactive.1

ldbc.snb.datagen.generator.numPersons:250
ldbc.snb.datagen.generator.numYears:1

ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.<SerializerType>DynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.<SerializerType>DynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.<SerializerType>StaticSerializer
```
