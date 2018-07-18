/* Q2. Top tags for country, age, gender, time
\set startDate '\'2010-01-01T00:00:00.000+00:00\''::timestamp
\set endDate   '\'2010-11-08T00:00:00.000+00:00\''::timestamp
\set country1  '\'Ethiopia\''
\set country2  '\'Belarus\''
 */
SELECT countryName
     , messageMonth
     , personGender
     , ageGroup
     , tagName
     , messageCount
  FROM (
    SELECT co.pl_name AS countryName
        , extract(MONTH FROM p.m_creationdate) as messageMonth
        , cr.p_gender AS personGender
        , CASE -- ugly hack because I was unable to figure out how to cast_int/floor an expression
            /*
  -- the youngest person was born in 1980, so should be of age 33 in 2013.
  -- Using 30 levels os CASE-WHEN lead to the exception during query compilation
java.lang.StackOverflowError
	at ddbt.codegen.LMSGen.expr(LMSGen.scala:109)
	at ddbt.codegen.LMSGen$$anonfun$expr$11.apply(LMSGen.scala:106)
	at ddbt.codegen.LMSGen$$anonfun$expr$11.apply(LMSGen.scala:106)
	at ddbt.codegen.LMSGen.expr(LMSGen.scala:41)
            */
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >= 10 THEN 10
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  9 THEN  9
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  8 THEN  8
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  7 THEN  7
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  6 THEN  6
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  5 THEN  5
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  4 THEN  4
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  3 THEN  3
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  2 THEN  2
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  1 THEN  1
            WHEN (2013 - extract(YEAR FROM cr.p_birthday))/5 >=  0 THEN  0
          END AS ageGroup
        , t.t_name AS tagName
        , count(*) AS messageCount
      FROM message p
        , message_tag pt
        , tag t
        , person cr -- creator
        , place  ci  -- city
        , place  co  -- country
    WHERE 1=1
        -- join
      AND p.m_messageid = pt.mt_messageid
      AND pt.mt_tagid = t.t_tagid
      AND p.m_creatorid = cr.p_personid
      AND cr.p_placeid = ci.pl_placeid
      AND ci.pl_containerplaceid = co.pl_placeid
        -- filter
      AND (co.pl_name = 'Ethiopia' OR co.pl_name = 'Belarus') -- FIXME:param country1, country2
      AND p.m_creationdate BETWEEN DATE('2010-01-01') AND DATE('2010-11-08') -- FIXME:param startDate, endDate
    GROUP BY co.pl_name, messageMonth, cr.p_gender, t.t_name, ageGroup
     ) res
 WHERE res.messageCount > 100
;
