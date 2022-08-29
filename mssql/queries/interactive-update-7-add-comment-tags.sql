IF (SELECT $NODE_ID FROM Tag WHERE id = :tagId) IS NOT NULL
BEGIN
    INSERT INTO Message_hasTag_Tag (
        $from_id, $to_id, 
        creationDate
    , MessageId
    , TagId
    )
    SELECT
        (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
        (SELECT $NODE_ID FROM Tag WHERE id = :tagId),
        :creationDate
    , :commentId
    , :tagId
    
END;
