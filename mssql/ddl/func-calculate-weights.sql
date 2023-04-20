CREATE OR ALTER FUNCTION dbo.CalculateInteractionScore(@person1Id bigint, @person2Id bigint)
RETURNS int
AS
BEGIN
    DECLARE @score int;
    SELECT @score = sum(score)
    FROM (
        SELECT mid1, mid2, max(score) AS score
        FROM (
            SELECT 1 AS score, m1.MessageId AS mid1, m2.MessageId AS mid2
            FROM Message m1, Message m2
            WHERE (m1.CreatorPersonId = @Person1Id AND m2.CreatorPersonId = @Person2Id AND m2.ParentMessageId = m1.MessageId)
            UNION ALL
            SELECT 1 AS score, m1.MessageId AS mid1, m2.MessageId AS mid2
            FROM Message m1, Message m2
            WHERE (m1.CreatorPersonId = @Person2Id AND m2.CreatorPersonId = @Person1Id AND m2.ParentMessageId = m1.MessageId)
        ) pps
        GROUP BY mid1, mid2
    ) tmp
    RETURN CASE
        WHEN (ROUND(40 - SQRT(@score), 0)) > 1
        THEN @score
        ELSE 0
    END
END;
