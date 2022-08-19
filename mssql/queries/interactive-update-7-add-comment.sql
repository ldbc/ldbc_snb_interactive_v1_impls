DECLARE @replyToMessageId bigint;
SET @replyToMessageId = :replyToCommentId + :replyToPostId

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
  , @replyToMessageId
);

DECLARE @ParentPersonId bigint;
SET @ParentPersonId = (SELECT CreatorPersonId FROM Message WHERE MessageId = @replyToMessageId);

UPDATE Person_knows_Person
SET weight = dbo.CalculateInteractionScore(:authorPersonId, @ParentPersonId)
WHERE (person1Id = :authorPersonId AND person2Id = @ParentPersonId )
   OR (person2Id = :authorPersonId AND person1Id = @ParentPersonId);
