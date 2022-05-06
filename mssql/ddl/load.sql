USE ldbc;
-- Populate forum_tag table
BULK INSERT dbo.forum_tag FROM '/data/dynamic/forum_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_email table
BULK INSERT dbo.person_email FROM '/data/dynamic/person_email_emailaddress_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_tag table
BULK INSERT dbo.person_tag FROM '/data/dynamic/person_hasInterest_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_language table
BULK INSERT dbo.person_language FROM '/data/dynamic/person_speaks_language_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_university table
BULK INSERT dbo.person_university FROM '/data/dynamic/person_studyAt_organisation_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate person_company table
BULK INSERT dbo.person_company FROM '/data/dynamic/person_workAt_organisation_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

-- Populate message_tag table
BULK INSERT dbo.message_tag FROM '/data/dynamic/post_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);
BULK INSERT dbo.message_tag FROM '/data/dynamic/comment_hasTag_tag_0_0.csv' WITH ( FORMAT='CSV', FIELDTERMINATOR='|', ROWTERMINATOR='\n',FIRSTROW=2);

CREATE VIEW country AS
    SELECT city.pl_placeid AS ctry_city, ctry.pl_name AS ctry_name
    FROM place city, place ctry
    WHERE city.pl_containerplaceid = ctry.pl_placeid
      AND ctry.pl_type = 'country';

INSERT INTO message
    SELECT m_messageid, m_ps_imagefile, m_creationdate, m_locationip, m_browserused, m_ps_language, m_content, m_length, m_creatorid, m_locationid, m_ps_forumid, NULL AS m_c_replyof
    FROM dbo.post;

INSERT INTO message
    SELECT m_messageid, NULL, m_creationdate, m_locationip, m_browserused, NULL, m_content, m_length, m_creatorid, m_locationid, NULl, coalesce(m_replyof_post, m_replyof_comment)
    FROM dbo.comment;
