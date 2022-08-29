-- Remove friendship
DELETE FROM Person_knows_Person
WHERE Person1Id = :person1Id 
  AND Person2Id = :person2Id 
   OR Person1Id = :person2Id 
  AND Person2Id = :person1Id