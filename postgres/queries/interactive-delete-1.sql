/* DEL1. Remove person and its personal forums and message (sub)threads
\set personId 8796093023099
 */
DELETE FROM Person
WHERE id = :personId
;
