WITH extended_tags(s_subtagclassid,s_supertagclassid) AS (
    SELECT id
         , id
      FROM tagclass
     UNION ALL
    SELECT tc.id
         , t.s_supertagclassid
      FROM tagclass tc
         , extended_tags t
     WHERE tc.SubclassOfTagClassId = t.s_subtagclassid
)
SELECT TOP(20) personId
             , firstName
             , lastName
             , string_agg(name, ';')
             , sum(partialReplyCount)
            AS replyCount
          FROM ( SELECT DISTINCT Person.personId, firstName, lastName, name, count(*) AS partialReplyCount
                   FROM Person
                      , Message m1
                      , Person_knows_Person k
                      , Message m2
                      , Message_hasTag_Tag
                      , (SELECT DISTINCT id
                              , name 
                           FROM tag
                          WHERE (TypeTagClassId IN (
                                 SELECT DISTINCT s_subtagclassid
                                   FROM extended_tags k
                                      , tagclass
                                  WHERE id = k.s_supertagclassid
                                    AND name = :tagClassName
                                ))) selected_tags
                        WHERE Person1Id = :personId
                        AND k.Person2Id = Person.personId
                        AND Person.personId = m1.CreatorPersonId
                        AND m1.ParentMessageId = m2.MessageId
                        AND m2.ParentMessageId IS NULL
                        AND m2.MessageId = Message_hasTag_Tag.MessageId
                        AND Message_hasTag_Tag.TagId = selected_tags.id
      GROUP BY Person.personId, Person.firstName, person.lastName, name
      ) x
      GROUP BY personId, firstName, lastName
      ORDER BY replyCount DESC, personId ASC;