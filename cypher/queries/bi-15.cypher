// Q15. Social normals
/*
  :param { country: 'Spain' }
*/
MATCH
  (country:Country {name: $country}),
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(somePerson:Person),
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(friendOfSomePersion:Person),
  (somePerson)-[:knows]->(friendOfSomePerson)
RETURN
  count(friendOfSomePerson) as cfosp,
  count(somePerson) AS csp
// TODO
