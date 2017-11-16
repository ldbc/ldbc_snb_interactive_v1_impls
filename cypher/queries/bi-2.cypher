// Q2. Top tags for country, age, gender, time
/*
  :param {
    date1: 20091231230000000,
    date2: 20101107230000000,
    country1: 'Ethiopia',
    country2: 'Spain'
  }
*/
WITH
     apoc.date.parse('2013-01-01','ms','yyyy-MM-dd') AS end,
     365*24*60*60*1000.0 AS year
MATCH
  (country:Country)<-[:IS_PART_OF]-(:City)<-[:PERSON_IS_LOCATED_IN]-(person:Person)
  <-[:COMMENT_HAS_CREATOR|POST_HAS_CREATOR]-(message:Message)-[:COMMENT_HAS_TAG|POST_HAS_TAG]->(tag:Tag)
WHERE message.creationDate >= $date1
  AND message.creationDate <= $date2
  AND (country.name = $country1 OR country.name = $country2)
WITH
  country,
  apoc.date.field(message.creationDate,'month') AS month,
  person.gender AS gender,
  floor((end-person.birthday)/year/5) AS ageGroup,
  tag.name AS tagName,
  count(message) AS messageCount
WHERE messageCount > 100
RETURN
  country.name AS countryName,
  month,
  gender,
  ageGroup,
  tagName,
  messageCount
ORDER BY
  messageCount DESC,
  tagName ASC,
  ageGroup ASC,
  gender ASC,
  month ASC,
  countryName ASC
LIMIT 100
