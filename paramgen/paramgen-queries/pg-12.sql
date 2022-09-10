SELECT
    personId AS 'personId',
    tagClassName AS 'tagClassName'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    WHERE numFriends > 0 AND deletionDate > '2019' AND creationDate < '2012-11-29'
    ORDER BY diff, md5(id)
    LIMIT 50
    ),
    (SELECT
        name AS tagClassName,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM tagClassNumTags)) AS diff
    FROM tagClassNumTags
    ORDER BY diff, tagClassName
    LIMIT 50
    )
ORDER BY md5(concat(personId, tagClassName))
LIMIT 500
