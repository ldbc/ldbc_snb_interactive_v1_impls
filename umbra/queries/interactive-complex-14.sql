WITH RECURSIVE
    Paths(dst, path) AS (
            SELECT k_person2id, ARRAY[k_person1id, k_person2id] FROM knows WHERE k_person1id = :person1Id
            UNION ALL
            SELECT t.k_person2id, array_append(path, t.k_person2id)
            FROM (SELECT *
                  FROM Paths
                  WHERE NOT EXISTS (SELECT * FROM Paths s2 WHERE s2.dst = :person2Id)
                ) s,
                knows t
            WHERE s.dst = t.k_person1id
    ),
    SelectedPaths(dst, path) AS (
            SELECT dst, path
            FROM Paths
            WHERE dst = :person2Id
    ),
    Iterator(i) AS (
            SELECT i
            FROM
                (SELECT array_length(path, 1) v FROM SelectedPaths LIMIT 1) l(v),
                generate_series(1, l.v) t(i)
    ),
    PathWeights(dst, path, Score) AS (
            SELECT p.dst, p.path, SUM(Score)
            FROM SelectedPaths p, Iterator it, (
                    SELECT 0 AS Score
                    UNION ALL
                    SELECT (case when msg.m_c_replyof IS NULL then 1.0 else 0.5 end) AS Score
                    FROM Message msg, Message reply
                    WHERE reply.m_c_replyof = msg.m_messageid
                      AND msg.m_creatorid = p.path[i]
                      AND reply.m_creatorid = p.path[i+1]
                    UNION ALL
                    SELECT (case when msg.m_c_replyof IS NULL then 1.0 else 0.5 end) AS Score
                    FROM Message msg, Message reply
                    WHERE reply.m_c_replyof = msg.m_messageid
                      AND msg.m_creatorid = p.path[i+1]
                      AND reply.m_creatorid = p.path[i]
            ) t
            GROUP BY p.dst, p.path
    )
SELECT path, Score as Weights
FROM PathWeights
ORDER BY Weights DESC, path;
