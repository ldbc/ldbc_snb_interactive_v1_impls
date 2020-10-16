/* Q10. Experts in social circle
\set personId 19791209310731
\set country '\'Pakistan\''
\set tagClass '\'MusicalArtist\''
\set minPathDistance 2
\set maxPathDistance 3

For the SF1 database size, this query completes in a reasonable time for maxPathDistance <= 4.
Above that, I also encountered the following error because of the explosion in the number of paths.
  ERROR:  could not write to tuplestore temporary file: No space left on device
 */
WITH RECURSIVE friends(startPerson, path, friend) AS (
    SELECT p_personid, ARRAY[]::record[], p_personid
      FROM person
     WHERE 1=1
       AND p_personid = :personId
  UNION ALL
    SELECT f.startPerson
         , f.path || ROW(k.k_person1id, k.k_person2id)
         , CASE WHEN f.friend = k.k_person1id then k.k_person2id ELSE k.k_person1id END
      FROM friends f
         , knows k
     WHERE 1=1
        -- join
       AND f.friend = k.k_person1id -- note, that knows table have both (p1, p2) and (p2, p1)
        -- filter
       -- knows edge can't be traversed twice
       AND NOT ARRAY[ROW(k.k_person1id, k.k_person2id), ROW(k.k_person2id, k.k_person1id)] && f.path
        -- stop condition
       AND coalesce(array_length(f.path, 1), 0) < :maxPathDistance
)
   , friend_list AS (
    SELECT DISTINCT f.friend AS friendid
      FROM friends f
         , person tf -- the friend's preson record
         , place ci -- city
         , place co -- country
     WHERE 1=1
        -- join
       AND f.friend = tf.p_personid
       AND tf.p_placeid = ci.pl_placeid
       AND ci.pl_containerplaceid = co.pl_placeid
        -- filter
       AND coalesce(array_length(f.path, 1), 0) BETWEEN :minPathDistance AND :maxPathDistance
       AND co.pl_name = :country
)
   , messages_of_tagclass_by_friends AS (
    SELECT DISTINCT f.friendid
         , m.m_messageid AS messageid
      FROM friend_list f
         , message m
         , message_tag pt
         , tag t
         , tagclass tc
     WHERE 1=1
        -- join
       AND f.friendid = m.m_creatorid
       AND m.m_messageid = pt.mt_messageid
       AND pt.mt_tagid = t.t_tagid
       AND t.t_tagclassid = tc.tc_tagclassid
        -- filter
       AND tc.tc_name = :tagClass
)
SELECT m.friendid AS "person.id"
     , t.t_name AS "tag.name"
     , count(*) AS messageCount
  FROM messages_of_tagclass_by_friends m
     , message_tag pt
     , tag t
 WHERE 1=1
    -- join
   AND m.messageid = pt.mt_messageid
   AND pt.mt_tagid = t.t_tagid
 GROUP BY m.friendid, t.t_name
 ORDER BY messageCount DESC, t.t_name, m.friendid
 LIMIT 100
;
