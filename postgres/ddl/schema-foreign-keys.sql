-- static nodes
ALTER TABLE City ADD FOREIGN KEY (PartOfCountryId) REFERENCES Country(Id);
ALTER TABLE Company ADD FOREIGN KEY (LocationPlaceId) REFERENCES Country(id);
ALTER TABLE Tag ADD FOREIGN KEY (TypeTagClassId) REFERENCES TagClass(id);
ALTER TABLE TagClass ADD FOREIGN KEY (SubclassOfTagClassId) REFERENCES TagClass(id);
ALTER TABLE University ADD FOREIGN KEY (LocationPlaceId) REFERENCES City(id);

-- static endpoints (never deleted)
ALTER TABLE Person_hasInterest_Tag ADD FOREIGN KEY (TagId) REFERENCES Tag(id);
ALTER TABLE Person_studyAt_University ADD FOREIGN KEY (UniversityId) REFERENCES University(Id);
ALTER TABLE Person_workAt_Company ADD FOREIGN KEY (CompanyId) REFERENCES Company(id);
ALTER TABLE Person ADD FOREIGN KEY (LocationCityId) REFERENCES City(id);
ALTER TABLE Forum_hasTag_Tag ADD FOREIGN KEY (TagId) REFERENCES Tag(id);
ALTER TABLE Message ADD FOREIGN KEY (LocationCountryId) REFERENCES place;
ALTER TABLE Message_hasTag_Tag ADD FOREIGN KEY (TagId) REFERENCES Tag(id);

-- node should not be deleted upon deletion of the endpoint
-- can be null, so not a valid FK!
-- ALTER TABLE Forum ADD FOREIGN KEY (ModeratorPersonId) REFERENCES Person(id);

-- dynamic endpoints (edge should be deleted when the endpoint is deleted)
ALTER TABLE Forum_hasMember_Person ADD FOREIGN KEY (ForumId) REFERENCES Forum(id) ON DELETE CASCADE;
ALTER TABLE Forum_hasMember_Person ADD FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Forum_hasTag_Tag ADD FOREIGN KEY (ForumId) REFERENCES Forum(id) ON DELETE CASCADE;

ALTER TABLE Message ADD FOREIGN KEY (CreatorPersonId) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Message ADD FOREIGN KEY (ContainerForumId) REFERENCES Forum(id) ON DELETE CASCADE;
ALTER TABLE Message ADD FOREIGN KEY (ParentMessageId) REFERENCES Message(id) ON DELETE CASCADE;
ALTER TABLE Message_hasTag_Tag ADD FOREIGN KEY (id) REFERENCES Message(id) ON DELETE CASCADE;

ALTER TABLE Person_knows_Person ADD FOREIGN KEY (Person1Id) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Person_knows_Person ADD FOREIGN KEY (Person2Id) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Person_likes_Message ADD FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Person_likes_Message ADD FOREIGN KEY (id) REFERENCES Message(id) ON DELETE CASCADE;

ALTER TABLE Person_hasInterest_Tag ADD FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Person_studyAt_University ADD FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE CASCADE;
ALTER TABLE Person_workAt_Company ADD FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE CASCADE;
