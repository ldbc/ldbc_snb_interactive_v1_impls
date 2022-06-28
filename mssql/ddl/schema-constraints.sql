-- add PKs
USE ldbc;
ALTER TABLE Message ADD PRIMARY KEY (messageid);
ALTER TABLE Forum ADD PRIMARY KEY (id);
ALTER TABLE Forum_hasMember_Person ADD PRIMARY KEY (ForumId, PersonId);
ALTER TABLE Forum_hasTag_Tag ADD PRIMARY KEY (ForumId, TagId);
ALTER TABLE Person ADD PRIMARY KEY (id);
-- ALTER TABLE person_email ADD PRIMARY KEY (pe_personid, pe_email);
ALTER TABLE Person_hasInterest_Tag ADD PRIMARY KEY (PersonId, TagId);
ALTER TABLE Person_knows_Person ADD PRIMARY KEY (Person1Id, Person2Id);
ALTER TABLE Person_likes_Message ADD PRIMARY KEY (PersonId, MessageId);
-- ALTER TABLE person_language ADD PRIMARY KEY (plang_personid, plang_language);
ALTER TABLE Person_studyAt_University ADD PRIMARY KEY (PersonId, UniversityId);
ALTER TABLE Person_workAt_Company ADD PRIMARY KEY (PersonId, CompanyId);
ALTER TABLE Message_hasTag_Tag ADD PRIMARY KEY (MessageId, TagId);