

create function q_name (in str2 any array) returns varchar 
{
  vectored;
  declare str varchar;
  str := cast (str2 as varchar);
  if (str like '%_view_%' or str like '%_Update_%')
  return str;
  return regexp_substr ('Q[0-9]*', str, 0); 
}


create procedure path_str (in path any)
{
  declare str any;
  declare inx int;
  str := '';
  foreach (any  st  in path) do
    str := str || sprintf (' %ld->%ld (%g) ', st[0], coalesce (st[1], 0), coalesce (st[2], 0));
  return str;
}


create procedure c_weight (in p1 bigint, in p2 bigint)
{
  vectored;
  if (p1 is null or p2 is null)
     return 0;
  return
  	  (select coalesce (sum (case when ps2.ps_replyof is null then 1 else 0.5 end), 0) from post ps1, post ps2
	   where ps1.ps_creatorid = p1 and ps1.ps_replyof = ps2.ps_postid and ps2.ps_creatorid = p2) +
	  (select coalesce (sum (case when ps2.ps_replyof is null then 1 else 0.5 end), 0)  from post ps1, post ps2
	   where ps1.ps_creatorid = p2 and ps1.ps_replyof = ps2.ps_postid and ps2.ps_creatorid = p1);
}


create procedure c_weight_upd (in p1 bigint, in p2 bigint)
{
  vectored;
  set isolation = 'serializable';
  if (p1 is null or p2 is null)
    return 0;
  return
  	  (select coalesce (sum (case when ps2.ps_replyof is null then 1 else 0.5 end), 0) from post ps1, post ps2
	   where ps1.ps_creatorid = p1 and ps1.ps_replyof = ps2.ps_postid and ps2.ps_creatorid = p2 for update) +
	  (select coalesce (sum (case when ps2.ps_replyof is null then 1 else 0.5 end), 0)  from post ps1, post ps2
	   where ps1.ps_creatorid = p2 and ps1.ps_replyof = ps2.ps_postid and ps2.ps_creatorid = p1 for update);
}


create function c_weight_pre (in p1 bigint, in p2 bigint)
{
  vectored;
  if (p1 is null or p2 is null)
    return 0;
  if (p1 < p2)
    return coalesce ((select kw_weight from k_weight where kw_p1 = p1 and kw_p2 = p2), 0);
  else
    return coalesce ((select kw_weight from k_weight where kw_p1 = p2 and kw_p2 = p1), 0);
}


create procedure LdbcUpdate1AddPerson (in personid int,
       		 		       in personfirstname varchar,
				       in personlastname varchar,
				       in gender varchar,
				       in birthday varchar,
				       in creationdate varchar,
				       in locationip varchar,
				       in browserused varchar,
				       in cityid int, 
				       in languages varchar array,
				       in emails varchar array,
				       in tagids int array,
				       in studyatorgids int array,
				       in studyatyears int array,
				       in workatorgids int array,
				       in workatyears int array
				       )
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
--	insert into person values(personid, personfirstname, personlastname, gender,
--	       	    	   	  datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(birthday)),
--				  datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)),
--				  locationip,
--				  browserused, cityid, null);
	delete from person where p_personid = personid;
	for vectored
	    (in i1 varchar := languages) {
	    --insert into person_language values(personid, i1);
	    delete from person_language where plang_personid = personid;
	}
	for vectored
	    (in i1 varchar := emails) {
	    --insert into person_email values(personid, i1);
	    delete from person_email where pe_personid = personid;
	}
	for vectored
	    (in i1 int := tagids) {
	    --insert into person_tag values(personid, i1);
	    delete from person_tag where pt_personid = personid;
	}
	for vectored
	    (in i1 int := studyatorgids, in i2 int := studyatyears) {
	    --insert into person_university values(personid, i1, i2);
	    delete from person_university where pu_personid = personid;
	}
	for vectored
	    (in i1 int := workatorgids, in i2 int := workatyears) {
	    --insert into person_company values(personid, i1, i2);
	    delete from person_company where pc_personid = personid;
	}
	return;
};


