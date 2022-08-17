-- variant (b): anti-correlated countries
SELECT
    personId AS 'personId',
    countryXName AS 'countryXName',
    countryYName AS 'countryYName',
    creationDay AS 'startDate',
    2 + salt * 37 % 5 AS 'durationDays'
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.55) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(id)
    LIMIT 20
    ),
    (SELECT
        country1Name AS countryXName,
        country2Name AS countryYName,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.03) WITHIN GROUP (ORDER BY frequency) FROM countryPairsNumFriends)) AS diff
    FROM countryPairsNumFriends
    ORDER BY diff, country1Name, country2Name
    LIMIT 50
    ),
    (SELECT
        creationDay,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.15) WITHIN GROUP (ORDER BY frequency) FROM creationDayNumMessages)) AS diff
    FROM creationDayNumMessages
    ORDER BY diff, creationDay
    LIMIT 15
    ),
    (SELECT unnest(generate_series(1, 20)) AS salt)
WHERE countryXName != countryYName
ORDER BY md5(concat(personId, countryXName, countryYName, creationDay, salt))
LIMIT 500
