SELECT TOP(20) id
             , p_lastname
             , min (dist) as dist
             , p_birthday
             , p_creationdate
             , p_gender
             , p_browserused
             , p_locationip
             , (SELECT string_agg(pe_email, ';') 
                  FROM person_email 
                 WHERE pe_personid = id
                 GROUP BY pe_personid
            ) 
            AS emails
             , (SELECT string_agg(plang_language, ';')
                 FROM person_language
                WHERE plang_personid = id
                GROUP BY plang_personid) 
            AS languages
             , p1.pl_name
             , (SELECT string_agg(CONCAT(o2.o_name , '|' , CONVERT(VARCHAR(max), pu_classyear), '|' , p2.pl_name), ';')
                  FROM person_university
                     , organisation o2
                     , place p2
                 WHERE pu_personid = id
                   AND pu_organisationid = o2.o_organisationid
                   AND o2.o_placeid = p2.pl_placeid
                 GROUP BY pu_personid) AS university
             , (SELECT string_agg(CONCAT(o3.o_name , '|' , CONVERT(VARCHAR(max), pc_workfrom), '|' , p3.pl_name), ';')
                  FROM person_company, organisation o3, place p3
                 WHERE pc_personid = id
                   AND pc_organisationid = o3.o_organisationid
                   AND o3.o_placeid = p3.pl_placeid
                 GROUP BY pc_personid) 
            AS company
  FROM (SELECT k_person2id AS id
             , 1 AS dist FROM knows
             , person 
         WHERE k_person1id = :personId
           AND p_personid = k_person2id 
           AND p_firstname = :firstName
         UNION ALL
        SELECT b.k_person2id AS id
             , 2 AS dist 
          FROM knows a
             , knows b
             , person
         WHERE a.k_person1id = :personId
           AND b.k_person1id = a.k_person2id
           AND p_personid = b.k_person2id
           AND p_firstname = :firstName
           AND p_personid != :personId -- excluding start person
         UNION ALL
        SELECT c.k_person2id AS id
             , 3 AS dist
          FROM knows a
             , knows b
             , knows c
             , person
         WHERE a.k_person1id = :personId
           AND b.k_person1id = a.k_person2id
           AND b.k_person2id = c.k_person1id
           AND p_personid = c.k_person2id
           AND p_firstname = :firstName
           AND p_personid != :personId -- excluding start person
  ) tmp, person, place p1
  WHERE p_personid = id
    AND p_placeid = p1.pl_placeid
  GROUP BY id, p_lastname, p_birthday, p_creationdate, p_gender, p_browserused, p_locationip, p1.pl_name
  ORDER BY dist, p_lastname, id
;
