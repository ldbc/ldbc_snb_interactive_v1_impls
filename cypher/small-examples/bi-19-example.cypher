CREATE
  // tag classes, tags and forums
  (tagClass1:TagClass {name: 'MusicalArtist'}),
  (tagClass2:TagClass {name: 'OfficeHolder'}),
  (tag1:Tag {name: 'Elvis Presley'}),
  (tag2:Tag {name: 'Mr. Office'}),
  (forum1:Forum {name: 'Presley fan club'}),
  (forum2:Forum {name: 'Long live the closed office'}),
  // hasTag and hasType relationships
  (forum1)-[:hasTag]->(tag1)-[:hasType]->(tagClass1),
  (forum2)-[:hasTag]->(tag2)-[:hasType]->(tagClass2),
  // persons
  (person1:Person {id: 1, birthday: '1990-01-01'}),
  (person2:Person {id: 2, birthday: '1989-01-01'}),
  (person3:Person {id: 3, birthday: '1989-01-01'}),
  (person4:Person {id: 4, birthday: '1989-01-01'}),
  // knows relationships: person1 and persons 3/4 are strangers
  (person1)-[:knows]->(person2),
  (person2)-[:knows]->(person3),
  (person3)-[:knows]->(person4),
  // memberships
  (forum1)-[:hasMember]->(person3)<-[:hasMember]-(forum2),
  (forum1)-[:hasMember]->(person4)<-[:hasMember]-(forum2),
  // interactions
  (person1)<-[:hasCreator]-(:Comment)-[:replyOf]->(:Message)-[:hasCreator]->(person3),
  (person3)<-[:hasCreator]-(:Comment)-[:replyOf]->(:Message)-[:hasCreator]->(person1),
  (person4)<-[:hasCreator]-(:Comment)-[:replyOf]->(:Message)-[:hasCreator]->(person1)
