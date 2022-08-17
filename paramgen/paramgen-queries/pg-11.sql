SELECT
    personId AS 'personId',
    countryName AS 'countryName',
    1998 + salt * 37 % 15 AS 'workFromYear' -- 1998..2013
FROM
    (SELECT
        id AS personId,
        abs(frequency - (SELECT percentile_disc(0.45) WITHIN GROUP (ORDER BY frequency) FROM personNumFriends)) AS diff
    FROM personNumFriends
    ORDER BY diff, md5(id)
    LIMIT 50
    ),
    (SELECT
        name AS countryName,
        frequency AS freq,
        abs(frequency - (SELECT percentile_disc(0.43) WITHIN GROUP (ORDER BY frequency) FROM countryNumPersons)) AS diff
    FROM countryNumPersons
    ORDER BY diff, countryName
    LIMIT 20
    ),
    (SELECT unnest(generate_series(1, 20)) AS salt)
ORDER BY md5(concat(personId, countryName, salt))
LIMIT 500
