
-- SELECT levels
--      , PersonId
--      , Friends
--   FROM (SELECT Person1.personId AS PersonId
--              , STRING_AGG(Person2.personId, ';') WITHIN GROUP (GRAPH PATH) AS Friends
--              , LAST_VALUE(Person2.personId) WITHIN GROUP (GRAPH PATH) AS LastNode
--              , COUNT(Person2.personId) WITHIN GROUP (GRAPH PATH) AS levels
-- 	      FROM Person AS Person1
--              , Person_knows_Person FOR PATH AS fo
--              , Person FOR PATH AS Person2
-- 	     WHERE MATCH(SHORTEST_PATH(Person1(-(fo)->Person2)+))
-- 	       AND Person1.personId = :person1Id
--      ) AS Q
--  WHERE Q.LastNode = :person2Id
 
DECLARE @trustedPaths as table
(
    Id bigint,
    OrderDiscovered int,
    Path varchar(MAX)
)

INSERT INTO @trustedPaths
EXEC dbo.knows_Breadth_First :person1Id, :person2Id;

SELECT TOP(1) OrderDiscovered, Id, Path
  FROM @trustedPaths
  ORDER BY OrderDiscovered ASC;
