SELECT
    personId AS 'personId:ID',
    firstName AS 'firstName:STRING'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.55) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    ),
    (SELECT
        firstName,
        abs(frequency - (SELECT percentile_disc(0.65) WITHIN GROUP (ORDER BY frequency) FROM personFirstNames)) AS diff
    FROM personFirstNames
    ORDER BY diff, md5(firstName)
    )
ORDER BY md5(concat(personId, firstName))
LIMIT 500
