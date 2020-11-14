insert into forum (
    f_creationdate
  , f_forumid
  , f_title
  , f_moderatorid
)
values
(
    :creationDate
  , :forumId
  , :forumTitle
  , :moderatorPersonId
);
