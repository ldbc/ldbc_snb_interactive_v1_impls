USE ldbc;

-- Populate forum table
BULK INSERT dbo.forum FROM '/data/dynamic/forum_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate forum_person table
BULK INSERT dbo.forum_person FROM '/data/dynamic/forum_hasMember_person_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate forum_tag table
BULK INSERT dbo.forum_tag FROM '/data/dynamic/forum_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate organisation table
BULK INSERT dbo.organisation FROM '/data/static/organisation_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person table
BULK INSERT dbo.person_temp FROM '/data/dynamic/person_0_0.csv_encoded.csv' WITH ( DATAFILETYPE = 'char', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
INSERT INTO dbo.person ( p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid) SELECT p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid FROM dbo.person_temp;

drop table dbo.person_temp;

-- Populate person_email table
BULK INSERT dbo.person_email FROM '/data/dynamic/person_email_emailaddress_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_tag table
BULK INSERT dbo.person_tag FROM '/data/dynamic/person_hasInterest_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate knows table
BULK INSERT dbo.knows_temp FROM '/data/dynamic/person_knows_person_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
INSERT INTO dbo.knows_temp ( k_person1id, k_person2id, k_creationdate) SELECT k_person2id, k_person1id, k_creationdate FROM dbo.knows_temp;

-- Populate knows edge table (SQL Graph)
INSERT INTO dbo.knows($from_id, $to_id, k_person1id, k_person2id, k_creationdate)
SELECT 
    (SELECT $node_id FROM dbo.person WHERE k_person1id = p_personid),
    (SELECT $node_id FROM dbo.person WHERE k_person2id = p_personid),
k_person1id, k_person2id, k_creationdate FROM dbo.knows_temp;

drop table dbo.knows_temp;

-- Populate likes table
BULK INSERT dbo.likes FROM '/data/dynamic/person_likes_post_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
BULK INSERT dbo.likes FROM '/data/dynamic/person_likes_comment_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_language table
BULK INSERT dbo.person_language FROM '/data/dynamic/person_speaks_language_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_university table
BULK INSERT dbo.person_university FROM '/data/dynamic/person_studyAt_organisation_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_company table
BULK INSERT dbo.person_company FROM '/data/dynamic/person_workAt_organisation_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate place table
BULK INSERT dbo.place FROM '/data/static/place_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate message_tag table
BULK INSERT dbo.message_tag FROM '/data/dynamic/post_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
BULK INSERT dbo.message_tag FROM '/data/dynamic/comment_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate tagclass table
BULK INSERT dbo.tagclass FROM '/data/static/tagclass_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate tag table
BULK INSERT dbo.tag FROM '/data/static/tag_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);


CREATE VIEW country AS
    SELECT city.pl_placeid AS ctry_city, ctry.pl_name AS ctry_name
    FROM place city, place ctry
    WHERE city.pl_containerplaceid = ctry.pl_placeid
      AND ctry.pl_type = 'country';


BULK INSERT dbo.post FROM '/data/dynamic/post_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
BULK INSERT dbo.comment FROM '/data/dynamic/comment_0_0.csv_encoded.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);


INSERT INTO message
    SELECT m_messageid, m_ps_imagefile, m_creationdate, m_locationip, m_browserused, m_ps_language, m_content, m_length, m_creatorid, m_locationid, m_ps_forumid, NULL AS m_c_replyof
    FROM dbo.post;

INSERT INTO message
    SELECT m_messageid, NULL, m_creationdate, m_locationip, m_browserused, NULL, m_content, m_length, m_creatorid, m_locationid, NULl, coalesce(m_replyof_post, m_replyof_comment)
    FROM dbo.comment;
