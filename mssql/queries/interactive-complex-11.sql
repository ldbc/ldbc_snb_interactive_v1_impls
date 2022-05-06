SELECT TOP(10) p_personid
             , p_firstname
             , p_lastname
             , o_name
             , pc_workfrom
          FROM person
             , person_company
             , organisation
             , place
             , (SELECT k_person2id
                  FROM knows
                 WHERE k_person1id = :personId
                 UNION
                SELECT k2.k_person2id
                  FROM knows k1
                     , knows k2
                 WHERE k1.k_person1id = :personId
                   AND k1.k_person2id = k2.k_person1id
                   AND k2.k_person2id <> :personId
               ) f
         WHERE p_personid = f.k_person2id
           AND p_personid = pc_personid
           AND pc_organisationid = o_organisationid
           AND pc_workfrom < :workFromYear
           AND o_placeid = pl_placeid
           AND pl_name = :countryName
      ORDER BY pc_workfrom, p_personid, o_name DESC
;
