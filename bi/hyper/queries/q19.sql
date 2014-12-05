select p.p_personid, count(distinct stranger.p_personid) as strangercnt, count(distinct rep.ps_postid) as cnt
from person p, post org, post rep, person stranger
where p.p_birthday > '1989-1-1'::date
and org.ps_postid = rep.ps_replyof
and rep.ps_creatorid = p.p_personid
and org.ps_creatorid = stranger.p_personid
and not exists(select 1 from knows where k_person1id = p.p_personid and k_person2id=stranger.p_personid)
and stranger.p_personid in 
    (select fp_personid from  forum_person, forum_tag, tagclass, tag_tagclass
      where ft_forumid=fp_forumid
          and ft_tagid=ttc_tagid 
          and ttc_tagclassid=tc_tagclassid 
          and tc_name='MusicalArtist')
and stranger.p_personid in 
    (select fp_personid from forum_person, forum_tag, tagclass, tag_tagclass
      where ft_forumid=fp_forumid
          and ft_tagid=ttc_tagid 
          and ttc_tagclassid=tc_tagclassid 
          and tc_name='OfficeHolder')
group by p.p_personid
order by cnt desc
limit 10;

