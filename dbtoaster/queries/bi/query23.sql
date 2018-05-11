/* Q23. Holiday destinations
\set country  '\'Belarus\''
 */
SELECT count(*) AS messageCount
     , dest.pl_name AS destinationName
     , extract(MONTH FROM m.ps_creationdate) AS month
  FROM place pco -- person country
     , place pci -- person city
     , person p
     , post m
     , place dest
 WHERE 1=1
    -- join
   AND pco.pl_placeid = pci.pl_containerplaceid
   AND pci.pl_placeid = p.p_placeid
   AND p.p_personid = m.ps_creatorid
   AND m.ps_locationid = dest.pl_placeid
    -- filter
   AND pco.pl_name = 'Belarus' -- FIXME:param :country
   AND m.ps_locationid != pco.pl_placeid
 GROUP BY dest.pl_name, month
 --ORDER BY messageCount DESC, dest.pl_name, month
 --LIMIT 100
;
