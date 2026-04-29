-- B-tree indexes for Apache AGE LDBC SNB graph (AGE 1.5+)
-- Run once after data load, before benchmarking.
--
-- AGE stores graph data in schema "ldbc_snb" with quoted label names.
-- Property access: agtype_object_field_text(properties, 'key')
-- Numeric cast: CAST(... AS bigint)  -- not :: operator (not valid in index expressions)

LOAD 'age';
SET search_path = ag_catalog, '$user', public;

-- Vertex id indexes (equality lookups in MATCH WHERE id = ...)
CREATE INDEX IF NOT EXISTS idx_person_id       ON ldbc_snb."Person"     (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_comment_id      ON ldbc_snb."Comment"    (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_post_id         ON ldbc_snb."Post"       (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_forum_id        ON ldbc_snb."Forum"      (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_tag_id          ON ldbc_snb."Tag"        (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_tagclass_id     ON ldbc_snb."TagClass"   (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_city_id         ON ldbc_snb."City"       (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_country_id      ON ldbc_snb."Country"    (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_university_id   ON ldbc_snb."University" (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_company_id      ON ldbc_snb."Company"    (CAST(agtype_object_field_text(properties, 'id') AS bigint));
CREATE INDEX IF NOT EXISTS idx_continent_id    ON ldbc_snb."Continent"  (CAST(agtype_object_field_text(properties, 'id') AS bigint));

-- Name/text lookup indexes
CREATE INDEX IF NOT EXISTS idx_tag_name        ON ldbc_snb."Tag"        (agtype_object_field_text(properties, 'name'));
CREATE INDEX IF NOT EXISTS idx_tagclass_name   ON ldbc_snb."TagClass"   (agtype_object_field_text(properties, 'name'));
CREATE INDEX IF NOT EXISTS idx_country_name    ON ldbc_snb."Country"    (agtype_object_field_text(properties, 'name'));

-- Date range indexes for IC2, IC3, IC4, IC7, IC9
CREATE INDEX IF NOT EXISTS idx_comment_date    ON ldbc_snb."Comment"    (CAST(agtype_object_field_text(properties, 'creationDate') AS bigint));
CREATE INDEX IF NOT EXISTS idx_post_date       ON ldbc_snb."Post"       (CAST(agtype_object_field_text(properties, 'creationDate') AS bigint));

-- Edge start_id / end_id indexes (traversal performance)
CREATE INDEX IF NOT EXISTS idx_knows_start       ON ldbc_snb."KNOWS"          (start_id);
CREATE INDEX IF NOT EXISTS idx_knows_end         ON ldbc_snb."KNOWS"          (end_id);
CREATE INDEX IF NOT EXISTS idx_hascreator_start  ON ldbc_snb."HAS_CREATOR"    (start_id);
CREATE INDEX IF NOT EXISTS idx_hascreator_end    ON ldbc_snb."HAS_CREATOR"    (end_id);
CREATE INDEX IF NOT EXISTS idx_replyof_start     ON ldbc_snb."REPLY_OF"       (start_id);
CREATE INDEX IF NOT EXISTS idx_replyof_end       ON ldbc_snb."REPLY_OF"       (end_id);
CREATE INDEX IF NOT EXISTS idx_hastag_start      ON ldbc_snb."HAS_TAG"        (start_id);
CREATE INDEX IF NOT EXISTS idx_hastag_end        ON ldbc_snb."HAS_TAG"        (end_id);
CREATE INDEX IF NOT EXISTS idx_likes_start       ON ldbc_snb."LIKES"          (start_id);
CREATE INDEX IF NOT EXISTS idx_likes_end         ON ldbc_snb."LIKES"          (end_id);
CREATE INDEX IF NOT EXISTS idx_containerof_start ON ldbc_snb."CONTAINER_OF"   (start_id);
CREATE INDEX IF NOT EXISTS idx_containerof_end   ON ldbc_snb."CONTAINER_OF"   (end_id);
CREATE INDEX IF NOT EXISTS idx_hasmember_start   ON ldbc_snb."HAS_MEMBER"     (start_id);
CREATE INDEX IF NOT EXISTS idx_hasmember_end     ON ldbc_snb."HAS_MEMBER"     (end_id);
CREATE INDEX IF NOT EXISTS idx_islocatedin_start ON ldbc_snb."IS_LOCATED_IN"  (start_id);
CREATE INDEX IF NOT EXISTS idx_islocatedin_end   ON ldbc_snb."IS_LOCATED_IN"  (end_id);
CREATE INDEX IF NOT EXISTS idx_hasinterest_start ON ldbc_snb."HAS_INTEREST"   (start_id);
CREATE INDEX IF NOT EXISTS idx_hasinterest_end   ON ldbc_snb."HAS_INTEREST"   (end_id);
CREATE INDEX IF NOT EXISTS idx_workat_start      ON ldbc_snb."WORK_AT"        (start_id);
CREATE INDEX IF NOT EXISTS idx_workat_end        ON ldbc_snb."WORK_AT"        (end_id);
CREATE INDEX IF NOT EXISTS idx_studyat_start     ON ldbc_snb."STUDY_AT"       (start_id);
CREATE INDEX IF NOT EXISTS idx_studyat_end       ON ldbc_snb."STUDY_AT"       (end_id);
CREATE INDEX IF NOT EXISTS idx_hastype_start     ON ldbc_snb."HAS_TYPE"       (start_id);
CREATE INDEX IF NOT EXISTS idx_hastype_end       ON ldbc_snb."HAS_TYPE"       (end_id);
CREATE INDEX IF NOT EXISTS idx_issubclassof_start ON ldbc_snb."IS_SUBCLASS_OF" (start_id);
CREATE INDEX IF NOT EXISTS idx_issubclassof_end   ON ldbc_snb."IS_SUBCLASS_OF" (end_id);
CREATE INDEX IF NOT EXISTS idx_hasmoderator_start ON ldbc_snb."HAS_MODERATOR"  (start_id);
CREATE INDEX IF NOT EXISTS idx_hasmoderator_end   ON ldbc_snb."HAS_MODERATOR"  (end_id);
CREATE INDEX IF NOT EXISTS idx_ispartof_start    ON ldbc_snb."IS_PART_OF"     (start_id);
CREATE INDEX IF NOT EXISTS idx_ispartof_end      ON ldbc_snb."IS_PART_OF"     (end_id);
