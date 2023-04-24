// Q2. Recent messages by your friends
/*
:params { personId: 10995116278009, maxDate: 1287230400000 }
*/
MATCH (:Person {id: $personId })-[:KNOWS]-(friend:Person)<-[:HAS_CREATOR]-(message:Message)
    WHERE message.creationDate < $maxDate
    RETURN
        friend.id AS personId,
        friend.firstName AS personFirstName,
        friend.lastName AS personLastName,
        message.id AS postOrCommentId,
        coalesce(message.content,message.imageFile) AS postOrCommentContent,
        message.creationDate AS postOrCommentCreationDate
    ORDER BY
        postOrCommentCreationDate DESC,
        toInteger(postOrCommentId) ASC
    LIMIT 20
