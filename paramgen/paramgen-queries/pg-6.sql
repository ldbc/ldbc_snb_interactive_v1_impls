SELECT
    personId AS 'personId:ID',
    tagName AS 'tagName:STRING'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 50
    ),
    (SELECT
        tagName,
        abs(frequency - (SELECT percentile_disc(0.23) WITHIN GROUP (ORDER BY frequency) FROM tagNumPersons)) AS diff
    FROM tagNumPersons
    ORDER BY diff, md5(tagName)
    LIMIT 30
    )
ORDER BY md5(concat(personId, tagName))
LIMIT 500