create procedure LdbcUpdate2AddPostLike (in personid int,
       		 			 in postid int,
					 in datetimestr varchar
					 )
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
	--insert into likes values(personid, postid, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(datetimestr)));
	delete from likes where l_personid = personid and l_postid = postid;
	return;
};

create procedure LdbcUpdate4AddForum  (in forumid int,
       		 		       in forumtitle varchar,
				       in creationdate varchar,
				       in moderatorpersonid int, 
				       in tagids int array
				       )
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
	--insert into forum values(forumid, forumtitle, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)), moderatorpersonid);
	delete from forum where f_forumid = forumid;
	for vectored
	    (in i1 int := tagids) {
	    --insert into forum_tag values(forumid, i1);
	    delete from forum_tag where ft_forumid = forumid;
	}
	return;
};

create procedure LdbcUpdate5AddForumMembership (in forumid int,
       		 			        in personid int,
						in creationdate varchar
					 	)
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
	--insert into forum_person values(forumid, personid, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)));
	delete from forum_person where fp_forumid = forumid and fp_personid = personid;
	return;
};

create procedure LdbcUpdate6AddPost (in postid int,
       		 		     in imagefile varchar,
				     in creationdate varchar,
				     in locationip varchar,
				     in browserused varchar,
				     in lang varchar,
				     in content varchar,
				     in len int,
				     in authorpersonid int,
				     in forumid int,
				     in countryid int,
				     in tagids int array
				     )
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
--	insert into post values(postid, imagefile, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)),
--	       	    	 	locationip,
--				browserused, lang, content, len, authorpersonid, authorpersonid, countryid, forumid, NULL, NULL);
	delete from post where ps_postid = postid;
				
	for vectored
	    (in i1 int := tagids) {
	    --insert into post_tag values(postid, i1);
	    delete from post_tag where pst_postid = postid;
	}

};


create procedure kw_reply (in p1 bigint, in r_post bigint, in r_comment bigint)
{
  declare tmp, p2, reply bigint;
  declare inc real;
  whenever not found goto none;
  select ps_creatorid into p2 from post where ps_postid = r_post + r_comment + 1;
if (r_post)
 inc := 1;
 else 
 inc := 0.5;
  if (p1 > p2)
    {
    tmp := p2;
    p2 := p1;
    p1 := tmp;
    }
  set isolation = 'serializable';
  if (not exists (select 1 from knows where k_person1id = p1 and k_person2id = p2))
    return;
  if (exists (select 1 from k_weight where kw_p1 = p1 and kw_p2 = p2 for update))
    update k_weight set kw_weight = kw_weight + inc where kw_p1 = p1 and kw_p2 = p2;
  else
    insert into k_weight values (p1, p2, inc);
none:
return;
}

create procedure LdbcUpdate7AddComment (in commentid int,
				    	in creationdate varchar,
				     	in locationip varchar,
				     	in browserused varchar,
				     	in content varchar,
				     	in len int,
				     	in authorpersonid int,
				     	in countryid int,
					in replytopostid int,
					in replytocommentid int,
				     	in tagids int array
				     	)
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
--	insert into post values(commentid, NULL, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)),
--	       	    	 	locationip,
--				browserused, NULL, content, len, authorpersonid, NULL, countryid, NULL,
--				replytocommentid+replytopostid+1,
--				NULL);
	delete from post where ps_postid = commentid;
				
	for vectored
	    (in i1 int := tagids) {
	    --insert into post_tag values(commentid, i1);
	    delete from post_tag where pst_postid = commentid;
	}
	  kw_reply (authorpersonid, replytocommentid, replytopostid);
};
				     

create procedure k_weight_add (in p1 bigint, in p2 bigint)
{
  declare cw real;
 cw := c_weight_upd (p1, p2);
  if (cw <> 0)
    {
      if (p1 < p2)
	insert into k_weight values (p1, p2, cw);
      else
	insert into k_weight values (p1, p2, cw);
    }
}

