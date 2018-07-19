insert into likes (
    l_personid
  , l_messageid
  , l_creationdate
)
values
(
    :personId
  , :postId
  , :creationDate
);
