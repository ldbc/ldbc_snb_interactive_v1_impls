-- Populate posts and comments tables, union them into message
COPY post FROM '/data/dynamic/post_0_0.csv' WITH DELIMITER '|' CSV HEADER;
COPY comment FROM '/data/dynamic/comment_0_0.csv' WITH DELIMITER '|' CSV HEADER;
