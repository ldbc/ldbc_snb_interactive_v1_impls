COPY forum             FROM 'PATHVAR/dynamic/forum_0_0.csv'                       (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY forum_person      FROM 'PATHVAR/dynamic/forum_hasMember_person_0_0.csv'      (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY forum_tag         FROM 'PATHVAR/dynamic/forum_hasTag_tag_0_0.csv'            (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY organisation      FROM 'PATHVAR/static/organisation_0_0.csv'                 (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person            FROM 'PATHVAR/dynamic/person_0_0.csv'                      (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person_email      FROM 'PATHVAR/dynamic/person_email_emailaddress_0_0.csv'   (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person_tag        FROM 'PATHVAR/dynamic/person_hasInterest_tag_0_0.csv'      (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY likes             FROM 'PATHVAR/dynamic/person_likes_post_0_0.csv'           (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY likes             FROM 'PATHVAR/dynamic/person_likes_comment_0_0.csv'        (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person_language   FROM 'PATHVAR/dynamic/person_speaks_language_0_0.csv'      (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person_university FROM 'PATHVAR/dynamic/person_studyAt_organisation_0_0.csv' (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY person_company    FROM 'PATHVAR/dynamic/person_workAt_organisation_0_0.csv'  (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY place             FROM 'PATHVAR/static/place_0_0.csv'                        (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY tagclass          FROM 'PATHVAR/static/tagclass_0_0.csv'                     (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY tag               FROM 'PATHVAR/static/tag_0_0.csv'                          (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');

COPY post              FROM 'PATHVAR/dynamic/post_0_0.csv'                        (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY comment           FROM 'PATHVAR/dynamic/comment_0_0.csv'                     (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');

COPY post_tag          FROM 'PATHVAR/dynamic/post_hasTag_tag_0_0.csv'             (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY comment_tag       FROM 'PATHVAR/dynamic/comment_hasTag_tag_0_0.csv'          (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');

COPY knows (k_person1id, k_person2id, k_creationdate) FROM 'PATHVAR/dynamic/person_knows_person_0_0.csv' (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');
COPY knows (k_person2id, k_person1id, k_creationdate) FROM 'PATHVAR/dynamic/person_knows_person_0_0.csv' (DELIMITER '|', HEADER, TIMESTAMPFORMAT '%Y-%m-%dT%H:%M:%S.%g+0000');

-- COPY message (m_messageid, m_ps_imagefile, m_creationdate, m_locationip, m_browserused, m_ps_language, m_content, m_length, m_creatorid, m_ps_forumid, m_locationid) FROM 'PATHVAR/dynamic/post_0_0.csv' WITH (DELIMITER '|', HEADER, FORMAT csv);
-- COPY message FROM 'PATHVAR/dynamic/comment_0_0-postgres.csv' WITH (DELIMITER '|', HEADER, FORMAT csv);

CREATE TABLE country AS
    SELECT city.pl_placeid AS ctry_city, ctry.pl_name AS ctry_name
    FROM place city, place ctry
    WHERE city.pl_containerplaceid = ctry.pl_placeid
      AND ctry.pl_type = 'country';

CREATE TABLE message_tag AS
    SELECT * FROM post_tag
    UNION ALL
    SELECT * FROM comment_tag;

CREATE TABLE message AS
    SELECT m_messageid, m_ps_imagefile, m_creationdate, m_locationip, m_browserused, m_ps_language, m_content, m_length, m_creatorid, m_locationid, m_ps_forumid, NULL AS m_c_replyof
    FROM post
    UNION ALL
    SELECT m_messageid, NULL, m_creationdate, m_locationip, m_browserused, NULL, m_content, m_length, m_creatorid, m_locationid, NULl, coalesce(m_replyof_post, m_replyof_comment)
    FROM comment;

DROP TABLE post;
DROP TABLE comment;
DROP TABLE post_tag;
DROP TABLE comment_tag;
