---
name: amorfs-etl architecture
overview: Create a standalone TypeScript console ETL app that streams LDBC SNB SF0.1 CSVs into Neo4j as a strict Amorfs 4-uniform hypergraph, with enforced schema constraints, preview/approval flow, batched ingestion, and post-load verification.
todos:
  - id: scaffold-amorfs-etl
    content: Create standalone TypeScript console scaffold and environment configuration for amorfs-etl.
    status: pending
  - id: implement-neo4j-core
    content: Implement Neo4jClient and Neo4jSchemaManager with Concept constraints, fulltext index, and verb seeding.
    status: pending
  - id: build-stream-mapper
    content: Implement CsvStreamProcessor, AmorfsMapper, and batch UNWIND ingestion pipeline with strict Amorfs validation.
    status: pending
  - id: approval-and-verification
    content: Implement discovery mapping-plan logging, user Y/N approval gate, and post-load invariants verification.
    status: pending
  - id: docs-and-quickstart
    content: Write README with Docker/remote Neo4j setup and end-to-end Quick Start commands.
    status: pending
isProject: false
---

# amorfs-etl System Architecture and Implementation Plan (Refined for Amorfs v0.1 + v0.61 Compliance)

## Scope and conformance objective
- Build a standalone TypeScript console ETL app at [amorfs-etl](amorfs-etl) that upgrades from a basic loader to a standard-conformant Amorfs engine.
- Read LDBC SNB SF0.1 data from configurable default path `../ldbc/cypher/scripts/social_network-sf0.1-CsvComposite-LongDateFormatter/social_network`.
- Target Neo4j (local Docker or remote) via `neo4j-driver` and `.env` config.
- Enforce 100% adherence to:
  - Amorfs Format Specification v0.1 structural invariants.
  - v0.61 compliance requirements for strict numeric parsing and identity consistency.

## Revised architecture blueprint
- **Entry and orchestration**:
  - [amorfs-etl/src/index.ts](amorfs-etl/src/index.ts)
  - [amorfs-etl/src/cli.ts](amorfs-etl/src/cli.ts)
- **Infrastructure**:
  - [amorfs-etl/src/db/Neo4jClient.ts](amorfs-etl/src/db/Neo4jClient.ts): connectivity, sessions, transaction wrappers.
  - [amorfs-etl/src/db/Neo4jSchemaManager.ts](amorfs-etl/src/db/Neo4jSchemaManager.ts): constraints, indexes, verb seeding.
  - [amorfs-etl/src/db/cypher.ts](amorfs-etl/src/db/cypher.ts): canonical write/read query templates.
- **Streaming + parsing**:
  - [amorfs-etl/src/ingest/CsvStreamProcessor.ts](amorfs-etl/src/ingest/CsvStreamProcessor.ts): `csv-parse` row streaming, strict numeric parser, provenance envelope extraction.
  - [amorfs-etl/src/ingest/BatchExecutor.ts](amorfs-etl/src/ingest/BatchExecutor.ts): UNWIND micro-batches and retry policy.
- **Semantic mapping core**:
  - [amorfs-etl/src/mapping/AmorfsMapper.ts](amorfs-etl/src/mapping/AmorfsMapper.ts): strict SVO/hyperedge transformation with implied concept support.
  - [amorfs-etl/src/mapping/IdStrategy.ts](amorfs-etl/src/mapping/IdStrategy.ts): deterministic IDs for base/association/abstract concepts.
  - [amorfs-etl/src/mapping/rules](amorfs-etl/src/mapping/rules): entity and relation mapping definitions.
- **Compliance + observability**:
  - [amorfs-etl/src/logging/ConsolidatedLogger.ts](amorfs-etl/src/logging/ConsolidatedLogger.ts): mapping plan and conformance counters.
  - [amorfs-etl/src/verify/Verifier.ts](amorfs-etl/src/verify/Verifier.ts): invariant enforcement checks.
  - [amorfs-etl/src/verify/ComplianceReport.ts](amorfs-etl/src/verify/ComplianceReport.ts): v0.1/v0.61 pass/fail report.

```mermaid
flowchart TD
  csvSource["LDBCCsvFiles"] --> discovery["Phase1DiscoveryAndPlan"]
  discovery --> approval{"Phase2UserApproval"}
  approval -->|"Y"| ingest["Phase3StreamedIngestion"]
  approval -->|"N"| stop["AbortNoWrites"]
  ingest --> parser["StrictCsvAndNumberParsing"]
  parser --> mapper["AmorfsMapperStandardLevel"]
  mapper --> batch["UNWINDBatchExecutor"]
  batch --> graph["Neo4jConceptGraph"]
  graph --> verify["Phase4InvariantVerification"]
  verify --> multilingual["Phase5MultilingualExpressionMapping"]
  multilingual --> report["ComplianceReportv061"]
```