create procedure LdbcUpdate8AddFriendship (in person1id int,
       		 			   in person2id int,
					   in creationdate varchar
					   )
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
	--insert into knows values(person1id, person2id, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)));
	--insert into knows values(person2id, person1id, datediff ('millisecond',  stringdate ('1970.1.1 00:00:00.000+0000'), stringdate(creationdate)));
	delete from knows where k_person1id = person1id and k_person2id = person2id;
	delete from knows where k_person1id = person2id and k_person2id = person1id;
	k_weight_add (person1id, person2id);
	return;
};

create procedure LdbcUpdateSparql (in triplets varchar array)
{
	declare n_dead any;
	n_dead := 0;
	again:	
	declare exit handler for sqlstate '40001' {
		rollback work;
		n_dead := n_dead + 1;
		if (10 < n_dead) {
		   signal ('40001', 'Over 10 deadlocks in rdf load, please retry load');
		   return;
		}
		goto again;
	};
	for vectored
	    (in t varchar := triplets) {
	    ttlp_mt(t, '', 'sib', 0);
	}
	return;
};

create procedure post_view (in postid int) {
  declare content, imagefile varchar;
  declare creationdate int;
  result_names(content, imagefile, creationdate);

  whenever not found goto done1;
  declare cr1 cursor for 
      select ps_content, ps_imagefile, ps_creationdate
        from post
	where
	  ps_postid = postid;

  open cr1;
  while (1)
    {
      fetch cr1 into content, imagefile, creationdate;
      result (content, imagefile, creationdate);
    }

done1:
  close cr1;

  declare firstname, lastname varchar;
  declare personid int;
  result_names(personid, firstname, lastname);
  end_result ();

  whenever not found goto done2;
  declare cr2 cursor for 
      select p_personid, p_firstname, p_lastname
        from post, person
	where
	  ps_postid = postid and ps_creatorid = p_personid;

  open cr2;
  while (1)
    {
      fetch cr2 into personid, firstname, lastname;
      result (personid, firstname, lastname);
    }

done2:
  close cr2;

  declare modfirstname, modlastname, forumname varchar;
  declare modpersonid, forumid int;
  result_names(forumid, forumname, modpersonid, modfirstname, modlastname);
  end_result ();

  whenever not found goto done3;
  declare cr3 cursor for 
      select f_forumid, f_title, p_personid, p_firstname, p_lastname
        from post, person, forum
	where
	  ps_postid = postid and ps_forumid = f_forumid and f_moderatorid = p_personid;

  open cr3;
  while (1)
    {
      fetch cr3 into forumid, forumname, modpersonid, modfirstname, modlastname;
      result (forumid, forumname, modpersonid, modfirstname, modlastname);
    }

done3:
  close cr3;

  declare origpostcontent, origfirstname, origlastname varchar;
  declare origpostid, origautorid, friendornot int;
  result_names(origpostid, origpostcontent, origautorid, origfirstname, origlastname, friendornot);
  end_result ();

  whenever not found goto done4;
  declare cr4 cursor for 
      select p2.ps_postid, p2.ps_content, p_personid, p_firstname, p_lastname,
      	     (case when exists (
	     	   	       select 1 from knows
			       where p1.ps_creatorid = k_person1id and p2.ps_creatorid = k_person2id)
	      then 1
	      else 0
	      end)
        from post p1, post p2, person
	where
	  p1.ps_postid = postid and p1.ps_replyof = p2.ps_postid and p2.ps_creatorid = p_personid;

  open cr4;
  while (1)
    {
      fetch cr4 into origpostid, origpostcontent, origautorid, origfirstname, origlastname, friendornot;
      result (origpostid, origpostcontent, origautorid, origfirstname, origlastname, friendornot);
    }

done4:
  close cr4;

}

create procedure post_view_1 (in postid int) {
  declare content, imagefile varchar;
  declare creationdate int;
  result_names(content, imagefile, creationdate);

  whenever not found goto done1;
  declare cr1 cursor for 
      select ps_content, ps_imagefile, ps_creationdate
        from post
	where
	  ps_postid = postid;

  open cr1;
  while (1)
    {
      fetch cr1 into content, imagefile, creationdate;
      result (content, imagefile, creationdate);
    }

done1:
  close cr1;
}

