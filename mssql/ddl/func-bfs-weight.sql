/* http://hansolav.net/sql/graphs.html#bfs
 * Runs breadth-first search from a specific node.
 * @StartNode: If of node to start the search at.
 * @EndNode: Stop the search when node with this id is found. Specify NULL
 * 			 to traverse the whole graph.
*/

CREATE OR ALTER PROCEDURE dbo.knows_Breadth_First_Weight (@StartNode bigint, @EndNode bigint = NULL)
AS
BEGIN
    SET XACT_ABORT ON    
    BEGIN TRAN
    
    SET NOCOUNT ON;

	CREATE TABLE #Discovered
	(
		Id bigint NOT NULL,
		Predecessor bigint NULL,
		OrderDiscovered bigint,
        weight bigint
	)

    INSERT INTO #Discovered (Id, Predecessor, OrderDiscovered, weight)
    VALUES (@StartNode, NULL, 0, 0)

	WHILE @@ROWCOUNT > 0
    BEGIN
		IF @EndNode IS NOT NULL
			IF EXISTS (SELECT TOP 1 1 FROM #Discovered WHERE Id = @EndNode)
				BREAK    
    
		-- We need to get all shortest path and then choose the one with the lowest weight
		INSERT INTO #Discovered (Id, Predecessor, OrderDiscovered, weight)
		SELECT e.person2id, MIN(e.person1id), MIN(d.OrderDiscovered) + 1, e.weight
		FROM #Discovered d JOIN dbo.Person_knows_Person e ON d.Id = e.person1id
		WHERE e.person2id NOT IN (SELECT Id From #Discovered) AND e.weight > 0
		GROUP BY e.person1id, e.person2id, e.weight
    END;

	WITH BacktraceCTE(Id, OrderDiscovered, Path, score)
	AS
	(
		SELECT n.personId, d.OrderDiscovered, CAST(n.personId AS varchar(MAX)), d.weight
		FROM #Discovered d JOIN dbo.Person n ON d.Id = n.personId
		WHERE d.Id = @StartNode
		
		UNION ALL

		SELECT n.personId, d.OrderDiscovered,
			CAST(cte.Path + ';' + CAST(n.personId as varchar(MAX)) as varchar(MAX)), cte.score + CAST(ROUND(40 - SQRT(d.weight),0) AS bigint)
		FROM #Discovered d JOIN BacktraceCTE cte ON d.Predecessor = cte.Id
		JOIN dbo.Person n ON d.Id = n.personId
	)
	
	SELECT Id, OrderDiscovered, Path, score FROM BacktraceCTE
	WHERE Id = @EndNode OR @EndNode IS NULL
	ORDER BY score
    
    DROP TABLE #Discovered
    COMMIT TRAN
    RETURN 0
END;
