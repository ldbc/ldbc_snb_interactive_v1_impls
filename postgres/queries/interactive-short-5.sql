/* IS5. Creator of a message
\set messageId 824633720985
 */
select Person.id, Person.firstname, Person.lastname
from Message, Person
where Message.MessageId = :messageId and message.CreatorPersonId = Person.id;
;
