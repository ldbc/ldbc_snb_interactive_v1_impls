-- Posting summary 
select  year (ps_creationdate) as year, case when ps_replyof is null then 0 else 1 end as is_reply, 
	case when ps_length < 40 then 'short'
	     when ps_length < 80 then 'one liner'
	     when ps_length < 160 then 'tweet'
	     else 'long' end as size, 
	count (*) as cnt, avg (ps_length) as avg_len, 
	sum (ps_length) as total,
	100.0 * count (*) / (select count (*) from post where ps_creationdate < cast ('2011-9-15'as date))  as pct
from post 
where ps_creationdate <  cast ('2011-9-15'as date)
group by year, is_reply, size
order by year, is_reply, size;
; 
