SET search_path = ag_catalog, public;
SELECT personId, personFirstName, personLastName, commonInterestScore, personGender, personCityName
FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)-[:IS_LOCATED_IN]->(city:City)
    WHERE friend.id <> $personId
    OPTIONAL MATCH (p)-[directKnows:KNOWS]-(friend)
    WITH p, friend, city, directKnows
    WHERE directKnows IS NULL
    WITH DISTINCT p, friend, city
    OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)
    WITH p, friend, city, count(DISTINCT post) AS postCount
    OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(commonPost:Post)-[:HAS_TAG]->(:Tag)<-[:HAS_INTEREST]-(p)
    WITH friend, city, postCount, count(DISTINCT commonPost) AS commonPostCount
    RETURN friend.id, friend.firstName, friend.lastName,
           commonPostCount - (postCount - commonPostCount),
           friend.gender, city.name, friend.birthday
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          commonInterestScore agtype, personGender agtype, personCityName agtype, birthday agtype)
) candidates
WHERE (
  EXTRACT(MONTH FROM TO_TIMESTAMP(birthday::text::bigint / 1000.0)) = $month
  AND EXTRACT(DAY FROM TO_TIMESTAMP(birthday::text::bigint / 1000.0)) >= 21
) OR (
  EXTRACT(MONTH FROM TO_TIMESTAMP(birthday::text::bigint / 1000.0)) = ($month % 12) + 1
  AND EXTRACT(DAY FROM TO_TIMESTAMP(birthday::text::bigint / 1000.0)) < 22
)
ORDER BY commonInterestScore DESC, personId ASC
LIMIT 10;
