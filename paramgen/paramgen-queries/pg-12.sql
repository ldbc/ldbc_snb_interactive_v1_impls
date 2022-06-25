SELECT
    personId AS 'personId:ID',
    countryName AS 'countryName:STRING',
    1995 + salt * 37 % 10 AS 'workFromYear:INT'
FROM
    (SELECT
        personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(personId)
    LIMIT 50
    ),
    (SELECT
        countryName,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM countryNumPersons)) AS diff
    FROM countryNumPersons
    ORDER BY diff, countryName
    LIMIT 20
    ),
    (SELECT unnest(generate_series(1, 20)) AS salt)
ORDER BY md5(concat(personId, countryName, salt))
LIMIT 500
