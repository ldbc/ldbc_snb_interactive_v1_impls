

log_enable(2, 1);
__dbf_set ('txn_after_image_limit', 10000000000);
__dbf_set ('enable_mt_txn', 1);
__dbf_set ('enable_mt_transact', 1);




create procedure snb_load (in f varchar)
{
  log_enable (2,1);
  if (f like '%likes_pos%')
    {
      insert into likes	 select * from person_likes_post_f table option (from f);
      return;
    }
  if (f like '%likes_com%')
    {
      insert into likes select pp_personid, pp_postid + 0, pp_creationdate from person_likes_comment_f table option (from f); 
      return;
    }

  if (f like '%comment_hasTag%')
    {
      insert soft post_tag select ct_commentid + 0, ct_tagid from comment_hastag_tag_f table option (from f);  
      return;
    }
  if (f like '%post_hasTag%')
    {
      insert soft post_tag select * from post_hastag_tag_f table option (from f);
      return;
    }
  if (f like '%post%')
    {
      insert into post(ps_postid, ps_imagefile, ps_creationdate, ps_locationip, ps_browserused, ps_language, ps_content, ps_length, ps_creatorid, ps_locationid, ps_forumid)
select p_postid, p_imagefile, p_creationdate,
       bit_or(
         bit_or( bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[0], 24),
                 bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[1], 16)),
         bit_or( bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[2], 8),
                 sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[3])
       ), 
       p_browserused, p_language, p_content, p_length, p_creator, p_placeid, p_forumid 
from post_f table option (from f) where idn (p_postid) is not null;
      return;
    }
  if (f like '%comment%')
    {
      insert into post(ps_postid, ps_creationdate, ps_locationip, ps_browserused, ps_content, ps_length, ps_creatorid, ps_locationid, ps_replyof)
select (c_commentid + 0), c_creationdate, 
       bit_or(
         bit_or( bit_shift(sprintf_inverse(c_locationip, '%d.%d.%d.%d', 2)[0], 24),
                 bit_shift(sprintf_inverse(c_locationip, '%d.%d.%d.%d', 2)[1], 16)),
         bit_or( bit_shift(sprintf_inverse(c_locationip, '%d.%d.%d.%d', 2)[2], 8),
                 sprintf_inverse(c_locationip, '%d.%d.%d.%d', 2)[3])
       ),
      c_browserused, c_content, c_length, c_creator, c_place, (case when isinteger (c_replyofcomment) then (c_replyofcomment + 0) else c_replyofpost  end)
    from comment_f table option (from f)  where idn (c_commentid) is not null; 
      return;
    }
  if (f like '%forum_hasMem%')
    {
      insert soft forum_person select * from forum_hasmember_person_f table option (from f) ;
      return;
    }
  if (f like '%forum_hasTag%')
    {
      insert into forum_tag select * from forum_hastag_tag_f table option (from f)  ;
      return;
    }

  if (f like '%forum%')
    {
      insert soft forum(f_forumid, f_title, f_creationdate, f_moderatorid)
	select f_forumid, f_title, f_creationdate, f_moderator
	from forum_f table option (from f)  ;
      return;
    }
  if (f like '%workAt%')
    {
      insert into person_company select * from person_workat_organisation_f table option (from f) ;
      return;
    }
  if (f like '%study%')
    {
      insert into person_university select * from person_studyat_organisation_f table option (from f) ;
      return;
    }
  if (f like '%organisat%')
    {
      insert into organisation
select o_organisationid, o_type, o_name, o_url, o_placeid
	from organisation_f table option (from f);
      return;
    }
  if (f like '%email%')
    {
      insert into person_email select * from person_email_emailaddress_f table option (from f)  ;
      return;
    }
  if (f like '%person_hasInt%')
    {
      insert into person_tag select * from person_hasinterest_tag_f table option (from f) ;
      return;
    }
  if (f like '%language%')
    {
      insert into person_language select * from person_speaks_language_f table option (from f) ;
      return;
    }
  if (f like '%person_knows_person%')
    {
      insert into knows select * from person_knows_person_f table option (from f)  ;
      return;
    }
  if (f like '%person%')
    {
      insert into person (   p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid)
select p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate,
       bit_or(
         bit_or( bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[0], 24),
                 bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[1], 16)),
         bit_or( bit_shift(sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[2], 8),
                 sprintf_inverse(p_locationip, '%d.%d.%d.%d', 2)[3])
       ), 
       p_browserused, p_placeid
from person_f table option (from f)    
where idn (p_personid) is not null;
      return;
    }

  if (f like '%place%')
    {
      insert into place
select p_placeid, p_name, p_url, p_type, p_ispartof
from place_f table option (from f)  ;
      return;
    }


  if (f like '%tag_hasTyp%')
    {
      insert soft tag_tagclass select * from tag_hastype_tagclass_f table option (from f);
      return;
    }
  if (f like '%tagclas%')
    {
      Insert soft tagclass select * from tagclass_f table option (from f);
      return;
    }
  if (f like '%ubclas%')
    {
      insert soft subclass select * from tagclass_issubclassof_tagclass_f table option (from f);
      return;
    }
  if (f like '%tag%')
    {
      insert into tag select * from tag_f table option (from f);
      return;
    }
  log_message (sprintf ('unrecognized file %s', f));
  signal ('BADFI', 'snb load does not know file type');
}


ld_dir ('outputDir', '%.csv', 'sql:snb_load (?)');

rdf_loader_run () &
rdf_loader_run () &
wait_for_children;
checkpoint;