## Standard-level semantic mapping rules
### 1) Implied Concepts support
- Add explicit support for concepts that have no direct expression and derive meaning through associations only.
- `AmorfsMapper` will emit such nodes as `(:Concept {id, kind:'implied', expression:null})` and attach them only through valid association concepts.
- Examples:
  - abstract `friendship` concept between two person instance hubs.
  - abstract `address` concept connected to constituent location concepts.

### 2) Provenance as associations (preferred), metadata as supplemental
- Relationship-style provenance must be represented as queryable concept associations rather than hidden only in `metadata`.
- For provenance facts like `derived_from`, `measured_by`, `imported_from`:
  - create provenance concept nodes and connect through reified association concepts.
- Keep `metadata` JSON on association concept for temporal and non-relational audit payloads only.
- Rule: if provenance can be expressed as a graph relation, it must be an association first; `metadata` is secondary.

### 3) Strict numeric parsing (v0.61)
- `CsvStreamProcessor` includes a strict numeric lexer before mapping:
  - accept only valid JSON number grammar.
  - reject leading zeros (except zero itself), malformed exponents, `+` prefixed numbers, and locale-formatted numbers.
- Any parse violation:
  - marks row as non-conformant.
  - logs file/line/column and halts ingestion in strict mode.

## Amorfs model enforcement (hard invariants)
- Every node uses only label `:Concept`; no entity labels (`:Person`, `:Post`, etc.).
- Only two authorized verb concepts are allowed:
  - `verb_has_a`
  - `verb_which_is`
- Every association is a reified concept with exactly three outgoing links:
  - one `:hasSubject`
  - one `:hasVerb`
  - one `:hasObject`
- Each LDBC instance row creates an Instance Hub and a `which_is` association to its type concept.

## Robust concept identity and deterministic ID strategy
- **Base concept IDs**: deterministic hash of canonical identity:
  - `hash(namespace + conceptRole + normalizedExpressionOrSourceKey)`
- **Association IDs**: deterministic hash of canonical tuple:
  - `hash(subjectId + verbId + objectId + canonicalMetadataJson)`
- **Abstract/implied concept IDs**: deterministic synthetic key:
  - `hash(namespace + impliedType + normalizedContextSignature)`
  - context signature includes source file, semantic role, and anchored participant IDs.
- Canonicalization rules before hashing:
  - stable key order for JSON metadata.
  - UTF-8 normalization and whitespace normalization on expressions.
  - explicit null handling (`null` vs empty string distinguished).
- Outcome: idempotent reruns merge naturally and avoid duplicate conceptual nodes.

## Updated execution workflow phases
- **Phase 1: Discovery and Mapping Plan**
  - scan mandatory CSV files and headers.
  - infer mapping classes (intrinsic, contextual, implied, provenance associations).
  - estimate concept expansion and association reification counts.
- **Phase 2: User Approval**
  - print plan and prompt `[Y/N]`.
  - `N` aborts with zero writes.
- **Phase 3: Streamed Ingestion**
  - parse rows via strict CSV + numeric validation.
  - map row to concepts/associations, then write via UNWIND batches.
- **Phase 4: Verification (Invariant Enforcement)**
  - enforce association fan-out exactness (1 subject, 1 verb, 1 object).
  - enforce uniformity (`:Concept` only, no extra labels).
  - enforce verb seed protection (exactly and only authorized verbs).
  - verify deterministic ID integrity (recomputation spot checks).
- **Phase 5: Multi-lingual Expression Mapping**
  - support datasets like `person_speaks` where one concept links to multiple expressions.
  - represent language-specific expressions as additional concepts linked through valid associations while preserving one conceptual identity.
  - validate no violation of two-verb and 3-edge association invariants.

## Expanded verification layer (queries and checks)
- `Verifier` must run blocking checks and fail on any non-zero violation count:
  - `associationFanoutViolationCount`
  - `nonConceptLabelCount`
  - `unauthorizedVerbCount`
  - `missingSubjectOrVerbOrObjectCount`
  - `strictNumberParseViolationCount`
  - `duplicateIdentityCollisionCount`
- `ComplianceReport` outputs:
  - per-invariant pass/fail.
  - totals and sampled offending IDs.
  - readiness decision: `CONFORMANT` or `NON_CONFORMANT`.

