/* Q4. New topics
\set personId 17592186044461
\set startDate '\'2010-10-01\''::date
\set durationDays 31
 */
select name, count(*) as postCount
from tag, Message, Message_hasTag_Tag recent, Person_knows_Person
where
    Message.MessageId = recent.MessageId and
    recent.Tagid = Tag.id and
    Message.CreatorPersonId = Person2Id and
    ParentMessageId IS NULL and -- post, not comment
    person1id = :personId and
    Message.creationdate >= :startDate and
    Message.creationdate < (:startDate + INTERVAL '1 days' * :durationDays) and
    not exists (
        select * from
            (
                select distinct tagid from Message, Message_hasTag_Tag, Person_knows_Person
                where
                person1id = :personId and
                person2id = CreatorPersonId and
                ParentMessageId IS NULL and -- post, not comment
                Message_hasTag_Tag.MessageId = Message.MessageId and
                Message.creationdate < :startDate
            ) tags
        where tags.tagid = recent.tagid
    )
group by name
order by postCount desc, name asc
limit 10
;
