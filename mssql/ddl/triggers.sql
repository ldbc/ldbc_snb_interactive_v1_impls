USE ldbc;

CREATE OR ALTER TRIGGER TR_DEL_Message ON Message
FOR DELETE 
AS
BEGIN

  UPDATE Person_knows_Person
     SET weight = weight - 1
     FROM (
                SELECT Person1Id, Person2Id
                  FROM Person_knows_Person 
            INNER JOIN (SELECT CreatorPersonId FROM DELETED) m1 on Person1Id = m1.CreatorPersonId
            INNER JOIN (SELECT CreatorPersonId FROM Message WHERE ParentMessageId IN (SELECT MessageId FROM DELETED)) m2 on Person2Id = m2.CreatorPersonId
     ) repliers
    WHERE Person_knows_Person.Person1Id = repliers.Person1Id
     AND Person_knows_Person.Person2Id = repliers.Person2Id

    DELETE FROM Message WHERE ParentMessageId IN (  SELECT MessageId FROM DELETED) -- Delete the comments
END;

CREATE OR ALTER TRIGGER TR_DEL_Forum ON Forum
AFTER DELETE 
AS
BEGIN

  DELETE FROM Message WHERE ContainerForumId IN (  SELECT id FROM DELETED) -- Delete the post
  DELETE FROM Forum_hasTag_Tag WHERE ForumId IN (  SELECT id FROM DELETED)
  DELETE FROM Forum_hasMember_Person WHERE ForumId IN (  SELECT id FROM DELETED)
END;

CREATE OR ALTER TRIGGER TR_DEL_Person_edges ON Person
AFTER DELETE 
AS
BEGIN

  DELETE FROM Person_hasInterest_Tag WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_studyAt_University WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_workAt_Company WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Forum_hasMember_Person WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Message WHERE CreatorPersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_knows_Person WHERE Person1Id IN ( SELECT PersonId FROM DELETED)
  DELETE FROM Person_knows_Person WHERE Person2Id IN ( SELECT PersonId FROM DELETED)
END;
