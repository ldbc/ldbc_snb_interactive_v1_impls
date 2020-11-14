insert into message_tag (
    mt_creationdate
  , mt_messageid
  , mt_tagid
)
values
(
    :creationDate
  , :postId
  , :tagId
);
