SELECT
    personId AS 'personId:ID',
    creationDay AS 'minDate:DATE'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    ),
    (SELECT
        creationDay,
        abs(frequency - (SELECT percentile_disc(0.23) WITHIN GROUP (ORDER BY frequency) FROM creationDayNumMessages)) AS diff
    FROM creationDayNumMessages
    ORDER BY diff, md5(creationDay)
    )
ORDER BY md5(concat(personId, creationDay))
LIMIT 500
