SELECT
    personId AS 'personId',
    tagName AS 'tagName'
FROM
    (
        SELECT Person1Id AS personId,
               numFriendsOfFriends,
               abs(numFriendsOfFriends - (
                    SELECT percentile_disc(0.45)
                    WITHIN GROUP (ORDER BY numFriendsOfFriends)
                      FROM personNumFriendsOfFriends)
               ) AS diff
          FROM personNumFriendsOfFriends
         WHERE numFriends > 0 AND deletionDate > '2019'
         ORDER BY diff, md5(Person1Id)
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
