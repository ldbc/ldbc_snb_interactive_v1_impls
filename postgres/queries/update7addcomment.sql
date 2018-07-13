insert into post values
(
    :commentId,
    NULL,
    :creationDate,
    :locationIp,
    :browserUsed,
    NULL,
    :content,
    :length,
    :authorPersonId,
    NULL,
    :countryId,
    NULL,
    :replyToCommentId + :replyToPostId + 1 -- replyToCommentId is -1 if the message is a reply to a post and vica versa (see spec)
);
