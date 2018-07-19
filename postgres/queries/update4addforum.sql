insert into forum (
    f_forumid
  , f_title
  , f_creationdate
  , f_moderatorid
)
values
(
    :forumId
  , :forumTitle
  , :creationDate
  , :moderatorPersonId
);
