/* Q19. Stranger’s interaction
\set date '\'1989-01-01T00:00:00.000+00:00\''::timestamp
\set tagClass1 '\'MusicalArtist\''
\set tagClass2 '\'OfficeHolder\''
 */
WITH RECURSIVE -- note: RECURSIVE denotes that some CTE (subquery) will be recursive below.
     strangers AS (
    SELECT DISTINCT fp1.fp_personid AS personid
      FROM tagclass tc1
         , tagclass tc2
         , tag t1
         , tag t2
         , forum_tag ft1
         , forum_tag ft2
         , forum_person fp1
         , forum_person fp2
     WHERE 1=1
        -- join
       AND tc1.tc_tagclassid = t1.t_tagclassid
       AND tc2.tc_tagclassid = t2.t_tagclassid
       AND ft1.ft_tagid = t1.t_tagid
       AND ft2.ft_tagid = t2.t_tagid
       AND ft1.ft_forumid = fp1.fp_forumid
       AND ft2.ft_forumid = fp2.fp_forumid
       AND fp1.fp_personid = fp2.fp_personid
        -- filter
       AND tc1.tc_name = :tagClass1
       AND tc2.tc_name = :tagClass2
)
   , interactions(strangerid, messageid, replyid, replyAuthorId) AS (
     -- this is the actual recursive CTE
    SELECT s.personid
         , m.m_messageid
         , r.m_messageid
         , r.m_creatorid
      FROM strangers s
         , message m
         , message r
     WHERE 1=1
        -- join
       AND s.personid = m.m_creatorid
       AND m.m_messageid = r.m_c_replyof
  UNION ALL
    SELECT i.strangerid
         , i.messageid
         , r.m_messageid
         , r.m_creatorid
      FROM interactions i
         , message r
     WHERE 1=1
        -- join
       AND i.replyid = r.m_c_replyof
)
   , interactions_longest AS (
     -- interaction i2 does not extend towards the original post by an interaction when the stranger transitively replied to himself
    SELECT i2.*
      FROM interactions i1
           RIGHT JOIN interactions i2
                   ON (1=1
                   AND i1.strangerid = i1.replyAuthorId -- i1 is an interaction on the stranger with himself
                   AND i1.replyid = i2.messageid -- this implies that i1.replyAuthorId = i2.strangerid
                      )
     WHERE 1=1
       AND i1.strangerid IS NULL
)
   , interactions_between_non_friends AS (
    SELECT i.*
      FROM interactions_longest i
           LEFT JOIN knows k ON (i.strangerid = k.k_person1id AND i.replyAuthorId = k.k_person2id)
     WHERE 1=1
       AND k.k_person1id IS NULL
)
SELECT p.p_personid AS "person.id"
     , count(DISTINCT i.strangerid) AS strangerCount
     , count(i.replyid) AS interactionCount
  FROM person p
       LEFT JOIN interactions_between_non_friends i ON (p.p_personid = i.replyAuthorId AND p.p_personid != i.strangerid)
 WHERE 1=1
    -- filter
   AND p.p_birthday > :date
 GROUP BY p.p_personid
 ORDER BY interactionCount DESC, p.p_personid
 LIMIT 100
;
