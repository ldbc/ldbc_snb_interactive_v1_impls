-- Experts in domain linked via comtact chain all working for Chinese companies 
select top 100 kn.k_person2id, t_name, count (*)  as cnt
from 
     (select transitive t_distinct t_in (1) t_out (2) k_person1id, k_person2id from knows
      where k_person2id in (select p_personid from person, country 
      	    		    where p_placeid = ctry_city and ctry_name = 'China')
     ) kn,
     post, post_tag, tag, tag_tagclass, tagclass
where ps_postid = pst_postid and t_tagid = pst_tagid and ttc_tagid = pst_tagid
      and ttc_tagclassid = tc_tagclassid and tc_name = 'MusicalArtist' 
      and ps_creatorid = kn.k_person2id and kn.k_person1id = 11052
group by t_name, kn.k_person2id 
order by cnt desc, t_name;
