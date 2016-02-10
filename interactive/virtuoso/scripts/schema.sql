drop table post;
drop table forum;
drop table forum_person;
drop table forum_tag;
drop table organisation;
drop table person;
drop table person_email;
drop table person_tag;
drop table knows;
drop table likes;
drop table person_language;
drop table person_university;
drop table person_company;
drop table place;
drop table post_tag;
drop table tagclass;
drop table subclass;
drop table tag;
drop table tag_tagclass;


create table post (
    ps_postid bigint primary key (column),
    ps_imagefile varchar,
    ps_creationdate bigint not null,
    ps_locationip varchar not null,
    ps_browserused varchar not null,
    ps_language varchar,
    ps_content long varchar not null,
    ps_length int not null,
    ps_creatorid bigint,
    ps_p_creatorid bigint,
    ps_locationid bigint,
    ps_forumid bigint,
    ps_replyof bigint,
    ps_country int
);

alter index post on post partition (ps_postid int (0hexffff00));

create table forum (
   f_forumid bigint primary key (column),
   f_title varchar not null,
   f_creationdate bigint not null, 
   f_moderatorid bigint
);

alter index forum on forum partition (f_forumid int (0hexffff00));

create table forum_person (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_creationdate bigint not null,
   primary key (fp_forumid, fp_personid) column
);

alter index forum_person on forum_person partition (fp_forumid int (0hexffff00));

create table forum_tag (
   ft_forumid bigint not null,
   ft_tagid bigint not null,
   primary key (ft_forumid, ft_tagid) column
);

alter index forum_tag on forum_tag partition (ft_forumid int (0hexffff00));

create table organisation (
   o_organisationid bigint primary key (column),
   o_type varchar not null,
   o_name varchar not null,
   o_url varchar not null,
   o_placeid bigint
);

alter index organisation on organisation partition (o_organisationid int (0hexffff00));

create table person (
   p_personid bigint primary key (column),
   p_firstname varchar not null,
   p_lastname varchar not null,
   p_gender varchar not null,
   p_birthday bigint not null,
   p_creationdate bigint not null,
   p_locationip varchar not null,
   p_browserused varchar not null,
   p_placeid bigint,
    p_country int
);

alter index person on person partition (p_personid int (0hexffff00));

create index p_placeid on person (p_placeid) partition (p_placeid int);


create table person_email (
   pe_personid bigint not null,
   pe_email varchar not null,
   primary key (pe_personid, pe_email) column
);

alter index person_email on person_email partition (pe_personid int (0hexffff00));

create table person_tag (
   pt_personid bigint not null,
   pt_tagid bigint not null,
   primary key (pt_personid, pt_tagid) column
);

alter index person_tag on person_tag partition (pt_personid int (0hexffff00));

create table knows (
   k_person1id bigint not null,
   k_person2id bigint not null,
   k_creationdate bigint,
   primary key (k_person1id, k_person2id) column
);

alter index knows on knows partition (k_person1id int (0hexffff00));

create table likes (
   l_personid bigint not null,
   l_postid bigint not null,
   l_creationdate bigint not null,
   primary key (l_postid, l_personid) column
);

alter index likes on likes partition (l_postid int (0hexffff00));

create column index l_personid on likes (l_personid, l_creationdate, l_postid) partition (l_personid int (0hexffff00));

create table person_language (
   plang_personid bigint not null,
   plang_language varchar not null,	
   primary key (plang_personid, plang_language) column
);

alter index person_language on person_language partition (plang_personid int (0hexffff00));

create table person_university (
   pu_personid bigint not null,
   pu_organisationid bigint not null,
   pu_classyear int not null,
   primary key (pu_personid, pu_organisationid) column
);

alter index person_university on person_university partition (pu_personid int (0hexffff00));

create table person_company (
   pc_personid bigint not null,
   pc_organisationid bigint not null,
   pc_workfrom int not null,
   primary key (pc_personid, pc_organisationid) column
);

alter index person_company on person_company partition (pc_personid int (0hexffff00));

create table place (
   pl_placeid bigint primary key (column),
   pl_name varchar not null,
   pl_url varchar not null,
   pl_type varchar not null,
   pl_containerplaceid bigint
);

alter index place on place partition cluster REPLICATED;
create index pl_containerplaceid on place (pl_containerplaceid) partition cluster REPLICATED;

create table post_tag (
   pst_postid bigint not null,
   pst_tagid bigint not null,
   primary key (pst_postid, pst_tagid) column
);

alter index post_tag on post_tag partition (pst_postid int (0hexffff00));

create column index pst_tagid on post_tag (pst_tagid) partition (pst_tagid int (0hexffff00));


create table tagclass (
   tc_tagclassid bigint primary key (column),
   tc_name varchar not null,
   tc_url varchar not null
);

alter index tagclass on tagclass partition cluster REPLICATED;

create table subclass (
   s_subtagclassid bigint not null,
   s_supertagclassid bigint not null,
   primary key (s_subtagclassid, s_supertagclassid) column
);

alter index subclass on subclass partition cluster REPLICATED;

create table tag (
   t_tagid bigint primary key (column),
   t_name varchar not null,
   t_url varchar not null
);

alter index tag on tag partition cluster REPLICATED;

create table tag_tagclass (
   ttc_tagid bigint not null,
   ttc_tagclassid bigint not null,
   primary key (ttc_tagid, ttc_tagclassid) column
);

alter index tag_tagclass on tag_tagclass partition cluster REPLICATED;



create column index k_p2 on knows (k_person2id, k_person1id) partition (k_person2id int (0hexffff00));

create column index ps_creatorid on post (ps_creatorid, ps_creationdate) partition (ps_creatorid int (0hexffff00));
create column not null index ps_p_creatorid on post (ps_p_creatorid) partition (ps_p_creatorid int (0hexffff00));
create column index ps_replyof on post (ps_replyof) partition (ps_replyof int (0hexffff00));
--create column index ps_replyof on post (ps_replyof, ps_creatorid, ps_creationdate) partition (ps_replyof int (0hexffff00));
create column index ps_forumid on post (ps_forumid, ps_creatorid) partition (ps_forumid int (0hexffff00));

create column index fp_personid on forum_person (fp_personid, fp_creationdate, fp_forumid) partition (fp_personid int (0hexffff00));


create table k_weight (kw_p1 bigint, kw_p2 bigint, kw_weight real,
  primary key (kw_p1, kw_p2) not column);
alter index k_weight on k_weight partition (kw_p1 int (0hexffff00));


create table c_sum (cs_p1 bigint, cs_p2 bigint, cs_wnd int, cs_tag bigint, cs_cnt int, 
  primary key (cs_p1, cs_p2, cs_wnd, cs_tag) column);
alter index c_sum on c_sum partition (cs_p1 int (0hexffff00));



create view country as select city.pl_placeid as ctry_city, ctry.pl_name as ctry_name from place city, place ctry where city.pl_containerplaceid = ctry.pl_placeid and ctry.pl_type = 'country';


create table  result_f (r_op varchar, r_sched bigint, r_start bigint, r_duration int, r_stat int);

ft_set_file ('result_f', 'results/LDBC-results_log.csv', delimiter => '|', skip_rows=>1);

create table  snb_result (sq_name varchar, sq_count int, sq_mean float, sq_min float, sq_max float, 
       sq_50 float, sq_90 float, sq_95 float, sq_99 float);

