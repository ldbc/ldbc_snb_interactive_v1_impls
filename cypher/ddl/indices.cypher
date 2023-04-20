// uniqueness constrains (implying an index)
// static nodes
CREATE CONSTRAINT FOR (n:Country)      REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:City)         REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Tag)          REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:TagClass)     REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Organisation) REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:University)   REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Company)      REQUIRE n.id IS UNIQUE;
// dynamic nodes
CREATE CONSTRAINT FOR (n:Message)      REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Comment)      REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Post)         REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Forum)        REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT FOR (n:Person)       REQUIRE n.id IS UNIQUE;

// name/firstName
CREATE INDEX FOR (n:Country)  ON n.name;
CREATE INDEX FOR (n:Person)   ON n.firstName;
CREATE INDEX FOR (n:Tag)      ON n.name;
CREATE INDEX FOR (n:TagClass) ON n.name;

// creationDate
CREATE INDEX FOR (n:Message)  ON n.creationDate;
CREATE INDEX FOR (n:Comment)  ON n.creationDate;
CREATE INDEX FOR (n:Post)     ON n.creationDate;
CREATE INDEX FOR (n:Forum)    ON n.creationDate;
CREATE INDEX FOR (n:Person)   ON n.creationDate;
