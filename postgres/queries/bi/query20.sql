/* Q20. High-level topics
\set tagClasses '\'{"Album","Person","OfficeHolder","MusicalArtist","Single","Country"}\''::varchar[]
 */
WITH RECURSIVE tagclass_tree(root_tagclassid, root_tagclassname, tagclassid) AS (
    SELECT tc.tc_tagclassid AS root_tagclassid
         , tc.tc_name AS root_tagclassname
         , tc.tc_tagclassid AS tagclassid
      FROM tagclass tc
     WHERE 1=1
       AND tc.tc_name = ANY (:tagClasses)
  UNION ALL
    SELECT tt.root_tagclassid
         , tt.root_tagclassname
         , tc.tc_tagclassid AS tagclassid
      FROM tagclass tc
         , tagclass_tree tt
     WHERE 1=1
        -- join
       AND tt.tagclassid = tc.tc_subclassoftagclassid
)
SELECT tt.root_tagclassname AS "tagClass.name"
     , count(mt.pst_postid) AS messageCount
  FROM tagclass_tree tt INNER JOIN tag_tagclass ttc ON (tt.tagclassid = ttc.ttc_tagclassid)
                        LEFT  JOIN post_tag     mt  ON (ttc.ttc_tagid = mt.pst_tagid)
 WHERE 1=1
 GROUP BY tt.root_tagclassname
 ORDER BY messageCount DESC, tt.root_tagclassname
 LIMIT 100
;
