// Q16. Fake news detection
// Alternative implementation using labelling.
// Maybe this is somewhat easier to follow. No result on its performance yet.

// drop previous labels
MATCH (p:PersonX) REMOVE p:PersonX

WITH count(*) AS dummy

MATCH (tag:Tag {name: $tagA})<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person:Person)
WHERE date(message.creationDate) = $dateA
SET person:PersonX

WITH count(*) AS dummy

OPTIONAL MATCH (personX1:PersonX)-[:KNOWS]-(personX2:PersonX)
WITH personX1, count(personX2) AS cpx2
WHERE cpx2 > $maxKnowsLimit // remove the label from people who have more friends than the max. limit
REMOVE personX1:PersonX

WITH count(*) AS dummy

// compute scores in the rest
MATCH (tag:Tag {name: $tagA})<-[:HAS_TAG]-(messageX:Message)-[:HAS_CREATOR]->(personX:PersonX)
WHERE date(messageX.creationDate) = $dateA

RETURN personX.id, count(messageX) AS cm
ORDER BY cm DESC, personX.id ASC
