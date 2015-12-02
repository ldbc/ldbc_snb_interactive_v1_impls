create procedure p_age_group (in bday int, in today int) returns int
{
  return floor (datediff ('year', dateadd('millisecond', bday, stringdate ('1970.1.1 00:00:00.000+00:00')), dateadd('millisecond', today, stringdate ('1970.1.1 00:00:00.000+00:00'))) / 5);
}

