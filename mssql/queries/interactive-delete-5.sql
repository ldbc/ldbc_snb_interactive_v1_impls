-- Remove forum membership
DELETE FROM Forum_hasMember_Person
WHERE ForumId = :forumId
  AND PersonId = :personId