## Deliverables (updated)
- [amorfs-etl/package.json](amorfs-etl/package.json)
  - runtime: `neo4j-driver`, `csv-parse`, `dotenv`, `commander`, `pino`, `zod`.
  - dev: `typescript`, `tsx`, `@types/node`, lint/format tooling.
- [amorfs-etl/src/db/Neo4jClient.ts](amorfs-etl/src/db/Neo4jClient.ts)
  - connection and retries.
  - schema bootstrap delegation.
  - safe managed transactions.
- [amorfs-etl/README.md](amorfs-etl/README.md)
  - Docker and remote setup.
  - Quick Start for all phases.
  - compliance mode flags.
  - performance benchmark section:
    - target parsing throughput: 10,000 concepts in < 1 second on baseline dev hardware.
    - memory objective: O(n) with bounded stream-window behavior and no full-file buffering.

## Proposed file blueprint (revised)
- [amorfs-etl/.env.example](amorfs-etl/.env.example)
- [amorfs-etl/tsconfig.json](amorfs-etl/tsconfig.json)
- [amorfs-etl/src/index.ts](amorfs-etl/src/index.ts)
- [amorfs-etl/src/config.ts](amorfs-etl/src/config.ts)
- [amorfs-etl/src/cli.ts](amorfs-etl/src/cli.ts)
- [amorfs-etl/src/domain/types.ts](amorfs-etl/src/domain/types.ts)
- [amorfs-etl/src/db/Neo4jClient.ts](amorfs-etl/src/db/Neo4jClient.ts)
- [amorfs-etl/src/db/Neo4jSchemaManager.ts](amorfs-etl/src/db/Neo4jSchemaManager.ts)
- [amorfs-etl/src/db/cypher.ts](amorfs-etl/src/db/cypher.ts)
- [amorfs-etl/src/ingest/CsvStreamProcessor.ts](amorfs-etl/src/ingest/CsvStreamProcessor.ts)
- [amorfs-etl/src/ingest/BatchExecutor.ts](amorfs-etl/src/ingest/BatchExecutor.ts)
- [amorfs-etl/src/mapping/AmorfsMapper.ts](amorfs-etl/src/mapping/AmorfsMapper.ts)
- [amorfs-etl/src/mapping/IdStrategy.ts](amorfs-etl/src/mapping/IdStrategy.ts)
- [amorfs-etl/src/mapping/rules/person.ts](amorfs-etl/src/mapping/rules/person.ts)
- [amorfs-etl/src/mapping/rules/person_knows_person.ts](amorfs-etl/src/mapping/rules/person_knows_person.ts)
- [amorfs-etl/src/mapping/rules/person_speaks.ts](amorfs-etl/src/mapping/rules/person_speaks.ts)
- [amorfs-etl/src/logging/ConsolidatedLogger.ts](amorfs-etl/src/logging/ConsolidatedLogger.ts)
- [amorfs-etl/src/verify/Verifier.ts](amorfs-etl/src/verify/Verifier.ts)
- [amorfs-etl/src/verify/ComplianceReport.ts](amorfs-etl/src/verify/ComplianceReport.ts)

## Updated implementation sequence
1. Scaffold app and strict configuration schema.
2. Implement Neo4j client, schema manager, and verb seed protection.
3. Implement strict CSV and JSON-number parsing in stream processor.
4. Implement deterministic `IdStrategy` (base, association, abstract/implied).
5. Implement standard-level `AmorfsMapper` with implied concept support.
6. Implement provenance-as-association modeling; reserve `metadata` for supplemental temporal/audit payload.
7. Implement discovery plan output and interactive user approval gate.
8. Implement streamed UNWIND ingestion with bounded memory behavior.
9. Implement Phase 4 invariant verifier and compliance report generator.
10. Implement Phase 5 multi-lingual expression mapping (`person_speaks` class).
11. Expand additional entity/relation mapping rules and finalize conformance tests.
12. Finalize README with benchmark procedure and acceptance thresholds.

## Technical rationale
- Standard-level semantic fidelity requires graph-native provenance relationships; this improves explainability and downstream reasoning over provenance paths.
- Deterministic hashing across normalized identity tuples ensures idempotency, natural merge semantics, and stable references for audit/comparison.
- Strict numeric syntax validation prevents silent data drift and aligns ingestion with machine-verifiable JSON semantics in v0.61.
- Explicit invariant verification moves compliance from “best effort” to enforceable gatekeeping before declaring the graph conformant.
- Multi-lingual expression mapping preserves one conceptual identity while enabling language-specific lexical surfaces required for richer semantic retrieval.