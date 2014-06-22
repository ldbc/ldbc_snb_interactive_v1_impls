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
	insert into person values(personid, personfirstname, personlastname, gender,
	       	    	   	  stringdate(birthday), stringdate(creationdate),
				  bit_or(
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[0], 24),
					       bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[1], 16)),
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[2], 8),
					       sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[3])
       				  ),
				  browserused, cityid, null);
	for vectored
	    (in i1 varchar := languages) {
	    insert into person_language values(personid, i1);
	}
	for vectored
	    (in i1 varchar := emails) {
	    insert into person_email values(personid, i1);
	}
	for vectored
	    (in i1 int := tagids) {
	    insert into person_tag values(personid, i1);
	}
	for vectored
	    (in i1 int := studyatorgids, in i2 int := studyatyears) {
	    insert into person_university values(personid, i1, i2);
	}
	for vectored
	    (in i1 int := workatorgids, in i2 int := workatyears) {
	    insert into person_company values(personid, i1, i2);
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
	insert into likes values(personid, postid, stringdate(datetimestr));
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
	insert into forum values(forumid, forumtitle, stringdate(creationdate), moderatorpersonid);
	for vectored
	    (in i1 int := tagids) {
	    insert into forum_tag values(forumid, i1);
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
	insert into forum_person values(forumid, personid, stringdate(creationdate));
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
	insert into post values(postid, imagefile, stringdate(creationdate),
				  bit_or(
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[0], 24),
					       bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[1], 16)),
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[2], 8),
					       sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[3])
       				  ),
				browserused, lang, content, len, authorpersonid, countryid, forumid, NULL, NULL);
				
	for vectored
	    (in i1 int := tagids) {
	    insert into post_tag values(postid, i1);
	}

};

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
	insert into post values(commentid, NULL, stringdate(creationdate),
				  bit_or(
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[0], 24),
					       bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[1], 16)),
					bit_or( bit_shift(sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[2], 8),
					       sprintf_inverse(locationip, '%d.%d.%d.%d', 2)[3])
       				  ),
				browserused, NULL, content, len, authorpersonid, countryid, NULL,
				replytocommentid+replytopostid+1,
				NULL);
				
	for vectored
	    (in i1 int := tagids) {
	    insert into post_tag values(commentid, i1);
	}

};
				     

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
	insert into knows values(person1id, person2id, stringdate(creationdate));
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

