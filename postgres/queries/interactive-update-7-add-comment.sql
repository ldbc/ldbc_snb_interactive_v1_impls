INSERT INTO Message (
    creationDate
  , MessageId
  , RootPostId
  , RootPostLanguage
  , content
  , imageFile
  , locationIP
  , browserUsed
  , length
  , CreatorPersonId
  , ContainerForumId
  , LocationCountryId
  , ParentMessageId
  , ParentPostId
  , ParentCommentId
  , type
)
VALUES
(
    :creationDate
  , :commentId
  , NULL
  , NULL
  , :content
  , NULL
  , :locationIP
  , :browserUsed
  , :length
  , :authorPersonId -- CreatorPersonId
  , NULL
  , :countryId -- LocationCountryId
  , :replyToCommentId + :replyToPostId -- ParentMessageId = ParentPostId, ParentCommentId
  , NULL
  , NULL
  , ''
);
