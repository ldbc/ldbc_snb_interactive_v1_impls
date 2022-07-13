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
);
