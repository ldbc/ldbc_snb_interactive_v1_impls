INSERT INTO forum (
    creationDate
  , id
  , title
  , ModeratorPersonId
)
VALUES
(
    :creationDate
  , :forumId
  , :forumTitle::text
  , :moderatorPersonId
);
