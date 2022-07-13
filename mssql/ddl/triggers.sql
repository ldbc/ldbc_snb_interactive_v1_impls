USE ldbc;

CREATE TRIGGER TR_DEL_Message ON Message
AFTER DELETE 
AS
BEGIN
  DELETE FROM Message WHERE ParentMessageId IN (  SELECT MessageId FROM DELETED)
  DELETE FROM Message_hasTag_Tag WHERE MessageId IN (  SELECT MessageId FROM DELETED)
  DELETE FROM Person_likes_Message WHERE MessageId IN (  SELECT MessageId FROM DELETED)
END;

CREATE TRIGGER TR_DEL_Forum ON Forum
AFTER DELETE 
AS
BEGIN
  DELETE FROM Message WHERE ContainerForumId IN (  SELECT id FROM DELETED)
  DELETE FROM Forum_hasTag_Tag WHERE ForumId IN (  SELECT id FROM DELETED)
  DELETE FROM Forum_hasMember_Person WHERE ForumId IN (  SELECT id FROM DELETED)
END;

CREATE TRIGGER TR_DEL_Person_edges ON Person
AFTER DELETE 
AS
BEGIN
  DELETE FROM Person_knows_Person WHERE Person1Id IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_knows_Person WHERE Person2Id IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_likes_Message WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_hasInterest_Tag WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_studyAt_University WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Person_workAt_Company WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Forum_hasMember_Person WHERE PersonId IN (  SELECT PersonId FROM DELETED)
  DELETE FROM Message WHERE CreatorPersonId IN (  SELECT PersonId FROM DELETED)
END;
