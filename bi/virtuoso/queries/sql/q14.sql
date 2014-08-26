-- top thread initiators 
select top 100 p_personid, p_firstname, p_lastname,
       count (*) as cnt, count (distinct org.ps_postid) as n_threads
from person, post org,
     (select transitive t_in (1) t_out (2) r.ps_replyof, r.ps_postid
      from post r
      where r.ps_creationdate between cast ('2011-10-1' as date) and dateadd ('month', 3, cast ('2011-10-1' as date) )
     ) reps
where reps.ps_replyof = org.ps_postid and org.ps_replyof is null
      and org.ps_creatorid = p_personid 
      and org.ps_creationdate between  cast ('2011-10-1' as date) and  dateadd ('month', 3, cast ('2011-10-1' as date) )
group by p_personid 
order by cnt desc, p_personid;
