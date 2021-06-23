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
  , CASE :imageFile WHEN '' THEN NULL ELSE :imageFile END
  , :creationDate
  , :locationIP
  , :browserUsed
  , :language
  , CASE :content WHEN '' THEN NULL ELSE :content END
  , :length
  , :authorPersonId
  , :countryId
  , :forumId
);
