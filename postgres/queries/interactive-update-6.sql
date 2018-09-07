insert into message (
    -- only post-related fields are filled explicitly
    m_messageid
  , m_ps_imagefile
  , m_creationdate
  , m_locationip
  , m_browserused
  , m_ps_language
  , m_content
  , m_length
  , m_creatorid
  , m_locationid
  , m_ps_forumid
)
values
(
    :postId
  , :imageFile
  , :creationDate
  , :locationIP
  , :browserUsed
  , :language
  , :content
  , :length
  , :authorPersonId
  , :countryId
  , :forumId
);
