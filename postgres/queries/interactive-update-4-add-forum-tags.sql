INSERT INTO Forum_hasTag_Tag (
    creationDate
  , ForumId
  , TagId
)
SELECT
    :creationDate
  , :forumId
  , unnest(:tagIds)
;
