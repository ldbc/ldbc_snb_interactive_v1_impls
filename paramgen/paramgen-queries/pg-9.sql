SELECT
    personId AS 'personId',
    creationDay AS 'maxDate'
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
    WHERE numFriends > 0 AND deletionDate > '2019' AND creationDate < '2012-11-29'
         ORDER BY diff, md5(Person1Id)
         LIMIT 50
    ),
    (SELECT
        creationDay,
        abs(frequency - (SELECT percentile_disc(0.23) WITHIN GROUP (ORDER BY frequency) FROM creationDayNumMessages)) AS diff
    FROM creationDayNumMessages
    ORDER BY diff, md5(creationDay)
    LIMIT 20
    )
ORDER BY md5(concat(personId, creationDay))
LIMIT 500
