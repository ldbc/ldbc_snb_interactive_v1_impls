-- variant (b): guaranteed that there is a path
SELECT
    person1Id AS 'person1Id',
    person2Id AS 'person2Id'
FROM people4Hops
ORDER BY md5(concat(person1Id + 3, person2Id + 4))
LIMIT 500
