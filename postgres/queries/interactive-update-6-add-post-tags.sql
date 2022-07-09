INSERT INTO Message_hasTag_Tag (
    creationDate
  , id
  , TagId
)
SELECT
    :creationDate
  , :postId
  , unnest(:tagIds)
;
