/* IS3. Friends of a person
\set personId 17592186044461
 */
select person.id, person.firstname, person.lastname, person.creationdate
from Person_knows_Person k, Person
where k.person1id = :personId and k.person2id = Person.id
order by person.creationdate desc, person.id asc;
;
