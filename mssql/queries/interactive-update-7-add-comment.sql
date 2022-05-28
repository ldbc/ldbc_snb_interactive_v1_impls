insert into message (
    -- only comment-related fields are filled explicitly
    m_messageid
  , m_creationdate
  , m_locationip
  , m_browserused
  , m_content
  , m_length
  , m_creatorid
  , m_locationid
  , m_c_replyof
)
values
(
    :commentId
  , :creationDate
  , :locationIP
  , :browserUsed
  , :content
  , :length
  , :authorPersonId
  , :countryId
  , :replyToCommentId + :replyToPostId + 1 -- replyToCommentId is -1 if the message is a reply to a post and vica versa (see spec)
);
