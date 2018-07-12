/* Q8. Related Topics
\set tag '\'Enrique_Iglesias\''
 */
SELECT t2.t_name AS "relatedTag.name"
     , count(*) AS count
  FROM tag t INNER JOIN post_tag pt ON (t.t_tagid = pt.pst_tagid)
             -- as an optimization, we don't need message here as it's ID is in post_tag pt
             -- so proceed to the comment directly
             INNER JOIN post c      ON (pt.pst_postid = c.ps_replyof)
             -- comment's tag
             INNER JOIN post_tag ct ON (c.ps_postid = ct.pst_postid)
             INNER JOIN tag t2      ON (ct.pst_tagid = t2.t_tagid)
             -- comment doesn't have the given tag: antijoin in the where clause
             LEFT  JOIN post_tag nt ON (c.ps_postid = nt.pst_postid AND nt.pst_tagid = pt.pst_tagid)
 WHERE 1=1
    -- join
   AND nt.pst_postid IS NULL -- antijoin: comment (c) does not have the given tag
    -- filter
   AND t.t_name = :tag
 GROUP BY t2.t_name
 ORDER BY count DESC, t2.t_name
 LIMIT 100
;
