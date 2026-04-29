# AGE queries

SQL/Cypher queries for Apache AGE. Each `.sql` file begins with `SET search_path = ag_catalog, public;`
followed by one or more `SELECT * FROM cypher(...)` calls.

Use the `./check-feature.sh` script to check for Cypher features used across query files. Some examples:

```bash
# variable-length paths
./check-feature.sh ':\w*\*'
# count
./check-feature.sh 'count('
# OPTIONAL MATCH
./check-feature.sh 'OPTIONAL MATCH'
# UNWIND
./check-feature.sh 'UNWIND'
# UNION ALL
./check-feature.sh 'UNION ALL'
```

## Notes

- **IC13 / IC14**: Apache AGE does not support `shortestPath()` or `allShortestPaths()`. These are degraded stubs — IC13 always returns `-1`, IC14 always returns an empty list. Their `.sql` files are placeholders only.
- All dates are stored and compared as epoch milliseconds (bigint).
- Pattern predicates in `WHERE` or `CASE WHEN` are not supported by AGE; these are rewritten using `OPTIONAL MATCH` + null checks.
