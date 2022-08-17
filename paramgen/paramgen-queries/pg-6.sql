SELECT
    personId AS 'personId:ID',
    tagName AS 'tagName:STRING'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(id)
    LIMIT 50
    ),
    (SELECT
        name AS tagName,
        abs(frequency - (SELECT percentile_disc(0.23) WITHIN GROUP (ORDER BY frequency) FROM tagNumPersons)) AS diff
    FROM tagNumPersons
    ORDER BY diff, md5(name)
    LIMIT 30
    )
ORDER BY md5(concat(personId, tagName))
LIMIT 500
