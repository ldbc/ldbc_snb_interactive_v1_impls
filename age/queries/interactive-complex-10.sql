SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (p:Person {id: $personId})-[:KNOWS]-(:Person)-[:KNOWS]-(friend:Person)-[:IS_LOCATED_IN]->(city:City)
  WHERE friend.id <> $personId
    AND NOT (p)-[:KNOWS]-(friend)
  WITH p, friend, city, TO_TIMESTAMP(toFloat(friend.birthday) / 1000.0) AS bday
  WHERE (EXTRACT(MONTH FROM bday) = $month AND EXTRACT(DAY FROM bday) >= 21)
     OR (EXTRACT(MONTH FROM bday) = ($month % 12) + 1 AND EXTRACT(DAY FROM bday) < 22)
  WITH DISTINCT p, friend, city
  OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)
  WITH p, friend, city, collect(post) AS posts
  WITH friend,
       city,
       size(posts) AS postCount,
       size([pp IN posts WHERE (pp)-[:HAS_TAG]->()<-[:HAS_INTEREST]-(p)]) AS commonPostCount
  RETURN friend.id, friend.firstName, friend.lastName,
         commonPostCount - (postCount - commonPostCount),
         friend.gender, city.name
  ORDER BY (commonPostCount - (postCount - commonPostCount)) DESC, toInteger(friend.id) ASC
  LIMIT 10
$$) AS (personId agtype, personFirstName agtype, personLastName agtype,
        commonInterestScore agtype, personGender agtype, personCityName agtype);
