SELECT
    personId AS 'personId:ID',
    tagClassName AS 'tagClassName:STRING'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 50
    ),
    (SELECT
        tagClassName,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM tagClassNumTags)) AS diff
    FROM tagClassNumTags
    ORDER BY diff, tagClassName
    LIMIT 50
    )
ORDER BY md5(concat(personId, tagClassName))
LIMIT 500
