/* Q12. Trending Posts
\set date '\'2011-07-22T00:00:00.000+00:00\''::timestamp
\set likeThreshold 400
 */
select
  ps_postid,
  p_firstname,
  p_lastname,
  ps_creationdate,
  count(*) as likeCount
from post, person, likes
where ps_postid in (select l_postid from likes group by l_postid having count(*) > :likeThreshold)
  and p_personid = ps_creatorid
  and l_postid = ps_postid
  and ps_creationdate > :date
group by ps_postid, p_firstname, p_lastname, ps_creationdate
order by
  likeCount desc,
  ps_postid asc
limit 100;
