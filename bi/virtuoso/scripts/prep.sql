create procedure p_age_group (in bday int, in today int) returns int
{
  return floor (datediff ('year', dateadd('millisecond', bday, stringdate ('1970.1.1 00:00:00.000+00:00')), dateadd('millisecond', today, stringdate ('1970.1.1 00:00:00.000+00:00'))) / 5);
}

create view person_tag_rel as
select tr_personid, tr_tag, sum (sc) as tr_score from (
select pt_personid as tr_personid, t_name as tr_tag, 100 as sc from person_tag, tag where  t_tagid = pt_tagid
union all select ps_creatorid, t_name, 1 as sc from post, post_tag, tag where pst_postid = ps_postid and pst_tagid = t_tagid) ff
group by tr_personid, tr_tag
;

create view related as
select rep.ps_creatorid as p1, org.ps_creatorid as p2, 4 as score
  from post org, post rep where rep.ps_replyof = org.ps_postid
union all
select org.ps_creatorid as p1, rep.ps_creatorid as p2, 1 as score
  from post org, post rep where rep.ps_replyof = org.ps_postid
union all
select k_person1id, k_person2id, 15 as score from knows
union all
  select l_personid, ps_creatorid, 10 from likes, post where l_postid = ps_postid
union all
  select ps_creatorid, l_personid, 1 from likes, post where l_postid = ps_postid
;
