INCLUDE 'schema.sql';


/*
*/

SELECT count(*) AS message_cnt
  FROM message
;

SELECT COUNT(*) AS forum_cnt
  FROM forum
;

SELECT COUNT(*) AS forum_person_cnt
  FROM forum_person
;

SELECT COUNT(*) AS forum_tag_cnt
  FROM forum_tag
;

SELECT COUNT(*) AS org_cnt
   FROM organisation
;

SELECT COUNT(*) AS person_cnt
   FROM person
;

SELECT COUNT(*) AS person_email_cnt
   FROM person_email
;

SELECT COUNT(*) AS person_tag_cnt
   FROM person_tag
;

SELECT COUNT(*) AS knows_cnt
   FROM knows
;

SELECT COUNT(*) AS likes_cnt
   FROM likes
;

SELECT COUNT(*) AS person_language_cnt
   FROM person_language
;

SELECT COUNT(*) AS person_university_cnt
   FROM person_university
;

SELECT COUNT(*) AS person_company_cnt
   FROM person_company
;

SELECT COUNT(*) AS place_cnt
   FROM place
;

SELECT COUNT(*) AS message_tag_cnt
   FROM message_tag
;

SELECT COUNT(*) AS tag_cnt
   FROM tag
;

SELECT COUNT(*) AS tagclass_cnt
   FROM tagclass
;

