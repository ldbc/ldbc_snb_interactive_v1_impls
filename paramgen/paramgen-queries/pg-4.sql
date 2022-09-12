SELECT
    personId AS 'personId',
    creationDay AS 'startDate',
    2 + salt * 37 % 5 AS 'durationDays',
       useFrom AS 'useFrom',
       useUntil AS 'useUntil'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.60) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff,
               creationDate AS useFrom,
               deletionDate AS useUntil
    FROM personNumFriends
    WHERE frequency > 0 AND deletionDate - INTERVAL 1 DAY  > :date_limit_filter AND creationDate + INTERVAL 1 DAY < :date_limit_filter
    ORDER BY diff, md5(id)
    LIMIT 10
    ),
    (SELECT
        creationDay,
        abs(frequency - (SELECT percentile_disc(0.65) WITHIN GROUP (ORDER BY frequency) FROM creationDayNumMessages)) AS diff
    FROM creationDayNumMessages
    ORDER BY diff, md5(creationDay)
    LIMIT 10
    ),
    (SELECT unnest(generate_series(1, 20)) AS salt)
ORDER BY md5(concat(personId, creationDay, salt))
LIMIT 500
