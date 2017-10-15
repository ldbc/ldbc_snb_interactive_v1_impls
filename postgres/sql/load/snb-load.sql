-- Populate forum table
COPY forum FROM 'PATHVAR/forum_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate forum_person table
COPY forum_person FROM 'PATHVAR/forum_hasMember_person_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate forum_tag table
COPY forum_tag FROM 'PATHVAR/forum_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate organisation table
COPY organisation FROM 'PATHVAR/organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person table
COPY person FROM 'PATHVAR/person_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person_email table
COPY person_email FROM 'PATHVAR/person_email_emailaddress_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person_tag table
COPY person_tag FROM 'PATHVAR/person_hasInterest_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate knows table
COPY knows ( k_person1id, k_person2id, k_creationdate) FROM 'PATHVAR/person_knows_person_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY knows ( k_person2id, k_person1id, k_creationdate) FROM 'PATHVAR/person_knows_person_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate likes table
COPY likes FROM 'PATHVAR/person_likes_post_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY likes FROM 'PATHVAR/person_likes_comment_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person_language table
COPY person_language FROM 'PATHVAR/person_speaks_language_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person_university table
COPY person_university FROM 'PATHVAR/person_studyAt_organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate person_company table
COPY person_company FROM 'PATHVAR/person_workAt_organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate place table
COPY place FROM 'PATHVAR/place_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate post_tag table
COPY post_tag FROM 'PATHVAR/post_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY post_tag FROM 'PATHVAR/comment_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate tagclass table
COPY tagclass FROM 'PATHVAR/tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate subclass table
COPY subclass FROM 'PATHVAR/tagclass_isSubclassOf_tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate tag table
COPY tag FROM 'PATHVAR/tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;

-- Populate tag_tagclass table
COPY tag_tagclass FROM 'PATHVAR/tag_hasType_tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;


-- PROBLEMATIC

-- Populate post table
COPY post FROM 'PATHVAR/post_0_0-postgres.csv'    WITH (FORCE_NOT_NULL ("ps_content"),  DELIMITER '|', HEADER, FORMAT csv);
COPY post FROM 'PATHVAR/comment_0_0-postgres.csv' WITH (FORCE_NOT_NULL ("ps_content"),  DELIMITER '|', HEADER, FORMAT csv);

create view country as select city.pl_placeid as ctry_city, ctry.pl_name as ctry_name from place city, place ctry where city.pl_containerplaceid = ctry.pl_placeid and ctry.pl_type = 'country';

vacuum analyze;
