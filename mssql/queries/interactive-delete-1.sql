-- Remove person and its personal forums and message (sub)threads
DELETE FROM Person
WHERE personId = :personId