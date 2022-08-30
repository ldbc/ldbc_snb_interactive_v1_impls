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

INSERT INTO Message_hasCreator_Person($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :postId),
    (SELECT $NODE_ID FROM Person WHERE personId = :authorPersonId)
);