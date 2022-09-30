-- /* 
--  Trusted connection paths
--  person1id 10995116279771,
--  person2Id 30786325579204;
-- */
DECLARE @trustedPaths as table
(
    Id bigint,
    OrderDiscovered int,
    Path varchar(MAX),
    score bigint
)

INSERT INTO @trustedPaths
EXEC dbo.knows_Breadth_First_Weight :person1Id, :person2Id;

SELECT TOP(1) Path, score
  FROM @trustedPaths
 ORDER BY score ASC;
