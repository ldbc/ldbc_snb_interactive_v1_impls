DECLARE @Score bigint;
SET @Score = dbo.CalculateInteractionScore(:person1Id, :person2Id);

INSERT Person_knows_Person ($from_id, $to_id, Person1id, Person2id, weight, creationDate)
VALUES (
    NODE_ID_FROM_PARTS(object_id('Person'), CAST( :person1Id AS BIGINT )),
    NODE_ID_FROM_PARTS(object_id('Person'), CAST( :person2Id AS BIGINT )),
    :person1Id,
    :person2Id,
    @score,
    :creationDate
),
(
    NODE_ID_FROM_PARTS(object_id('Person'), CAST( :person2Id AS BIGINT )),
    NODE_ID_FROM_PARTS(object_id('Person'), CAST( :person1Id AS BIGINT )),
    :person2Id,
    :person1Id,
    @score,
    :creationDate
);
