SELECT
    personId AS 'personId',
       useFrom AS 'useFrom',
       useUntil AS 'useUntil'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff,
               creationDate AS useFrom,
               deletionDate AS useUntil
    FROM personNumFriends
    WHERE frequency > 0 AND deletionDate - INTERVAL 1 DAY  > :date_limit_filter AND creationDate + INTERVAL 1 DAY < :date_limit_filter
    ORDER BY diff, md5(id)
    LIMIT 500
    )
ORDER BY md5(personId)
LIMIT 500
