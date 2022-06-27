INSERT INTO Message_hasTag_Tag (
    creationDate
  , MessageId
  , TagId
)
SELECT :creationDate, :postId, unnest(:tagIds);
