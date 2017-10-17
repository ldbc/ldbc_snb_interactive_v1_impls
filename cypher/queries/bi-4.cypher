// Popular topics in a country
MATCH (:Country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(person:Person)<-[:hasModerator]-(forum:Forum)-[:containerOf]->(post:Post)-[:hasTag]->(:Tag)-[:hasType]->(:TagClass)
RETURN forum.id, forum.title, forum.creationDate, person.id, count(post) AS count
ORDER BY count DESC, forum.id ASC
LIMIT 20
