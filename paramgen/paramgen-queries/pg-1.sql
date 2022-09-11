-- WIP: This now tries to minimize the difference between frequency of friends and friend of friends. TODO: third hop
-- WITH selected_ids AS (
--     SELECT id AS personId,
--            frequency,
--            abs(frequency - (SELECT percentile_disc(0.65) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
--       FROM personNumFriends
--      ORDER BY diff, md5(id)
--      LIMIT 1000
-- )
-- , second_hop_selected AS (
--     SELECT personId, sum(personNumFriends.frequency) AS second_hop_count
--       FROM personNumFriends, 
--            Person_knows_Person k1,
--            selected_ids
--      WHERE k1.Person1Id = selected_ids.personId
--        AND personNumFriends.id = k1.person2Id
--      GROUP BY personId
-- )
-- , selected_ids_2 AS (
--     SELECT personId,
--            second_hop_count as frequency,
--            abs(second_hop_count - (SELECT percentile_disc(0.55) WITHIN GROUP (ORDER BY second_hop_count) FROM second_hop_selected)) AS diff2
--       FROM second_hop_selected
--      ORDER BY diff2, md5(personId)
--      LIMIT 100
-- )

SELECT personId AS 'personId',
       firstName AS 'firstName'
FROM
    (
        SELECT Person1Id AS personId,
               numFriendsOfFriends,
               abs(numFriendsOfFriends - (
                    SELECT percentile_disc(0.65)
                    WITHIN GROUP (ORDER BY numFriendsOfFriends)
                      FROM personNumFriendsOfFriends)
               ) AS diff
          FROM personNumFriendsOfFriends
         WHERE numFriends > 0 AND deletionDate > '2019' AND creationDate < '2012-11-29'
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
ORDER BY md5(concat(personId, firstName))
LIMIT 500
