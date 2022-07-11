SELECT
    personId AS 'personId:ID'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 500
    )
ORDER BY md5(personId)
LIMIT 500
