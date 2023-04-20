INSERT INTO Message (
    creationDate
  , id
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
)
VALUES
(
    :creationDate
  , :postId
  , :postId
  , :language::text
  , CASE :content WHEN '' THEN NULL ELSE :content::text END
  , CASE :imageFile WHEN '' THEN NULL ELSE :imageFile::text END
  , :locationIP::text
  , :browserUsed::text
  , :length
  , :authorPersonId -- CreatorPersonId
  , :forumId
  , :countryId -- LocationCountryId
  , NULL
);
