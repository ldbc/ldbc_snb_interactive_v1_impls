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
  , :postId
  , NULL
  , :language
  , CASE :content WHEN '' THEN NULL ELSE :content END
  , CASE :imageFile WHEN '' THEN NULL ELSE :imageFile END
  , :locationIP
  , :browserUsed
  , :length
  , :authorPersonId -- CreatorPersonId
  , :forumId
  , :countryId -- LocationCountryId
  , NULL
  , NULL
  , NULL
  , ''
);
