# GSQL queries

// IS1. Profile of a person
// IS1. query description is on page 60 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf

// IS2. Recent messages of a person
// IS2. query description is on page 61 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf

// IS3. Friends of a person
// IS3. query description is on page 62 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf

// IS4. Content of a message
// IS4. query description is on page 62 https://ldbc.github.io/LDBC_SNB_docs/ldbc-snb-specification.pdf

// IS5. Creator of a message
// IS5. query description is on page 63 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf

// IS6. Forum of a message
// IS6. query description is on page 63 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf

// IS7. Replies of a message
// IS7. query description is on page 63 https://ldbc.github.io/ldbc_snb_docs/ldbc-snb-specification.pdf
/*
Given a Message, retrieve the (1-hop) Comments that reply to it.
In addition, return a boolean flag knows indicating if the author of the reply (replyAuthor) knows
the author of the original message (messageAuthor). If author is same as original author, return
False for knows flag.
*/


// IC1. Transitive friends with certain name
// p. 46

// IC2. Given a start Person (person), find the most recent Messages from all of that Person’s friends (friend  nodes). 
//      Only consider Messages created before the given maxDate (excluding that day)
// P. 47

// IC3. Friends and friends of friends that have been to given countries
// p. 48
