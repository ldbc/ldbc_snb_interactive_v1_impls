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
SELECT
    :creationDate
  , :commentId::bigint
  , parent.RootPostId
  , parent.RootPostLanguage
  , :content::text
  , NULL
  , :locationIP::text
  , :browserUsed::text
  , :length
  , :authorPersonId -- CreatorPersonId
  , NULL
  , :countryId -- LocationCountryId
  , :replyToCommentId + :replyToPostId
FROM
  Message parent
WHERE
  parent.id = (:replyToCommentId + :replyToPostId)
;
