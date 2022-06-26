-- DEL 4: Remove forum and its content
DELETE FROM Forum
WHERE id = :forumId
