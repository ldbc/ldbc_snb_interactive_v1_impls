/* Q12. Trending Posts
\set date '\'2011-07-22T00:00:00.000+00:00\''::timestamp
\set likeThreshold 400
 */
SELECT result.messageId
     , result.messageCreationDate
     , result.creatorFirstName
     , result.creatorLastName
     , result.likeCount
  FROM (
    SELECT m.ps_postid AS messageId
        , m.ps_creationdate AS messageCreationDate
        , c.p_firstname AS creatorFirstName
        , c.p_lastname AS creatorLastName
        , count(*) as likeCount
    FROM post m
        , person c -- creator
        , likes l
    WHERE 1=1
        -- join
    AND m.ps_creatorid = c.p_personid
    AND m.ps_postid = l.l_postid
        -- filter
    AND m.ps_creationdate > DATE('2011-07-22') -- FIXME:param :date
    GROUP BY m.ps_postid
            , m.ps_creationdate
            , c.p_firstname
            , c.p_lastname
  ) result
 WHERE result.likeCount > 400 -- FIXME:param :likeThreshold
 --ORDER BY likeCount DESC, messageId
 --LIMIT 100
;
