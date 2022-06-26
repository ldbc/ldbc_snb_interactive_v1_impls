DROP TABLE IF EXISTS Person_Delete_candidates;
DROP TABLE IF EXISTS Forum_Delete_candidates;
DROP TABLE IF EXISTS Comment_Delete_candidates;
DROP TABLE IF EXISTS Post_Delete_candidates;
DROP TABLE IF EXISTS Person_likes_Comment_Delete_candidates;
DROP TABLE IF EXISTS Person_likes_Post_Delete_candidates;
DROP TABLE IF EXISTS Forum_hasMember_Person_Delete_candidates;
DROP TABLE IF EXISTS Person_knows_Person_Delete_candidates;

CREATE TABLE Person_Delete_candidates                (deletionDate timestamp with time zone not null, id bigint not null);
CREATE TABLE Forum_Delete_candidates                 (deletionDate timestamp with time zone not null, id bigint not null);
CREATE TABLE Comment_Delete_candidates               (deletionDate timestamp with time zone not null, id bigint not null);
CREATE TABLE Post_Delete_candidates                  (deletionDate timestamp with time zone not null, id bigint not null);
CREATE TABLE Person_likes_Comment_Delete_candidates  (deletionDate timestamp with time zone not null, src bigint not null, trg bigint not null);
CREATE TABLE Person_likes_Post_Delete_candidates     (deletionDate timestamp with time zone not null, src bigint not null, trg bigint not null);
CREATE TABLE Forum_hasMember_Person_Delete_candidates(deletionDate timestamp with time zone not null, src bigint not null, trg bigint not null);
CREATE TABLE Person_knows_Person_Delete_candidates   (deletionDate timestamp with time zone not null, src bigint not null, trg bigint not null);

-- If DELETE USING matches multiple times on a single row (in the table-under-deletion),
-- Umbra throws an error: 'more than one row returned by a subquery used as an expression'
-- To prevent this, we use '..._unique' tables which do not contain timestamps (which are
-- not required for performing the delete) and are filtered to distinct id values.
DROP TABLE IF EXISTS Comment_Delete_candidates_unique;
DROP TABLE IF EXISTS Post_Delete_candidates_unique;
DROP TABLE IF EXISTS Forum_Delete_candidates_unique;

CREATE TABLE Comment_Delete_candidates_unique(id bigint not null);
CREATE TABLE Post_Delete_candidates_unique(id bigint not null);
CREATE TABLE Forum_Delete_candidates_unique(deletionDate timestamp with time zone not null, id bigint not null);
