DELETE FROM Comment_Delete_candidates_unique;
DELETE FROM Post_Delete_candidates_unique;
DELETE FROM Forum_Delete_candidates_unique;

----------------------------------------------------------------------------------------------------
-- DEL1: Remove Person, its personal Forums, and its Message (sub)threads --------------------------
----------------------------------------------------------------------------------------------------
DELETE FROM Person
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person.id
;

DELETE FROM Person_likes_Message
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person_likes_Message.PersonId
;

DELETE FROM Person_workAt_Company
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person_workAt_Company.PersonId
;

DELETE FROM Person_studyAt_University
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person_studyAt_University.PersonId
;

-- treat KNOWS edges as undirected
DELETE FROM Person_knows_Person
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person_knows_Person.Person1Id
   OR Person_Delete_candidates.id = Person_knows_Person.Person2Id
;

DELETE FROM Person_hasInterest_Tag
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Person_hasInterest_Tag.PersonId
;

DELETE FROM Forum_hasMember_Person
USING Person_Delete_candidates
WHERE Person_Delete_candidates.id = Forum_hasMember_Person.PersonId
;

UPDATE Forum
SET ModeratorPersonId = NULL
WHERE Forum.title LIKE 'Group %'
  AND ModeratorPersonId IN (SELECT id FROM Person_Delete_candidates)
;

-- offload cascading Forum deletes to DEL4
INSERT INTO Forum_Delete_candidates
SELECT Person_Delete_candidates.deletionDate AS deletionDate, Forum.id AS id
FROM Person_Delete_candidates
JOIN Forum
  ON Forum.ModeratorPersonId = Person_Delete_candidates.id
WHERE Forum.title LIKE 'Album %'
   OR Forum.title LIKE 'Wall %'
;

-- offload cascading Post deletes to DEL6
INSERT INTO Post_Delete_candidates
SELECT Person_Delete_candidates.deletionDate AS deletionDate, Post_View.id AS id
FROM Person_Delete_candidates
JOIN Post_View
  ON Post_View.CreatorPersonId = Person_Delete_candidates.id
;

-- offload cascading Comment deletes to DEL7
INSERT INTO Comment_Delete_candidates
SELECT Person_Delete_candidates.deletionDate AS deletionDate, Comment_View.id AS id
FROM Person_Delete_candidates
JOIN Comment_View
  ON Comment_View.CreatorPersonId = Person_Delete_candidates.id
;

----------------------------------------------------------------------------------------------------
-- DEL2: Remove Post like --------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
DELETE FROM Person_likes_Message
USING Person_likes_Post_Delete_candidates
WHERE Person_likes_Post_Delete_candidates.src = Person_likes_Message.PersonId
  AND Person_likes_Post_Delete_candidates.trg = Person_likes_Message.MessageId
;

----------------------------------------------------------------------------------------------------
-- DEL3: Remove Comment like -----------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
DELETE FROM Person_likes_Message
USING Person_likes_Comment_Delete_candidates
WHERE Person_likes_Comment_Delete_candidates.src = Person_likes_Message.PersonId
  AND Person_likes_Comment_Delete_candidates.trg = Person_likes_Message.MessageId
;

----------------------------------------------------------------------------------------------------
-- DEL4: Remove Forum and its content --------------------------------------------------------------
----------------------------------------------------------------------------------------------------
INSERT INTO Forum_Delete_candidates_unique
  SELECT min(deletionDate), id
  FROM Forum_Delete_candidates
  GROUP BY id
;

DELETE FROM Forum
USING Forum_Delete_candidates_unique
WHERE Forum.id = Forum_Delete_candidates_unique.id
;

DELETE FROM Forum_hasMember_Person
USING Forum_Delete_candidates_unique
WHERE Forum_Delete_candidates_unique.id = Forum_hasMember_Person.ForumId
;

-- offload cascading Post deletes to DEL6
INSERT INTO Post_Delete_candidates
SELECT Forum_Delete_candidates_unique.deletionDate AS deletionDate, Post_View.id AS id
FROM Post_View
JOIN Forum_Delete_candidates_unique
  ON Forum_Delete_candidates_unique.id = Post_View.ContainerForumId
