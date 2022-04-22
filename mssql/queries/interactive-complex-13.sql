
SELECT PersonId, Friends, levels
FROM (
	SELECT
		Person1.p_personid AS PersonId,
		STRING_AGG(Person2.p_personid, '->') WITHIN GROUP (GRAPH PATH) AS Friends,
		LAST_VALUE(Person2.p_personid) WITHIN GROUP (GRAPH PATH) AS LastNode,
		COUNT(Person2.p_personid) WITHIN GROUP (GRAPH PATH) AS levels
	FROM
		person AS Person1,
		knows FOR PATH AS fo,
		person FOR PATH  AS Person2
	WHERE MATCH(SHORTEST_PATH(Person1(-(fo)->Person2)+))
	AND Person1.p_personid = :person1Id
) AS Q
WHERE Q.LastNode = :person2Id