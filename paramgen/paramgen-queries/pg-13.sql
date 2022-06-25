SELECT
    person1Id AS 'person1Id:ID',
    person2Id AS 'person2Id:ID'
FROM
    (SELECT
        personId AS person1Id,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 30
    ),
    (SELECT
        personId AS person2Id,
        abs(frequency - (SELECT percentile_disc(0.37) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 30
    )
WHERE person1Id != person2Id
ORDER BY md5(concat(person1Id, person2Id))
LIMIT 500
