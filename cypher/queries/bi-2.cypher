// Q2. Top tags for country, age, gender, time
/*
  :param {
    date1: '2009-12-31T23:00:00.000+0000',
    date2: '2010-11-07T23:00:00.000+0000',
    country1: 'Ethiopia',
    country2: 'Spain'
  }
*/
MATCH
    (country:Country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(person:Person)
    <-[:hasCreator]-(message:Message)-[:hasTag]->(tag:Tag)
WHERE message.creationDate >= $date1
  AND message.creationDate <= $date2
  AND (country.name = $country1 OR country.name = $country2)
WITH
  country.name AS countryName,
  toInteger(substring(message.creationDate, 5, 2)) AS month,
  person.gender AS gender,
  floor(toFloat(2013-toInteger(substring(person.birthday, 0, 4)))/5) AS ageGroup,
  tag.name AS tagName,
  message
WITH
  countryName, month, gender, ageGroup, tagName, count(message) AS messageCount
WHERE messageCount > 100
RETURN
  countryName,
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
