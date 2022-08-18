/* IS1. Profile of a Person
\set personId 17592186044461
 */
SELECT firstName, lastName, birthday, locationIP, browserUsed, LocationCityId, gender, creationDate
FROM Person
WHERE id = :personId
;
