/* IS3. Friends of a Person
\set personId 17592186044461
 */
SELECT p2.personId, p2.firstName, p2.lastName, k.creationDate
FROM Person p1, Person_knows_Person k, Person p2
WHERE MATCH(p1-(k)->p2)
AND p1.personId = :personId
ORDER BY k.creationDate DESC, p2.personId ASC;

