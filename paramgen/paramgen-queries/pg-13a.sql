-- variant (a): guaranteed that no path exists
SELECT
    person1Id AS 'person1Id',
    person2Id AS 'person2Id'
FROM
    (SELECT
        id AS person1Id,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(id)
    LIMIT 30
    ),
    (SELECT
        id AS person2Id,
        abs(frequency - (SELECT percentile_disc(0.37) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(id)
    LIMIT 30
    )
WHERE person1Id != person2Id
ORDER BY md5(concat(person1Id, person2Id))
LIMIT 500
