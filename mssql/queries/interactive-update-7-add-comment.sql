INSERT INTO Message (
    creationDate
  , MessageId
  , language
  , content
  , imageFile
  , locationIP
  , browserUsed
  , length
  , CreatorPersonId
  , ContainerForumId
  , LocationCountryId
  , ParentMessageId
)
VALUES
(
    :creationDate
  , :commentId
  , NULL
  , :content
  , NULL
  , :locationIP
  , :browserUsed
  , :length
  , :authorPersonId -- CreatorPersonId
  , NULL
  , :countryId -- LocationCountryId
  , :replyToCommentId + :replyToPostId
);

INSERT INTO Message_replyOf_Message($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    (SELECT $NODE_ID FROM Message WHERE MessageId = :replyToCommentId + :replyToPostId)
);
