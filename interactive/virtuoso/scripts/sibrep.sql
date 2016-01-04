


create function q_name (in str2 any array) returns varchar 
{
  vectored;
  declare str varchar;
  str := cast (str2 as varchar);
  if (str like '%_view_%' or str like '%_Update_%')
  return str;
  return regexp_substr ('Q[0-9]*', str, 0); 
}

create table ql_staging (
ql_id int primary key identity, 
       ql_q varchar,
       ql_start_dt      datetime,
       ql_rt_msec       int,
       ql_text            LONG VARCHAR,
       ql_plan                    LONG VARCHAR
);


insert into ql_staging (ql_id, ql_q, ql_text, ql_plan, ql_rt_msec, ql_start_dt) 
select ql_id, regexp_substr ('Q[0-9]*', cast (ql_text as varchar), 0) , ql_text, ql_plan, ql_rt_msec, ql_start_dt from sys_query_log;





select ql_q, avg (ql_rnd_rows), avg (ql_seq_rows), (sum (ql_same_seg) * 100.0) / sum (ql_rnd_rows) s  from ql_staging s, sys_query_log l where s.ql_id =  l.ql_id and ql_rnd_rows > 0 group by ql_q order by 2 desc;

select  regexp_substr ('Q[0-9]*', cast (ql_text as varchar), 0) as q, sum (ql_c_clocks) / 2.3e9 from sys_query_log group by q order by 2 desc;

select  regexp_substr ('Q[0-9]*', cast (ql_text as varchar), 0) as q, avg (ql_c_clocks) / 2.3e9 from sys_query_log where ql_start_dt > {ts '2013-10-5 10:00'} group by q order by 2 desc;



select ql_q, count (distinct ql_plan_hash) from ql_staging  s, sys_query_log l where s.ql_id = l.ql_id group by ql_q order by 2 desc;



set blobs on;

select ql_q, ql_rt_msec, ql_text, ql_plan   from ql_staging q1
       where ql_rt_msec = (select min (ql_rt_msec) from ql_staging q2 where  q1.ql_q = q2.ql_q)
or ql_rt_msec = (select max (ql_rt_msec) from ql_staging q2 where  q1.ql_q = q2.ql_q)
order by ql_q, ql_rt_msec;



create table iss_pages (idx varchar, pgs int);
insert into iss_pages select iss_index, iss_pages from sys_index_space_stats;


select top 3 ql_rt_msec, ql_disk_reads, cast (ql_text as varchar) from (
select  regexp_substr ('Q[0-9]*', cast (ql_text as varchar), 0) as q, ql_rt_msec, ql_text, ql_disk_reads from sys_query_log) ql where q = 'Q9' order by ql_rt_msec desc;

select sprintf ('%9.2g%% %9.9g %s %d %9.6g %9.6g %9.6g', 
100 *   sq_count * sq_mean / (select sum (sq_count * sq_mean) from snb_result),
sq_count * sq_mean, sq_name, sq_count, sq_mean, sq_min, sq_max) from snb_result order by sq_count * sq_mean desc;



select sum (case when r_start - r_sched > 1000 then 1 else 0 end) as n_late, count (*) as cnt, sum (r_start - r_sched) as sum_delay, (r_start - (select min (r_start) from result_f)) / 10000 as wnd from result_f group by wnd order by wnd;

select n_late * 100.0 / cnt, n_late, cnt from (select sum (case when r_start - r_sched > 1000 then 1 else 0 end) as n_late, count (*) as cnt from result_f) f;

select r_op, avg(r_duration) as average, sum(r_duration) as total, sum(r_duration) * 100.0 / (select sum(r_duration) from result_f) as perc from result_f group by r_op order by perc desc;