create procedure post_view_2 (in postid int) {
  declare firstname, lastname varchar;
  declare personid int;
  result_names(personid, firstname, lastname);

  whenever not found goto done2;
  declare cr2 cursor for 
      select p_personid, p_firstname, p_lastname
        from post, person
	where
	  ps_postid = postid and ps_creatorid = p_personid;

  open cr2;
  while (1)
    {
      fetch cr2 into personid, firstname, lastname;
      result (personid, firstname, lastname);
    }

done2:
  close cr2;
}


create procedure post_view_3 (in postid int) {
  declare modfirstname, modlastname, forumname varchar;
  declare modpersonid, forumid int;
  result_names(forumid, forumname, modpersonid, modfirstname, modlastname);

  whenever not found goto done3;
  declare cr3 cursor for 
      select f_forumid, f_title, p_personid, p_firstname, p_lastname
        from post, person, forum
	where
	  ps_postid = (select coalesce(min(ps_replyof), postid) from (select transitive t_in (1) t_out (2) t_distinct ps_postid, ps_replyof from post) k where ps_postid = postid)
	  and ps_forumid = f_forumid and f_moderatorid = p_personid;

  open cr3;
  while (1)
    {
      fetch cr3 into forumid, forumname, modpersonid, modfirstname, modlastname;
      result (forumid, forumname, modpersonid, modfirstname, modlastname);
    }

done3:
  close cr3;
}

create procedure post_view_4 (in postid int) {
  declare origpostcontent, origfirstname, origlastname varchar;
  declare origpostid, origautorid, friendornot int;
  declare creationdate int;
  result_names(origpostid, origpostcontent, creationdate, origautorid, origfirstname, origlastname, friendornot);

  whenever not found goto done4;
  declare cr4 cursor for 
      select p2.ps_postid, p2.ps_content, p2.ps_creationdate, p_personid, p_firstname, p_lastname,
      	     (case when exists (
	     	   	       select 1 from knows
			       where p1.ps_creatorid = k_person1id and p2.ps_creatorid = k_person2id)
	      then 1
	      else 0
	      end)
        from post p1, post p2, person
	where
	  p1.ps_postid = postid and p2.ps_replyof = p1.ps_postid and p2.ps_creatorid = p_personid
	order by 3 desc, 4;

  open cr4;
  while (1)
    {
      fetch cr4 into origpostid, origpostcontent, creationdate, origautorid, origfirstname, origlastname, friendornot;
      result (origpostid, origpostcontent, creationdate, origautorid, origfirstname, origlastname, friendornot);
    }

done4:
  close cr4;
}


create procedure person_view (in personid int) {
  declare firstname, lastname, gender, browserused varchar;
  declare birthday int;
  declare creationdate int;
  declare locationip varchar;
  declare placeid int;
  result_names(firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid);

  whenever not found goto done1;
  declare cr1 cursor for 
      select p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid
        from person
	where
	  p_personid = personid;

  open cr1;
  while (1)
    {
      fetch cr1 into firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid;
      result (firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid);
    }

done1:
  close cr1;

  declare content, imagefile, origfirstname, origlastname varchar;
  declare postid, origpostid, origpersonid int;
  declare postcreationdate int;
  result_names(postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);
  end_result ();

  whenever not found goto done2;
  declare cr2 cursor for 
    select p1.ps_postid, p1.ps_content, p1.ps_imagefile, p1.ps_creationdate,
           p2.ps_postid, p2.p_personid, p2.p_firstname, p2.p_lastname
    from 
         (select top 10 ps_postid, ps_content, ps_imagefile, ps_creationdate, ps_replyof
          from post
          where ps_creatorid = personid
          order by ps_creationdate desc
         ) p1
         left outer join
         (select ps_postid, p_personid, p_firstname, p_lastname
          from post, person
          where ps_creatorid = p_personid
         )p2  
         on p2.ps_postid = p1.ps_replyof;

  open cr2;
  while (1)
    {
      fetch cr2 into postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname;
      result (postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);
    }

done2:
  close cr2;

  declare friendfirstname, friendlastname varchar;
  declare friendpersonid int;
  declare since int;
  result_names(friendpersonid, friendfirstname, friendlastname, since);
  end_result ();

  whenever not found goto done3;
  declare cr3 cursor for 
      select p_personid, p_firstname, p_lastname, k_creationdate
        from knows, person
	where
	  k_person1id = personid and k_person2id = p_personid;

  open cr3;
  while (1)
    {
      fetch cr3 into friendpersonid, friendfirstname, friendlastname, since;
      result (friendpersonid, friendfirstname, friendlastname, since);
    }

done3:
  close cr3;

}

