select COALESCE(m_ps_imagefile,'')||COALESCE(ps_content,''), ps_creationdate
from post
where ps_postid = :messageId;
