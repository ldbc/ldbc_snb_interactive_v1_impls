/* Q1. Posting summary
\set date '\'2011-07-21T22:00:00.000+00:00\''::timestamp
 */
SELECT messageYear, message_order, lengthCategory
     , count(*) AS messageCount
     , avg(post_prep.ps_length) AS averageMessageLength
     , sum(post_prep.ps_length) AS sumMessageLength
     , count(*) / pc.cnt AS percentageOfMessages
  FROM (
    SELECT extract(year from p1.ps_creationdate) AS messageYear
         , CASE WHEN p1.ps_replyof = -1 THEN 0 ELSE 1 END AS message_order -- was: IS NOT NULL AS isComment
         , CASE
             WHEN p1.ps_length <  40 THEN 0 -- short
             WHEN p1.ps_length <  80 THEN 1 -- one liner
             WHEN p1.ps_length < 160 THEN 2 -- tweet
             ELSE                      3 -- long
           END AS lengthCategory
         , p1.ps_length
      FROM post p1
     WHERE 1=1
       AND p1.ps_creationdate < DATE('2011-07-22') -- FIXME:param
       --AND ps_content IS NOT NULL
       AND p1.ps_imagefile = '-1' -- was: IS NULL
     ) post_prep
     , (
    SELECT count(*) AS cnt
      FROM post p2
     WHERE 1=1
       AND p2.ps_creationdate < DATE('2011-07-22') -- FIXME:param
     ) pc -- post_count
 GROUP BY messageYear, message_order, lengthCategory, pc.cnt
 --ORDER BY messageYear DESC, isComment ASC, lengthCategory ASC
;
