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
