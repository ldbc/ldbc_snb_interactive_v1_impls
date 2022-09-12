SELECT
    personId AS 'personId',
    creationDay AS 'maxDate',
    useFrom AS 'useFrom',
    useUntil AS 'useUntil'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.55) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff,
        creationDate AS useFrom,
        deletionDate AS useUntil
    FROM personNumFriends
    WHERE frequency > 0 AND deletionDate - INTERVAL 1 DAY  > :date_limit_filter AND creationDate + INTERVAL 1 DAY < :date_limit_filter
    ORDER BY diff, md5(id)
    LIMIT 50
    ),
    (SELECT
        creationDay,
        abs(frequency - (SELECT percentile_disc(0.65) WITHIN GROUP (ORDER BY frequency) FROM creationDayNumMessages)) AS diff
    FROM creationDayNumMessages
    ORDER BY diff, md5(creationDay)
    LIMIT 20
    )
ORDER BY md5(concat(personId, creationDay))
LIMIT 500
