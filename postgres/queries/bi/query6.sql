/* Q6. Most active Posters of a given Topic
\set tag '\'Abbas_I_of_Persia\''
 */
WITH detail AS (
SELECT cr.p_personid AS person_id
     , count(DISTINCT r.ps_postid)  AS replyCount
     , count(DISTINCT l.l_postid||' '||l.l_personid) AS likeCount
     , count(DISTINCT m.ps_postid)  AS messageCount
     , null as score
  FROM tag t
     , post_tag pt
     , post m LEFT JOIN post  r ON (m.ps_postid = r.ps_replyof) -- m: all messages, not just posts; r: direct reply to m
              LEFT JOIN likes l ON (m.ps_postid = l.l_postid)  -- l: likes to m
     , person cr -- creator
 WHERE 1=1
    -- join
   AND t.t_tagid = pt.pst_tagid
   AND pt.pst_postid = m.ps_postid
   AND m.ps_creatorid = cr.p_personid
    -- filter
   AND t.t_name = :tag
 GROUP BY cr.p_personid
)
SELECT person_id AS "person.id"
     , messageCount
     , replyCount
     , likeCount
     , messageCount + 2*replyCount + 10*likeCount AS score
  FROM detail
 ORDER BY score DESC, person_id
 LIMIT 100
;
WITH detail AS (
SELECT cr.p_personid AS person_id
     , count(DISTINCT r.ps_postid)  AS replyCount
     , count(DISTINCT l.l_postid||' '||l.l_personid) AS likeCount
     , count(DISTINCT m.ps_postid)  AS messageCount
     , null as score
  FROM tag t
     , post_tag pt
     , post m LEFT JOIN post  r ON (m.ps_postid = r.ps_replyof) -- m: all messages, not just posts; r: direct reply to m
              LEFT JOIN likes l ON (m.ps_postid = l.l_postid)  -- l: likes to m
     , person cr -- creator
 WHERE 1=1
    -- join
   AND t.t_tagid = pt.pst_tagid
   AND pt.pst_postid = m.ps_postid
   AND m.ps_creatorid = cr.p_personid
    -- filter
   AND t.t_name = :tag
 GROUP BY cr.p_personid
)
SELECT person_id AS "person.id"
     , messageCount
     , replyCount
     , likeCount
     , messageCount + 2*replyCount + 10*likeCount AS score
  FROM detail
 ORDER BY score DESC, person_id
 LIMIT 100
;
