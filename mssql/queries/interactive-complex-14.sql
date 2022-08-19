/* 
 Trusted connection paths
 person1id 17592186049308,
 person2Id 30786325579204;
*/
declare @trustedPaths as table
(
    Id bigint,
    OrderDiscovered int,
    Path varchar(MAX),
    score int
)

INSERT INTO @trustedPaths
EXEC dbo.knows_Breadth_First :person1Id, :person2Id;

SELECT TOP(1) Path, score FROM @trustedPaths ORDER BY score ASC;
