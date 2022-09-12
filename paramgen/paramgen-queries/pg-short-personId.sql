SELECT Person1Id AS personId,
    FROM personNumFriendsOfFriends
    WHERE numFriends > 0 AND deletionDate > '2019' AND creationDate < '2012-09-01'
    ORDER BY md5(Person1Id)
    LIMIT 50
