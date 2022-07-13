/* IS5. Creator of a message
\set messageId 824633720985
 */
SELECT Person.personId, Person.firstName, Person.lastName
FROM Message, Person
WHERE Message.MessageId = :messageId
  AND Message.CreatorPersonId = Person.personId
;