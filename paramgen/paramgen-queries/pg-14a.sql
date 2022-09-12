-- variant (a): guaranteed that no path exists
SELECT
    component1.PersonId AS 'person1Id',
    component2.PersonId AS 'person2Id',
    useFrom AS 'useFrom',
    useUntil AS 'useUntil'
FROM
    (
        SELECT PersonId, Component
        FROM personKnowsPersonConnected
        ORDER BY md5(PersonId + 3)
        LIMIT 100
    ) component1,
    (
        SELECT PersonId, Component
        FROM personKnowsPersonConnected
        ORDER BY md5(PersonId + 4)
        LIMIT 100
    ) component2,
    (
        SELECT :date_limit_long AS useUntil
    ),
    (
        SELECT :date_start_long as useFrom
    )
WHERE component1.Component != component2.Component
ORDER BY md5(concat(component1.PersonId, component2.PersonId))
LIMIT 500
