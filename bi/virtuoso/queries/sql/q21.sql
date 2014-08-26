-- People who are liked by people who produce nothing.
-- Problem: likes distribution (division by 0)
select top 100 liked, count (*) as n_likers, sum (likerposts) as liker_posts
from
	(select liked.p_personid as liked, (select count (*) from post lrpost
				    	    where lrpost.ps_creatorid = liker.p_personid) as likerposts 
	 from person liked, post, likes, person liker, country  
	 where ps_creationdate between cast ('2012-4-1' as date) and dateadd ('month', 3, cast ('2012-4-1' as date))
	 and ps_creatorid = liked.p_personid 
	 and liked.p_placeid = ctry_city and ctry_name = 'India'
	 and l_postid = ps_postid and l_personid = liker.p_personid
	) lks 
group by liked 
order by cast (n_likers as real) / liker_posts desc;
