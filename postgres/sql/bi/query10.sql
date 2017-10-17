with person_tag_post_score(ptps_personid, ptps_score) as (
  select ps_creatorid, count(*)
  from tag
  join post_tag on t_tagid = pst_tagid
  join post on pst_postid = ps_postid
  where t_name = '--1--'
  group by ps_creatorid
),
person_interest_score(pis_personid, pis_score) as (
  select pt_personid, 100
  from person_tag
  join tag on t_tagid = pt_tagid
  where t_name = '--1--'
),
person_total_score(pts_personid, pts_score) as (
  select coalesce(ptps_personid, pis_personid), coalesce(pis_score,0)+coalesce(ptps_score,0)
  from person_tag_post_score full outer
  join person_interest_score on pis_personid = ptps_personid
),
person_friends_score(pfs_personid, pfs_score) as (
  select a.pts_personid, b.pts_score
  from person_total_score a
  join knows on (a.pts_personid = k_person1id)
  join person_total_score b on k_person2id = b.pts_personid
  union all
  select a.pts_personid, b.pts_score
  from person_total_score a
  join knows on (a.pts_personid = k_person2id)
  join person_total_score b on k_person1id = b.pts_personid
)
select
  pts_personid,
  pts_score,
  sum(coalesce(pfs_score,0))
from person_total_score
left join person_friends_score on pts_personid = pfs_personid
group by pts_personid, pts_score
order by
  pts_score + sum(coalesce(pfs_score, 0)) desc,
  pts_personid asc
limit 100;
