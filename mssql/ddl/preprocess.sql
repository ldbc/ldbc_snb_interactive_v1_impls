USE ldbc;
-- Precalculate interaction weights
UPDATE t
   SET weight = knows.score
  FROM dbo.Person_knows_Person AS t
 CROSS APPLY (
       SELECT dbo.CalculateInteractionScore(Person1id, Person2id) AS score
) AS knows;
