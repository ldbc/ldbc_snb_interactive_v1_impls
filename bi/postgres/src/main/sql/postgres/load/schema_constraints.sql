ALTER TABLE post ADD PRIMARY KEY (ps_postid);
ALTER TABLE forum ADD PRIMARY KEY (f_forumid);
ALTER TABLE forum_person ADD PRIMARY KEY (fp_forumid, fp_personid);
ALTER TABLE forum_tag ADD PRIMARY KEY (ft_forumid, ft_tagid);
ALTER TABLE organisation ADD PRIMARY KEY (o_organisationid);
ALTER TABLE person ADD PRIMARY KEY (p_personid);
ALTER TABLE person_email ADD PRIMARY KEY (pe_personid, pe_email);
ALTER TABLE person_tag ADD PRIMARY KEY (pt_personid, pt_tagid);
ALTER TABLE knows ADD PRIMARY KEY (k_person1id, k_person2id);
ALTER TABLE likes ADD PRIMARY KEY (l_postid, l_personid);
ALTER TABLE person_language ADD PRIMARY KEY (plang_personid, plang_language);
ALTER TABLE person_university ADD PRIMARY KEY (pu_personid, pu_organisationid);
ALTER TABLE person_company ADD PRIMARY KEY (pc_personid, pc_organisationid);
ALTER TABLE place ADD PRIMARY KEY (pl_placeid);
ALTER TABLE post_tag ADD PRIMARY KEY (pst_postid, pst_tagid);
ALTER TABLE tagclass ADD PRIMARY KEY (tc_tagclassid);
ALTER TABLE subclass ADD PRIMARY KEY (s_subtagclassid, s_supertagclassid);
ALTER TABLE tag ADD PRIMARY KEY (t_tagid);
ALTER TABLE tag_tagclass ADD PRIMARY KEY (ttc_tagid, ttc_tagclassid);

vacuum analyze;
# Additional indexes
# create index p_placeid on person (p_placeid) partition (p_placeid int);
# create column index l_personid on likes (l_postid) partition (l_postid int (0hexffff00));
# create index pl_containerplaceid on place (pl_containerplaceid) partition cluster REPLICATED;
# create column index pst_tagid on post_tag (pst_tagid) partition (pst_tagid int (0hexffff00));