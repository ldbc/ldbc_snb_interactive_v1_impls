-- variant (b): guaranteed that there is a path
SELECT
    person1Id AS 'person1Id',
    person2Id AS 'person2Id',
    GREATEST(Person1CreationDate, Person2CreationDate) AS 'useFrom',
       LEAST(Person1DeletionDate, Person2DeletionDate) AS 'useUntil'
FROM people4Hops
WHERE Person1CreationDate + INTERVAL 1 DAY < :date_limit_filter
  AND Person2CreationDate + INTERVAL 1 DAY < :date_limit_filter
  AND Person1DeletionDate - INTERVAL 1 DAY > :date_limit_filter
  AND Person2DeletionDate - INTERVAL 1 DAY > :date_limit_filter
ORDER BY md5(concat(person1Id + 1, person2Id + 2))
LIMIT 500
