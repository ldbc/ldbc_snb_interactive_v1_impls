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

INSERT Message_hasCreator_Person ($from_id, $to_id)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Message'), CAST( :postId AS BIGINT)),
    NODE_ID_FROM_PARTS(object_id('Person'), CAST( :authorPersonId AS BIGINT))
);
