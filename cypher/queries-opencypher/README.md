# Cypher queries

Implementations using [standard openCypher](https://github.com/opencypher/openCypher/blob/master/docs/standardisation-scope.adoc) features.

Use the `./check-feature.sh` script to check for Cypher features used in the implementations. Some examples:

```bash
# variable-length paths
./check-feature.sh ':\w*\*'
# count
./check-feature.sh 'count('
# negative pattern conditions (approximative)
./check-feature.sh 'NOT ('
# UNWIND
./check-feature.sh 'UNWIND'
```
