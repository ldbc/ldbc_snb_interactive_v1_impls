create procedure percentile (in percentile float, in qname varchar) {
  whenever not found goto done;
  declare i, total int;
  declare res any;
  declare cr1 cursor for 
      select ql_rt_msec
        from ql_staging
	where ql_q = qname
	order by ql_rt_msec;

  open cr1;
  select count(*) into total from ql_staging where ql_q = qname;
  i := 0;
  while (i < floor(total * percentile))
    {
      fetch cr1 into res;
      i := i + 1;
    }

  return res;

done:
  close cr1;       
}
