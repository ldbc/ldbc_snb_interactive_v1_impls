/* IS1. Profile of a person
\set personId 10995116277794
 */
SELECT p_firstname
     , p_lastname
     , p_birthday
     , p_locationip
     , p_browserused
     , p_placeid
     , p_gender
     , p_creationdate
  FROM person
 WHERE p_personid = :personId;
;
