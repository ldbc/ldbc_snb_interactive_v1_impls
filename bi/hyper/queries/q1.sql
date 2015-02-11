select year, is_reply, size ,  count (*) as cnt, avg (ps_length) as avg_len,   sum (ps_length) as total,
  100.0 * count (*) / (select count (*) from post where ps_creationdate < '2011-9-15'::date)  as pct
from (
select  extract(year from ps_creationdate) as year, case when ps_replyof is null then 0 else 1 end as is_reply, 
  case when ps_length < 40 then 'short'
       when ps_length < 80 then 'one liner'
       when ps_length < 160 then 'tweet'
       else 'long' end as size, 
      ps_length
from post 
where ps_creationdate <  '2011-9-15'::date and ps_imagefile is null
) data
group by year, is_reply, size
order by year, is_reply, size;

