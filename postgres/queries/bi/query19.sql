/* Q19. Stranger’s interaction
\set date '\'1989-01-01T00:00:00.000+00:00\''::timestamp
\set tagClass1 '\'MusicalArtist\''
\set tagClass2 '\'OfficeHolder\''
 */
select
  p.p_personid,
  count(distinct stranger.p_personid) as strangercnt,
  count(distinct rep.ps_postid) as cnt
from person p, post org, post rep, person stranger
where p.p_birthday > :date
  and org.ps_postid = rep.ps_replyof
  and rep.ps_creatorid = p.p_personid
  and org.ps_creatorid = stranger.p_personid
  and not exists(
    select 1
    from knows
    where k_person1id = p.p_personid
      and k_person2id = stranger.p_personid)
  and stranger.p_personid in (
    select fp_personid
    from forum_person, forum_tag, tag, tagclass
    where ft_forumid = fp_forumid
      and ft_tagid = t_tagid
      and t_tagclassid = tc_tagclassid
      and tc_name = :tagClass1
  )
  and stranger.p_personid in (
    select fp_personid
     from forum_person, forum_tag, tag, tagclass
    where ft_forumid = fp_forumid
      and ft_tagid = t_tagid
      and t_tagclassid = tc_tagclassid
      and tc_name = :tagClass2
  )
group by p.p_personid
order by cnt desc
limit 100;
