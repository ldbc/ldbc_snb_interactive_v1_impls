-- Add weight column to Person_knows_Person
USE ldbc;
ALTER TABLE [dbo].[Person_knows_Person]
ADD weight bigint DEFAULT 0;

-- Precalculate interaction weights
UPDATE t
SET weight     = knows.score
FROM dbo.Person_knows_Person AS t
CROSS APPLY (
    SELECT dbo.CalculateInteractionScore(Person1id, Person2id) AS score
) AS knows;
