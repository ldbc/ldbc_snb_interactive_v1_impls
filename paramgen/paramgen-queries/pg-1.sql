SELECT personId AS 'personId',
       firstName AS 'firstName',
       useFrom AS 'useFrom',
       useUntil AS 'useUntil'
FROM
    (
        SELECT Person1Id AS personId,
               numFriendsOfFriends,
               abs(numFriendsOfFriends - (
                    SELECT percentile_disc(0.65)
                    WITHIN GROUP (ORDER BY numFriendsOfFriends)
                      FROM personNumFriendsOfFriends)
               ) AS diff,
               creationDate AS useFrom,
               deletionDate AS useUntil
          FROM personNumFriendsOfFriends
         WHERE numFriends > 0 AND deletionDate - INTERVAL 1 DAY  > :date_limit_filter AND creationDate + INTERVAL 1 DAY < :date_limit_filter
         ORDER BY diff, md5(Person1Id)
         LIMIT 50
    ),
    (
        SELECT firstName,
               abs(frequency - (SELECT percentile_disc(0.65) WITHIN GROUP (ORDER BY frequency) FROM personFirstNames)) AS diff
          FROM personFirstNames
         ORDER BY diff, md5(firstName)
         LIMIT 20
    )
ORDER BY md5(concat(personId, firstName, useUntil, useFrom))
LIMIT 500
