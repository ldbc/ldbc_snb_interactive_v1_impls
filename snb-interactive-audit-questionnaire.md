# Questionnaire for LDBC SNB test sponsors

We created the following questionnaire to make the SNB auditing process more streamlined. If you need any clarification, please reach out to the SNB task force.

## Checklist

- [ ] The SUT has a complete implementation of the benchmark which complies with the LDBC specification.
- [ ] The implementation uses a stable version of an LDBC SNB driver.
- [ ] The implementation passes cross-validation against one of the existing reference implementations on at least the SF10 data set.
- [ ] If you are not the vendor of the DBMS used in the SUT: do you have written permission of the vendor of the DBMS?

## System

### Overview

- Vendor name
- System name
- System version
- Date of the release to be used
- Link to product page
- If possible, please share your motivation for commissioning an LDBC audit (e.g. to demonstrate the maturity of your product, to achieve record-breaking performance, etc.)

### Test sponsor

- Are you a member company of the LDBC? _(Must be one to commission an audit)_
- Do you have written consent of the vendor of the DBMS used in the SUT? _(The Test Sponsor must have prior written consent from the vendor if the two are not the same company.)_
- Test sponsor representative _(This person is going to sign the Full Disclosure Report in case of a successful audit. Please include title, e.g. "Dr.", if applicable.)_

### High-level technical information

- System type (e.g. relational DBMS, graph DBMS)
- Storage type (in-memory/disk-based)
- Main implementation language of the system (e.g. C++)
- Query language(s) supported by the system (e.g. Cypher, Gremlin)
- Query language(s) used for the audited implementation (e.g. Cypher)
- Query execution strategy (interpreted/vectorized/compiled/etc.)
- Is a distributed version available (regardless of whether it is used in the audit)?
- If a distributed version is available, what sharding strategy does it use?

### Licensing

- Product license (e.g. proprietary license, Apache Software License v2)
- License of the Java/Python client libraries (used in the LDBC SNB Interactive/BI drivers, respectively)

### Database features

- Link to documentation
- Are stored procedures supported?
- If stored procedures are supported, what language(s) can they be implemented in?
- What is the maximum isolation level for transactions?
- Is the database client package available in a central repository (Maven Central, PyPI, Conda, etc.)? (if it is, please provide link)
- Is the database client available as an open-source project? (if it is, please provide link)
- Does the DBMS have any noteworthy features that should be mentioned in its introduction? (e.g. support for temporal queries, incremental view maintenance, etc.)

### Type system

- Data schema: is system schema-free, schema-optional or does it require the specification of the full schema?
- Does the system support Unicode natively?
- Does the system support a native datetime type?
- Does the system support an array datatype? (e.g. for storing attributes such as `person.speaks`)

## Data sets and loading

- Which scale factors (SFs) should be included in the audit?
- Which data layout is used? (e.g. composite attributes with merged foreign keys)
- Which date format is used? (e.g. Datagen's StringDateFormatter with a given formatter such as RFC-3339, LongDateFormatter, etc.)
- How is the initial loading step implemented (e.g. offline bulk loader)?

## Benchmark environment

- Cloud vendor (if applicable)
- Is the DBMS running in a containerized setup?
- Is the LDBC driver running in a containerized setup?
- On which machine is the LDBC driver running?
  - [ ] Same machine as the SUT
  - [ ] Different machine (please provide HW/instance type)
- Is a container orchestration system used? (e.g. Kubernetes - please describe which one)
- Which operating system(s) are used? (e.g. Ubuntu 22.04.1)
- Runtimes:
  - Interactive: Java version for running the driver (e.g. AdoptOpenJDK 8)
  - BI: Python version for running the driver (e.g. Python 3.8)
- Firewall ports to open (for multi-instance setup)
- How to access the web interface (if applicable)
- Disk storage
  - Disk configuration (e.g. RAID-0)
  - File system to use (e.g. xfs)

## Performance tuning

- What are the main database configurations ("tuning knobs") that are changed from their default values for the SNB implementation? (e.g. buffer size, size of connection pool, etc.)

## Implementation details

Please provide the following information **for each scale factor**.

### Setup

- What is the setup of the SUT:
  - Embedded (into the benchmark driver)
  - Client-server, single-node
  - Client-server, distributed
- Machine(s) for running the SUT (number of machines/HW/instance type)

### Costs

- Hardware costs (for cloud instances, the cost of a reserved instance for a 3-year term)
- Software license
- Maintenance fee (3-year period)

## For SNB Interactive implementations

Note that the minimum scale factor for SNB Interactive audits is SF30.

### ACID tests

- [ ] The ACID tests were implemented and pass with results that confirm the expected isolation level (minimum: read committed).

### Driver

- LDBC SNB driver version

### Queries

- What language were the operations implemented in?
  - Complex reads
  - Short reads
  - Updates

### Benchmark parameters

- Total compression ratio (TCR) with at least 95% on-time queries
- Total compression ratio (TCR) with 100% on-time queries (optional)
- Operation count
- Number of read threads
- Number of write threads

### Expected performance

Information about the expected performance helps the auditor quickly identify potential issues with the setup.

Please provide the following information **for each scale factor**.

- Expected loading time
- Expected throughput (ops/seconds)
- Expected warmup time (minimum 30 minutes, maximum 35 minutes)
- Expected benchmark time (minimum 2 hours, maximum 2 hours 15 minutes)

## Dissemination of the results

Upon a successful audit, we can announce the results through the following communication channels.

- Audited SNB benchmarks site: <https://ldbcouncil.org/benchmarks/snb>
- LDBC's Twitter: <https://twitter.com/ldbcouncil>
  - We will post a tweet such as: "Congratulations to @XXX on breaking the record on the LDBC Social Network Benchmark's Interactive workload for scale factor YYY with ZZZ operations/second"
  - We can at-mention your company in the tweet announcing the results.
  - We can display the logo of your company/product in the tweet.
- LDBC's board of directors mailing list

Please let us know your preferences for announcing the results:

- Is there an embargo for announcing the results? If so, when can we make the announcement?
- What is the Twitter handle of your company?
- Please attach a high-resolution logo of your company
- Please attach a high-resolution logo of your product (if it has its individual logo)