;

----------------------------------------------------------------------------------------------------
-- DEL5: Remove Forum membership -------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
DELETE FROM Forum_hasMember_Person
USING Forum_hasMember_Person_Delete_candidates
WHERE Forum_hasMember_Person_Delete_candidates.src = Forum_hasMember_Person.ForumId
  AND Forum_hasMember_Person_Delete_candidates.trg = Forum_hasMember_Person.PersonId
;

----------------------------------------------------------------------------------------------------
-- DEL6: Remove Post thread ------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
INSERT INTO Post_Delete_candidates_unique
  SELECT DISTINCT id
  FROM Post_Delete_candidates;

DELETE FROM Message
USING Post_Delete_candidates_unique -- starting from the delete candidate post
WHERE Post_Delete_candidates_unique.id = Message.MessageId
;

DELETE FROM Person_likes_Message
USING Post_Delete_candidates_unique
WHERE Post_Delete_candidates_unique.id = Person_likes_Message.MessageId
;

DELETE FROM Post_hasTag_Tag
USING Post_Delete_candidates_unique
WHERE Post_Delete_candidates_unique.id = Post_hasTag_Tag.PostId
;

-- offload cascading Comment deletes to DEL7
INSERT INTO Comment_Delete_candidates 
SELECT Post_Delete_candidates.deletionDate AS deletionDate, Comment_View.id AS id
FROM Comment_View
JOIN Post_Delete_candidates
  ON Post_Delete_candidates.id = Comment_View.ParentPostId
;

----------------------------------------------------------------------------------------------------
-- DEL7: Remove Comment subthread ------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
INSERT INTO Comment_Delete_candidates_unique
  SELECT DISTINCT id
  FROM Comment_Delete_candidates;

DELETE FROM Message
USING (
  WITH RECURSIVE MessagesToDelete AS (
      SELECT id
      FROM Comment_Delete_candidates_unique -- starting from the delete candidate comments
      UNION
      SELECT ChildComment.MessageId AS id
      FROM MessagesToDelete
      JOIN Message ChildComment
        ON ChildComment.ParentMessageId = MessagesToDelete.id
  )
  SELECT id
  FROM MessagesToDelete
  ) sub
WHERE sub.id = Message.MessageId
;

DELETE FROM Person_likes_Message
USING (
  WITH RECURSIVE MessagesToDelete AS (
      SELECT id
      FROM Comment_Delete_candidates_unique -- starting from the delete candidate comments
      UNION ALL
      SELECT ChildComment.MessageId AS id
      FROM MessagesToDelete
      JOIN Message ChildComment
        ON ChildComment.ParentMessageId = MessagesToDelete.id
  )
  SELECT id
  FROM MessagesToDelete
  ) sub
WHERE sub.id = Person_likes_Message.MessageId
;

DELETE FROM Comment_hasTag_Tag
USING (
  WITH RECURSIVE MessagesToDelete AS (
      SELECT id
      FROM Comment_Delete_candidates_unique -- starting from the delete candidate comments
      UNION ALL
      SELECT ChildComment.MessageId AS id
      FROM MessagesToDelete
      JOIN Message ChildComment
        ON ChildComment.ParentMessageId = MessagesToDelete.id
  )
  SELECT id
  FROM MessagesToDelete
  ) sub
WHERE sub.id = Comment_hasTag_Tag.CommentId
;

----------------------------------------------------------------------------------------------------
-- DEL8: Remove friendship -------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
DELETE FROM Person_knows_Person
USING Person_knows_Person_Delete_candidates
WHERE (Person_knows_Person.Person1Id = Person_knows_Person_Delete_candidates.src AND Person_knows_Person.Person2Id = Person_knows_Person_Delete_candidates.trg)
   OR (Person_knows_Person.Person1Id = Person_knows_Person_Delete_candidates.trg AND Person_knows_Person.Person2Id = Person_knows_Person_Delete_candidates.src)
;
