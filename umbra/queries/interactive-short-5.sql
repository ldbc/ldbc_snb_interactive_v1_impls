/* IS5. Creator of a message
\set messageId 824633720985
 */
SELECT Person.id, Person.firstName, Person.lastName
FROM Message, Person
WHERE Message.id = :messageId
  AND Message.CreatorPersonId = Person.id
;
