/* Q9. Forum with related Tags
\set tagClass1 '\'BaseballPlayer\''
\set tagClass2 '\'ChristianBishop\''
\set threshold 200
 */
SELECT f.f_forumid AS forumId
     , count(DISTINCT p1.m_messageid) AS count1
     , count(DISTINCT p2.m_messageid) AS count2
  FROM tagclass tc1
     , tag t1
     , message_tag pt1
     , message p1
     , tagclass tc2
     , tag t2
     , message_tag pt2
     , message p2
     , forum f
     , (
      SELECT fp1.forumid
        FROM (
            SELECT fp0.fp_forumid AS forumid
                , count(*) AS forumPersonCnt
            FROM forum_person fp0
            GROUP BY fp0.fp_forumid
        ) fp1
       WHERE fp1.forumPersonCnt > 200 -- FIXME:param :threshold
       ) pf -- popular_forums
 WHERE 1=1
    -- join
    -- tagClass1 to forum
   AND tc1.tc_tagclassid = t1.t_tagclassid
   AND t1.t_tagid = pt1.mt_tagid
   AND pt1.mt_messageid = p1.m_messageid
   AND p1.m_ps_forumid = f.f_forumid
   AND f.f_forumid = pf.forumid
    -- tagClass2 to forum
   AND tc2.tc_tagclassid = t2.t_tagclassid
   AND t2.t_tagid = pt2.mt_tagid
   AND pt2.mt_messageid = p2.m_messageid
   AND p2.m_ps_forumid = f.f_forumid
    -- filter
   AND tc1.tc_name = 'BaseballPlayer' -- FIXME:param :tagClass1
   AND tc2.tc_name = 'ChristianBishop' -- FIXME:param :tagClass2
   AND p1.m_c_replyof = -1
   AND p2.m_c_replyof = -1
 GROUP BY f.f_forumid
 --ORDER BY abs(count(DISTINCT p2.m_messageid) - count(DISTINCT p1.m_messageid) ) DESC, f.f_forumid
 --LIMIT 100
;
