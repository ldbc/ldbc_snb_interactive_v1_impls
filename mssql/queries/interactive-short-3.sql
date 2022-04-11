/* IS3. Friends of a person
\set personId 10995116277794
 */
  SELECT p_personid, p_firstname, p_lastname, k_creationdate
    FROM knows, person
   WHERE k_person1id = :personId 
     AND k_person2id = p_personid
ORDER BY k_creationdate DESC,
         p_personid ASC;
;
