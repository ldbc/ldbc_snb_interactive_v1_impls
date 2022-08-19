/* 
 Trusted connection paths
 person1id 17592186049308,
 person2Id 30786325579204;
*/
declare @trustedPaths as table
(
    Id bigint,
    OrderDiscovered int,
    Path varchar(MAX)
)

declare @pathWithScore as table
(
    Path varchar(MAX),
    score bigint
)

declare @interactions as table
(
    person1Id bigint,
    person2Id bigint,
    score bigint
)

INSERT INTO @trustedPaths
EXEC dbo.knows_Breadth_First :person1Id, :person2Id;

Declare @Path VARCHAR(MAX);
Declare @Score bigint;
Declare @person1Id bigint;
Declare @person2Id bigint;

While (Select Count(*) From @trustedPaths) > 0
Begin

    Select Top 1 @Path = Path From @trustedPaths
    DROP TABLE IF EXISTS #Temp

    select items as id, rownum
    INTO #Temp
    FROM dbo.Split(@Path, ';')

    Select Top 1 @person1Id = id From #Temp
    DELETE #Temp Where id = @person1Id
    While (Select Count(*) From #Temp) > 0
    BEGIN
        Select Top 1 @person2Id = id From #Temp

        INSERT INTO @interactions VALUES(@person1Id, @person2Id, dbo.CalculateInteractionScore(@person1Id, @person2Id))

        SET @person1Id = @person2Id
        DELETE #Temp Where id = @person2Id
        Select Top 1 @person2Id = id From #Temp
    END
    Delete @trustedPaths Where Path = @Path

    INSERT INTO @pathWithScore
    SELECT @Path, SUM(score) FROM @interactions;

    DELETE FROM @interactions;
End

SELECT TOP(1) * FROM @pathWithScore ORDER BY score;
