/* Q2. Recent messages by your friends
\set personId 17592186044461
\set maxDate '\'2010-10-16\''
 */
select person.id, person.firstname, person.lastname, messageid, COALESCE(message.imagefile, message.content), message.creationdate
from person, message, Person_knows_Person
where
    person.id = Message.CreatorPersonId and
    Message.creationDate <= :maxDate and
    person1id = :personId and
    person2id = person.id
order by creationdate desc, messageid asc
limit 20
;
