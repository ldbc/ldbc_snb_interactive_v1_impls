-- COPY forum to '/tmp/forum_ldbc.csv';
-- COPY forum_person to '/tmp/forum_person_ldbc.csv';
-- COPY person to '/tmp/person_ldbc.csv';
-- COPY knows to '/tmp/knows_ldbc.csv';
-- COPY likes to '/tmp/likes_ldbc.csv';
-- COPY post to '/tmp/post_ldbc.csv';
-- COPY (SELECT * from country) to '/tmp/country_ldbc.csv';

-- COPY forum from '/tmp/forum_ldbc.csv';
-- COPY forum_person from '/tmp/forum_person_ldbc.csv';
-- COPY person from '/tmp/person_ldbc.csv';
-- COPY knows from '/tmp/knows_ldbc.csv';
-- COPY likes from '/tmp/likes_ldbc.csv';
-- COPY post from '/tmp/post_ldbc.csv';
-- create table country (ctry_city bigint not null, ctry_name varchar not null);
-- COPY country from '/tmp/country_ldbc.csv';


-- Populate forum table
ALTER TABLE forum ADD COLUMN dummy int;
COPY forum FROM 'PATHVAR/forum_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE forum DROP COLUMN dummy;

-- Populate forum_person table
ALTER TABLE forum_person ADD COLUMN dummy int;
COPY forum_person FROM 'PATHVAR/forum_hasMember_person_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE forum_person DROP COLUMN dummy;

-- Populate forum_tag table
ALTER TABLE forum_tag ADD COLUMN dummy int;
COPY forum_tag FROM 'PATHVAR/forum_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE forum_tag DROP COLUMN dummy;

-- Populate organisation table
ALTER TABLE organisation ADD COLUMN dummy int;
COPY organisation FROM 'PATHVAR/organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE organisation DROP COLUMN dummy;

-- Populate person table
ALTER TABLE person ADD COLUMN dummy int;
COPY person FROM 'PATHVAR/person_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person DROP COLUMN dummy;

-- Populate person_email table
ALTER TABLE person_email ADD COLUMN dummy int;
COPY person_email FROM 'PATHVAR/person_email_emailaddress_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person_email DROP COLUMN dummy;

-- Populate person_tag table
ALTER TABLE person_tag ADD COLUMN dummy int;
COPY person_tag FROM 'PATHVAR/person_hasInterest_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person_tag DROP COLUMN dummy;

-- Populate knows table
ALTER TABLE knows ADD COLUMN dummy int;
COPY knows FROM 'PATHVAR/person_knows_person_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE knows DROP COLUMN dummy;

-- Populate likes table
ALTER TABLE likes ADD COLUMN dummy int;
COPY likes FROM 'PATHVAR/person_likes_post_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY likes FROM 'PATHVAR/person_likes_comment_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE likes DROP COLUMN dummy;

-- Populate person_language table
ALTER TABLE person_language ADD COLUMN dummy int;
COPY person_language FROM 'PATHVAR/person_speaks_language_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person_language DROP COLUMN dummy;

-- Populate person_university table
ALTER TABLE person_university ADD COLUMN dummy int;
COPY person_university FROM 'PATHVAR/person_studyAt_organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person_university DROP COLUMN dummy;

-- Populate person_company table
ALTER TABLE person_company ADD COLUMN dummy int;
COPY person_company FROM 'PATHVAR/person_workAt_organisation_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE person_company DROP COLUMN dummy;

-- Populate place table
ALTER TABLE place ADD COLUMN dummy int;
COPY place FROM 'PATHVAR/place_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE place DROP COLUMN dummy;

-- Populate post_tag table
ALTER TABLE post_tag ADD COLUMN dummy int;
COPY post_tag FROM 'PATHVAR/post_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY post_tag FROM 'PATHVAR/comment_hasTag_tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE post_tag DROP COLUMN dummy;

-- Populate tagclass table
ALTER TABLE tagclass ADD COLUMN dummy int;
COPY tagclass FROM 'PATHVAR/tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE tagclass DROP COLUMN dummy;

-- Populate subclass table
ALTER TABLE subclass ADD COLUMN dummy int;
COPY subclass FROM 'PATHVAR/tagclass_isSubclassOf_tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE subclass DROP COLUMN dummy;

-- Populate tag table
ALTER TABLE tag ADD COLUMN dummy int;
COPY tag FROM 'PATHVAR/tag_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE tag DROP COLUMN dummy;

-- Populate tag_tagclass table
ALTER TABLE tag_tagclass ADD COLUMN dummy int;
COPY tag_tagclass FROM 'PATHVAR/tag_hasType_tagclass_0_0.csv' WITH DELIMITER '|' CSV HEADER;
ALTER TABLE tag_tagclass DROP COLUMN dummy;


-- PROBLEMATIC

-- Populate post table
COPY post FROM 'PATHVAR/post_0_0_converted.csv' WITH (FORCE_NOT_NULL ("ps_content"),  DELIMITER '|', HEADER, FORMAT csv);
COPY post FROM 'PATHVAR/comment_0_0_converted.csv'  WITH (FORCE_NOT_NULL ("ps_content"),  DELIMITER '|', HEADER, FORMAT csv);

create view country as select city.pl_placeid as ctry_city, ctry.pl_name as ctry_name from place city, place ctry where city.pl_containerplaceid = ctry.pl_placeid and ctry.pl_type = 'country';