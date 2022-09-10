SELECT
    personId AS 'personId'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    WHERE numFriends > 0 AND deletionDate > '2019' AND creationDate < '2012-11-29'
    ORDER BY diff, md5(id)
    LIMIT 500
    )
ORDER BY md5(personId)
LIMIT 500