create procedure person_view_1 (in personid int) {
  declare firstname, lastname, gender, browserused, locationip varchar;
  declare birthday int;
  declare creationdate int;
  declare placeid int;
  result_names(firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid);

  whenever not found goto done1;
  declare cr1 cursor for 
      select p_firstname, p_lastname, p_gender, p_birthday, p_creationdate,
      	     p_locationip,
	     p_browserused, p_placeid
        from person
	where
	  p_personid = personid;

  open cr1;
  while (1)
    {
      fetch cr1 into firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid;
      result (firstname, lastname, gender, birthday, creationdate, locationip, browserused, placeid);
    }

done1:
  close cr1;
}

create procedure person_view_2 (in personid int) {
  declare content, imagefile, origfirstname, origlastname varchar;
  declare postid, origpostid, origpersonid int;
  declare postcreationdate int;
  result_names(postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);

  whenever not found goto done2;
  declare cr2 cursor for 
    select p1.ps_postid, p1.ps_content, p1.ps_imagefile, p1.ps_creationdate,
           p2.ps_postid, p2.p_personid, p2.p_firstname, p2.p_lastname
    from 
         (select top 10 ps_postid, ps_content, ps_imagefile, ps_creationdate, ps_replyof
          from post
          where ps_creatorid = personid
          order by ps_creationdate desc
         ) p1
         left outer join
         (select ps_postid, p_personid, p_firstname, p_lastname
          from post, person
          where ps_creatorid = p_personid
         )p2  
         on p2.ps_postid = p1.ps_replyof;

  open cr2;
  while (1)
    {
      fetch cr2 into postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname;
      result (postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);
    }

done2:
  close cr2;
}

create procedure person_view_2 (in personid int) {
  declare content, imagefile, origfirstname, origlastname varchar;
  declare postid, origpostid, origpersonid int;
  declare postcreationdate int;
  result_names(postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);

  whenever not found goto done2;
  declare cr2 cursor for 
    select top 10 ps_postid, ps_content, ps_imagefile, ps_creationdate
    from post
    where ps_creatorid = personid
    order by ps_creationdate desc;

  open cr2;
  while (1)
    {
      fetch cr2 into postid, content, imagefile, postcreationdate;
      select ps_postid, p_personid, p_firstname, p_lastname
      into origpostid, origpersonid, origfirstname, origlastname
      from post, person
      where ps_postid = (select coalesce(min(ps_replyof), postid) as origpostid
	  		 from (select transitive t_in (1) t_out (2) t_distinct ps_postid, ps_replyof from post) k
			 where ps_postid = postid)
      and ps_creatorid = p_personid;
      result (postid, content, imagefile, postcreationdate, origpostid, origpersonid, origfirstname, origlastname);
    }

done2:
  close cr2;
}


create procedure person_view_3 (in personid int) {
  declare friendfirstname, friendlastname varchar;
  declare friendpersonid int;
  declare since int;
  result_names(friendpersonid, friendfirstname, friendlastname, since);

  whenever not found goto done3;
  declare cr3 cursor for 
      select p_personid, p_firstname, p_lastname, k_creationdate
        from knows, person
	where
	  k_person1id = personid and k_person2id = p_personid
	order by 4 desc, 1;

  open cr3;
  while (1)
    {
      fetch cr3 into friendpersonid, friendfirstname, friendlastname, since;
      result (friendpersonid, friendfirstname, friendlastname, since);
    }

done3:
  close cr3;
}
