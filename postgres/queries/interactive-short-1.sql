/* IS1. Profile of a person
\set personId 17592186044461
 */
select firstname, lastname, birthday, locationip, browserused, LocationCityId, gender, creationdate
from person
where id = :personId;
;
