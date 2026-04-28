-- B-tree indexes for Apache AGE LDBC SNB graph
-- Run once after data load, before benchmarking.

-- Vertex id indexes (equality lookups in MATCH WHERE id = ...)
CREATE INDEX IF NOT EXISTS idx_person_id       ON ag_catalog.ldbc_snb_Person       ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_comment_id      ON ag_catalog.ldbc_snb_Comment      ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_post_id         ON ag_catalog.ldbc_snb_Post         ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_forum_id        ON ag_catalog.ldbc_snb_Forum        ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_tag_id          ON ag_catalog.ldbc_snb_Tag          ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_tagclass_id     ON ag_catalog.ldbc_snb_TagClass     ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_city_id         ON ag_catalog.ldbc_snb_City         ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_country_id      ON ag_catalog.ldbc_snb_Country      ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_university_id   ON ag_catalog.ldbc_snb_University   ((properties->>'id')::bigint);
CREATE INDEX IF NOT EXISTS idx_company_id      ON ag_catalog.ldbc_snb_Company      ((properties->>'id')::bigint);

-- Extra lookup indexes
CREATE INDEX IF NOT EXISTS idx_tag_name        ON ag_catalog.ldbc_snb_Tag          ((properties->>'name'));
CREATE INDEX IF NOT EXISTS idx_tagclass_name   ON ag_catalog.ldbc_snb_TagClass     ((properties->>'name'));
CREATE INDEX IF NOT EXISTS idx_country_name    ON ag_catalog.ldbc_snb_Country      ((properties->>'name'));

-- Date range indexes for IC2, IC3, IC4, IC7, IC9
CREATE INDEX IF NOT EXISTS idx_comment_date    ON ag_catalog.ldbc_snb_Comment      ((properties->>'creationDate')::bigint);
CREATE INDEX IF NOT EXISTS idx_post_date       ON ag_catalog.ldbc_snb_Post         ((properties->>'creationDate')::bigint);

-- Edge start_id / end_id indexes (traversal performance)
CREATE INDEX IF NOT EXISTS idx_knows_start     ON ag_catalog.ldbc_snb_KNOWS        (start_id);
CREATE INDEX IF NOT EXISTS idx_knows_end       ON ag_catalog.ldbc_snb_KNOWS        (end_id);
CREATE INDEX IF NOT EXISTS idx_hascreator_start ON ag_catalog.ldbc_snb_HAS_CREATOR (start_id);
CREATE INDEX IF NOT EXISTS idx_hascreator_end   ON ag_catalog.ldbc_snb_HAS_CREATOR (end_id);
CREATE INDEX IF NOT EXISTS idx_replyof_start   ON ag_catalog.ldbc_snb_REPLY_OF     (start_id);
CREATE INDEX IF NOT EXISTS idx_replyof_end     ON ag_catalog.ldbc_snb_REPLY_OF     (end_id);
CREATE INDEX IF NOT EXISTS idx_hastag_start    ON ag_catalog.ldbc_snb_HAS_TAG      (start_id);
CREATE INDEX IF NOT EXISTS idx_hastag_end      ON ag_catalog.ldbc_snb_HAS_TAG      (end_id);
CREATE INDEX IF NOT EXISTS idx_likes_start     ON ag_catalog.ldbc_snb_LIKES        (start_id);
CREATE INDEX IF NOT EXISTS idx_likes_end       ON ag_catalog.ldbc_snb_LIKES        (end_id);
CREATE INDEX IF NOT EXISTS idx_containerof_start ON ag_catalog.ldbc_snb_CONTAINER_OF (start_id);
CREATE INDEX IF NOT EXISTS idx_containerof_end   ON ag_catalog.ldbc_snb_CONTAINER_OF (end_id);
CREATE INDEX IF NOT EXISTS idx_hasmember_start ON ag_catalog.ldbc_snb_HAS_MEMBER   (start_id);
CREATE INDEX IF NOT EXISTS idx_hasmember_end   ON ag_catalog.ldbc_snb_HAS_MEMBER   (end_id);
CREATE INDEX IF NOT EXISTS idx_islocatedin_start ON ag_catalog.ldbc_snb_IS_LOCATED_IN (start_id);
CREATE INDEX IF NOT EXISTS idx_islocatedin_end   ON ag_catalog.ldbc_snb_IS_LOCATED_IN (end_id);
CREATE INDEX IF NOT EXISTS idx_hasinterest_start ON ag_catalog.ldbc_snb_HAS_INTEREST (start_id);
CREATE INDEX IF NOT EXISTS idx_hasinterest_end   ON ag_catalog.ldbc_snb_HAS_INTEREST (end_id);
CREATE INDEX IF NOT EXISTS idx_workat_start    ON ag_catalog.ldbc_snb_WORK_AT      (start_id);
CREATE INDEX IF NOT EXISTS idx_workat_end      ON ag_catalog.ldbc_snb_WORK_AT      (end_id);
CREATE INDEX IF NOT EXISTS idx_studyat_start   ON ag_catalog.ldbc_snb_STUDY_AT     (start_id);
CREATE INDEX IF NOT EXISTS idx_studyat_end     ON ag_catalog.ldbc_snb_STUDY_AT     (end_id);
CREATE INDEX IF NOT EXISTS idx_hastype_start   ON ag_catalog.ldbc_snb_HAS_TYPE     (start_id);
CREATE INDEX IF NOT EXISTS idx_hastype_end     ON ag_catalog.ldbc_snb_HAS_TYPE     (end_id);
CREATE INDEX IF NOT EXISTS idx_issubclassof_start ON ag_catalog.ldbc_snb_IS_SUBCLASS_OF (start_id);
CREATE INDEX IF NOT EXISTS idx_issubclassof_end   ON ag_catalog.ldbc_snb_IS_SUBCLASS_OF (end_id);
CREATE INDEX IF NOT EXISTS idx_hasmoderator_start ON ag_catalog.ldbc_snb_HAS_MODERATOR (start_id);
CREATE INDEX IF NOT EXISTS idx_hasmoderator_end   ON ag_catalog.ldbc_snb_HAS_MODERATOR (end_id);
CREATE INDEX IF NOT EXISTS idx_ispartof_start  ON ag_catalog.ldbc_snb_IS_PART_OF   (start_id);
CREATE INDEX IF NOT EXISTS idx_ispartof_end    ON ag_catalog.ldbc_snb_IS_PART_OF   (end_id);
