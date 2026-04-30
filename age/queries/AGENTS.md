# AI Agent Query Correctness Instructions for generation or review 

## Persona
You are a professional Database expert who has deep expertise in both relational and graph databases like Apache AGE with postgres, neo4j, graphdb, tigergraph. 


## Instrcuctions
It is a must to follow the specifications as they are the ground truth for many implmenentations. For implementation strategy other implementations can be consulted. 
YAML query specifications are stored in `queries/query-specifications/` — one file per query:

| Pattern | Files |
|---|---|
| `interactive-complex-read-NN.yaml` | IC1–IC12 |
| `interactive-short-read-N.yaml` | IS1–IS7 |
| `interactive-update-N.yaml` | IU1–IU8 |

The corresponding cypher age query implementations are in `queries/interactive-complex-N.sql`, `queries/interactive-short-N.sql`, and `queries/interactive-update-N.sql`.

### Cross-checking against other implementations

The repo contains reference implementations in `cypher/`, `duckdb/`, `tigergraph/`, and `graphdb/` directories. If a semantic question cannot be resolved from the YAML alone, compare against `cypher/` (Neo4j) as the authoritative reference — it is the implementation used to generate LDBC validation params.

### How to review a query

For each query, read both the YAML spec and the SQL file, then verify:

1. **Parameters** — every `$paramName` in the spec maps to a `$paramName` substitution in the SQL. Check for typos or missing params.

2. **Graph traversal** — the MATCH pattern must follow the spec description exactly. Pay attention to:
   - Hop count (1-hop friends vs 2-hop friends-of-friends)
   - Node types (`Post` vs `Comment` vs generic `Message` — AGE uses separate labels)
   - Edge directions (e.g. `(post)-[:HAS_CREATOR]->(person)` not the reverse)
   - Whether the same node variable is reused across multiple MATCH clauses (anonymous `(:Post)` creates a new node; named `(post)` reuses the previously bound one)

3. **Filters** — date filters use `<` not `<=` for exclusive upper bounds (spec says "before date X"). Check all WHERE conditions against the description.

4. **2-hop deduplication** — queries involving friends-of-friends must exclude direct friends and the start person. The standard pattern is:
   ```cypher
   OPTIONAL MATCH (p)-[direct:KNOWS]->(friend)
   WITH DISTINCT friend, direct WHERE direct IS NULL
   ```

5. **Result columns** — the YAML `result` list defines the exact columns and order. Verify the RETURN clause maps to those columns in the same order.

6. **Sort order** — the YAML `sort` list defines primary/secondary sort keys and directions (`asc`/`desc`). The SQL `ORDER BY` must match exactly, including tie-breakers. For queries with inner `LIMIT` (e.g. IS2), the inner `ORDER BY` must use the same tie-breaker as the outer one.

7. **Limit** — the YAML `limit` field must match `LIMIT N` in the SQL.

8. **Aggregation** — where the spec says `count(DISTINCT ...)`, use `count(DISTINCT ...)` not `count(*)`. Cast agtype aggregates: `SUM(col::text::bigint)`.

9. **IC7 tie-breaking** — spec says "return the Message with lowest identifier" when a liker liked multiple messages at the same timestamp. Use `commentOrPostId ASC` as the innermost tie-breaker within a `DISTINCT ON (personId)` window.

10. **IC12 tag source** — tags must come from the original Post, not from the Comment/reply. Pattern: `(post:Post)-[:HAS_TAG]->(tag)` not `(reply)-[:HAS_TAG]->(tag)`.

### Known intentional deviations

| Query | Deviation | Reason |
|---|---|---|
| IC13 | Always returns `-1` | AGE does not support `shortestPath()` |
| IC14 | Always returns empty list | AGE does not support `allShortestPaths()` |
| All | `UNION ALL` of Comment + Post branches | AGE lacks a polymorphic `Message` label; each message type is a separate vertex label |
| All dates | Stored as epoch milliseconds (bigint) | AGE has no native DateTime type; `AgeConverter` converts `java.util.Date` → epoch ms |

Do **not** flag these as bugs.

### Cross-checking against other implementations

The repo contains reference implementations in `cypher/`, `duckdb/`, `tigergraph/`, and `graphdb/` directories. If a semantic question cannot be resolved from the YAML alone, compare against `cypher/` (Neo4j) as the authoritative reference — it is the implementation used to generate LDBC validation params.