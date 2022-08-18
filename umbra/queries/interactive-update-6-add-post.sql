INSERT INTO Message (
    creationDate
  , id
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
