SELECT 1;
-- Work in progress
-- use ldbc;
-- go
-- DROP PROCEDURE dbo.knows_Dijkstra;
-- DROP FUNCTION dbo.CalculateInteractionScore
-- go

-- CREATE FUNCTION dbo.CalculateInteractionScore(@person1Id bigint, @person2Id bigint)
-- RETURNS int
-- AS
-- BEGIN
--     DECLARE @score int;
--     SELECT @score = sum(score)
--     FROM (
--         SELECT mid1, mid2, max(score) AS score
--         FROM (
--             SELECT 1 AS score, m1.MessageId AS mid1, m2.MessageId AS mid2
--             FROM Message m1, Message m2
--             WHERE (m1.CreatorPersonId = @Person1Id AND m2.CreatorPersonId = @Person2Id AND m2.ParentMessageId = m1.MessageId)
--             UNION ALL
--             SELECT 1 AS score, m1.MessageId AS mid1, m2.MessageId AS mid2
--             FROM Message m1, Message m2
--             WHERE (m1.CreatorPersonId = @Person2Id AND m2.CreatorPersonId = @Person1Id AND m2.ParentMessageId = m1.MessageId)
--         ) pps
--         GROUP BY mid1, mid2
--     ) tmp
--     RETURN CASE
--         WHEN (FLOOR(40 - SQRT(@score))) > 1 
--         THEN FLOOR(40 - SQRT(@score))
--         ELSE 1
--     END
-- END;
-- go
-- CREATE PROCEDURE dbo.knows_Dijkstra (@StartNode bigint, @EndNode bigint = NULL)
-- AS
-- BEGIN
--     -- Automatically rollback the transaction if something goes wrong.    
--     SET XACT_ABORT ON    
--     BEGIN TRAN
    
-- 	-- Increase performance and do not intefere with the results.
--     SET NOCOUNT ON;

--     -- Create a temporary table for storing the Scores as the algorithm runs
-- 	CREATE TABLE #Nodes
-- 	(
-- 		Id bigint NOT NULL PRIMARY KEY,    -- The Node Id
-- 		Score int NOT NULL,    -- What is the distance to this node, so far?
-- 		Predecessor int NULL,    -- The node we came from to get to this node with this distance.
-- 		Done bit NOT NULL        -- Are we done with this node yet (is the Score the final distance)?
-- 	)

--     -- Fill the temporary table with initial data
--     INSERT INTO #Nodes (Id, Score, Predecessor, Done)
--     SELECT personId, 9999999, NULL, 0 FROM dbo.Person
    
--     -- Set the Score for the node we start in to be 0.
--     UPDATE #Nodes SET Score = 0 WHERE Id = @StartNode
--     IF @@rowcount <> 1
--     BEGIN
--         DROP TABLE #Nodes
--         RAISERROR ('Could not set start node', 11, 1) 
--         ROLLBACK TRAN        
--         RETURN 1
--     END

--     DECLARE @FromNode int, @CurrentScore int

--     -- Run the algorithm until we decide that we are finished
--     WHILE 1 = 1
--     BEGIN
--         -- Reset the variable, so we can detect getting no records in the next step.
--         SELECT @FromNode = NULL

--         -- Select the Id and current Score for a node not done, with the lowest Score.
--         SELECT TOP 1 @FromNode = Id, @CurrentScore = Score
--         FROM #Nodes WHERE Done = 0 AND Score < 9999999
--         ORDER BY Score
        
--         -- Stop if we have no more unvisited, reachable nodes.
--         IF @FromNode IS NULL OR @FromNode = @EndNode BREAK

--         -- We are now done with this node.
--         UPDATE #Nodes SET Done = 1 WHERE Id = @FromNode

--         -- Update the Scores to all neighbour node of this one (all the nodes
--         -- there are edges to from this node). Only update the Score if the new
--         -- proposal (to go via the current node) is better (lower).
--         UPDATE #Nodes
-- 		SET Score = dbo.CalculateInteractionScore(Predecessor, @FromNode), Predecessor = @FromNode
--         FROM #Nodes n INNER JOIN dbo.Person_knows_Person e ON n.Id = e.Person2Id
--         WHERE Done = 0 AND e.Person1Id = @FromNode AND (@CurrentScore + Score) < n.Score
--     END;

--     SELECT * FROM #Nodes;-- WHERE Id = @EndNode ORDER BY Score ASC;

--     DROP TABLE #Nodes
--     COMMIT TRAN
--     RETURN 0
-- END
-- go


-- DECLARE @Person1Id AS bigint = 1251;--:person1Id 16;
-- DECLARE @Person2Id AS bigint = 8796093022572;--:person2Id;

-- EXEC dbo.knows_Dijkstra @Person1Id, @Person2Id ;
-- GO
