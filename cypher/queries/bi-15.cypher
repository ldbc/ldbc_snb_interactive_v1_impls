// Social normals
MATCH
  (country:Country),
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(somePerson:Person),
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(friendOfSomePersion:Person),
  (somePerson)-[:knows]->(friendOfSomePerson)
RETURN count(friendOfSomePerson) as cfosp, count(somePerson) AS csp
