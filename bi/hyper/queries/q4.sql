select top 20 f_forumid, f_title, f_creationdate, f_moderator, count (*) as cnt 
from forum, post, post_tag, tag_tagclass, tagclass, person, country  
where tc_name = 'MusicalArtist' and ttc_tagclassid = tc_tagclassid
      and ttc_tagid = pst_tagid and pst_postid = ps_postid
      and ps_forumid = f_forumid and f_moderator = p_personid
      and p_placeid = ctry_city and ctry_name = 'India' 
group by f_forumid, f_title, f_creationdate, f_moderator
order by  cnt desc, f_forumid;

