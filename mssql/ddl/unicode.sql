USE ldbc;

SET quoted_identifier ON
-- Person table
UPDATE dbo.person SET p_firstname = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, p_firstname).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
UPDATE dbo.person SET p_lastname = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, p_lastname).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
UPDATE dbo.person SET p_creationdate = Cast(Reverse(Stuff(Reverse(p_creationdate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Organisation Table
UPDATE dbo.organisation SET o_name = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, o_name).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
-- Message Table
UPDATE dbo.message SET m_content = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, m_content).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
UPDATE dbo.message SET m_creationdate = Cast(Reverse(Stuff(Reverse(m_creationdate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Tagclass table
UPDATE dbo.tagclass SET tc_name = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, tc_name).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
-- Tag table
UPDATE dbo.tag SET t_name = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, t_name).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
-- Forum Table
UPDATE dbo.forum SET f_title = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, f_title).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));
UPDATE dbo.forum SET f_creationdate = Cast(Reverse(Stuff(Reverse(f_creationdate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Place Table
UPDATE dbo.place SET pl_name = CONVERT(NVARCHAR(500), CAST(CONVERT(XML, '<?xml version="1.0" encoding="UTF-8"?>' + CONVERT(VARCHAR(500), CONVERT(XML, pl_name).value('.','varbinary(max)'))) as XML).value('.[1]','nvarchar(max)'));

-- Datetimeoffset loading
UPDATE dbo.forum_person SET fp_joindate = Cast(Reverse(Stuff(Reverse(fp_joindate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Datetimeoffset loading
UPDATE dbo.knows SET k_creationdate = Cast(Reverse(Stuff(Reverse(k_creationdate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Datetimeoffset loading
UPDATE dbo.likes SET l_creationdate = Cast(Reverse(Stuff(Reverse(l_creationdate), 3, 0, ':')) AS DATETIMEOFFSET(7)) AT TIME ZONE 'GMT Standard Time'

-- Datetimeoffset loading
ALTER TABLE dbo.person
ALTER COLUMN p_creationdate datetimeoffset;

-- Datetimeoffset loading
ALTER TABLE dbo.message
ALTER COLUMN m_creationdate datetimeoffset;

-- Datetimeoffset loading
ALTER TABLE dbo.forum
ALTER COLUMN f_creationdate datetimeoffset;

-- Datetimeoffset loading
ALTER TABLE dbo.forum_person
ALTER COLUMN fp_joindate datetimeoffset;

-- Datetimeoffset loading
ALTER TABLE dbo.knows
ALTER COLUMN k_creationdate datetimeoffset;

-- Datetimeoffset loading
ALTER TABLE dbo.likes
ALTER COLUMN l_creationdate datetimeoffset;
