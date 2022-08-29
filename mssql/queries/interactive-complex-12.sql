WITH extended_tags(s_subtagclassid,s_supertagclassid) AS (
    SELECT id, id
      FROM tagclass
     UNION ALL
    SELECT tc.id, t.s_supertagclassid
      FROM tagclass tc, extended_tags t
     WHERE tc.SubclassOfTagClassId = t.s_subtagclassid
)
SELECT TOP (20)
        Person2.personId as friendPersonId,
        Person2.firstName as friendFirstName,
        Person2.lastName as friendLastName,
        dbo.fn_DistinctList(string_agg(CONVERT(NVARCHAR(max), st.name), ';') WITHIN GROUP ( ORDER BY st.name), ';'),
        count(DISTINCT Message1.MessageId) as replyCount
FROM 	Person Person1,
        Person_knows_Person,
        Person Person2,
        Message_hasCreator_Person,
        Message_replyOf_Message,
        Message_hasTag_Tag,
        Tag Tag1,
        Message Message1,
        Message Message2,
    	(SELECT DISTINCT id, name 
            FROM tag t 
            WHERE EXISTS (
                SELECT s_subtagclassid
                FROM extended_tags k, tagclass
                WHERE id = k.s_supertagclassid
                AND name = :tagClassName
                AND t.TypeTagClassId = s_subtagclassid
            )) st
WHERE MATCH (
        Person1-(Person_knows_Person)->Person2
        AND Message1-(Message_hasCreator_Person)->Person2
        AND Message1-(Message_replyOf_Message)->Message2
        AND Message2-(Message_hasTag_Tag)->Tag1
)
AND Person1.personId = :personId
AND Person2.personId = Message1.CreatorPersonId
AND Message2.ParentMessageId IS NULL
AND Tag1.id = st.id
GROUP BY Person2.personId, Person2.firstName, Person2.lastName
ORDER BY replyCount DESC, Person2.personId ASC;
