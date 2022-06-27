-- INSERT INTO Forum_hasTag_Tag (
--     creationDate
--   , ForumId
--   , TagId
-- )
-- VALUES
-- (
--     :creationDate
--   , :forumId
--   , :tagId
-- );

INSERT INTO Forum_hasTag_Tag (
    creationDate
  , ForumId
  , TagId
)
SELECT :creationDate, :forumId, unnest(:tagIds);
