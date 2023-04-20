USE ldbc;

CREATE OR ALTER TRIGGER TR_DEL_Message ON Message
FOR DELETE 
AS
BEGIN
    UPDATE Person_knows_Person
        SET weight = weight - 1
        FROM (
            SELECT m1.CreatorPersonId AS Person2Id, m2.CreatorPersonId AS Person1Id
              FROM Message m1, Message m2, Message_replyOf_Message
             WHERE MATCH(m1-(Message_replyOf_Message)->m2)
               AND m2.MessageId IN ( SELECT MessageId FROM DELETED )
        ) repliers
        WHERE Person_knows_Person.Person1Id = repliers.Person1Id
          AND Person_knows_Person.Person2Id = repliers.Person2Id
           OR Person_knows_Person.Person1Id = repliers.Person2Id
          AND Person_knows_Person.Person2Id = repliers.Person1Id

    DELETE FROM Message WHERE ParentMessageId IN ( SELECT MessageId FROM DELETED)
    DELETE FROM Person_likes_Message WHERE MessageId IN ( SELECT MessageId FROM DELETED)
END;

CREATE OR ALTER TRIGGER TR_DEL_Forum ON Forum
AFTER DELETE 
AS
BEGIN
  DELETE FROM Message                   WHERE ContainerForumId  IN ( SELECT id FROM DELETED )
  DELETE FROM Forum_hasTag_Tag          WHERE ForumId           IN ( SELECT id FROM DELETED )
  DELETE FROM Forum_hasMember_Person    WHERE ForumId           IN ( SELECT id FROM DELETED )
END;

CREATE OR ALTER TRIGGER TR_DEL_Person_edges ON Person
AFTER DELETE 
AS
BEGIN
  DELETE FROM Person_hasInterest_Tag    WHERE PersonId          IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Person_studyAt_University WHERE PersonId          IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Person_likes_Message      WHERE PersonId          IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Person_knows_Person       WHERE Person1Id         IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Person_knows_Person       WHERE Person2Id         IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Person_workAt_Company     WHERE PersonId          IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Forum_hasMember_Person    WHERE PersonId          IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Forum                     WHERE ModeratorPersonId IN ( SELECT PersonId FROM DELETED )
  DELETE FROM Message                   WHERE CreatorPersonId   IN ( SELECT PersonId FROM DELETED )
END;
