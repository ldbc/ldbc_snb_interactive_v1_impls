/* DEL 8: Remove friendship
\set person1Id 8796093023099
\set person2Id 4398046511230
 */
DELETE FROM Person_knows_Person
WHERE (Person1Id = :person1Id AND Person2Id = :person2Id)
   OR (Person1Id = :person2Id AND Person2Id = :person1Id)
;
