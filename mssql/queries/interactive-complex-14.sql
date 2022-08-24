/* 
 Trusted connection paths
 person1id 17592186049308,
 person2Id 30786325579204;
*/
DECLARE @trustedPaths as table
(
    Id bigint,
    OrderDiscovered int,
    Path varchar(MAX)
)

DECLARE @pathWithScore as table
(
    Path varchar(MAX),
    score bigint
)

DECLARE @interactions as table
(
    person1Id bigint,
    person2Id bigint,
    score bigint
)

INSERT INTO @trustedPaths
EXEC dbo.knows_Breadth_First :person1Id, :person2Id;

DECLARE @Path VARCHAR(MAX);
DECLARE @Score bigint;
DECLARE @person1Id bigint;
DECLARE @person2Id bigint;

WHILE (Select Count(*) FROM @trustedPaths) > 0
BEGIN

    SELECT TOP 1 @Path = Path FROM @trustedPaths
    DROP TABLE IF EXISTS #Temp

    SELECT items AS id, rownum
    INTO #Temp
    FROM dbo.Split(@Path, ';')

    SELECT TOP 1 @person1Id = id FROM #Temp
    DELETE #Temp WHERE id = @person1Id
    WHILE (SELECT Count(*) FROM #Temp) > 0
    BEGIN
        SELECT TOP 1 @person2Id = id FROM #Temp

        SET @Score = dbo.CalculateInteractionScore(@person1Id, @person2Id)

        IF @Score = 1
            BEGIN
                DELETE FROM @interactions
                DROP TABLE IF EXISTS #Temp
                BREAK
            END
        ELSE
            BEGIN
                INSERT INTO @interactions VALUES(@person1Id, @person2Id, dbo.CalculateInteractionScore(@person1Id, @person2Id))
            END
        SET @person1Id = @person2Id
        DELETE #Temp WHERE id = @person2Id
        SELECT Top 1 @person2Id = id FROM #Temp
    END
    DELETE @trustedPaths WHERE Path = @Path
        IF @Score > 1
            BEGIN
                INSERT INTO @pathWithScore
                SELECT @Path, SUM(score) FROM @interactions;
            END
    DELETE FROM @interactions;
END

SELECT TOP(1) * FROM @pathWithScore ORDER BY score;
