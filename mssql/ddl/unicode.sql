USE ldbc;

SET quoted_identifier ON
-- Person table
UPDATE dbo.person       SET p_firstname = CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, p_firstname ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')
UPDATE dbo.person       SET p_lastname =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, p_lastname  ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Organisation Table
UPDATE dbo.organisation SET o_name     =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, o_name      ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Message Table
UPDATE dbo.message      SET m_content  =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, m_content   ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Tagclass table
UPDATE dbo.tagclass     SET tc_name    =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, tc_name     ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Tag table
UPDATE dbo.tag          SET t_name     =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, t_name      ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Forum table
UPDATE dbo.forum        SET f_title    =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, f_title     ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')

-- Place table
UPDATE dbo.place        SET pl_name    =  CONVERT(XML, CONVERT(NVARCHAR(max), CONVERT(XML, pl_name     ).value('.','varbinary(max)'))).value('.[1]','nvarchar(max)')
