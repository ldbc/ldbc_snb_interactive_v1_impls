select
  year,
  is_reply,
  size,
  count(*) as cnt,
  avg(ps_length) as avg_len,
  sum(ps_length) as total,
  (0.0 + count(*)) / (select count(*) from post where ps_creationdate < --1--) as pct
from (
  select
    extract(year from ps_creationdate) as year,
    case
      when ps_replyof is null then 0
      else 1
    end as is_reply,
    case
      when ps_length < 40  then 0 -- 'short'
      when ps_length < 80  then 1 -- 'one liner'
      when ps_length < 160 then 2 -- 'tweet'
      else 3                      -- 'long'
    end as size,
    ps_length
  from post
  where ps_creationdate < --1--
    and ps_imagefile is null
) data
group by year, is_reply, size
order by
  year desc,
  is_reply asc,
  size asc;
