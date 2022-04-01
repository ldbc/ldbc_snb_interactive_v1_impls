WITH RECURSIVE
    Paths(dst, path, endReached) AS (
            SELECT
                k_person2id AS dst,
                ARRAY[k_person1id, k_person2id] AS path,
                0 AS endReached
            FROM knows
            WHERE k_person1id = :person1Id::bigint
        UNION ALL
            SELECT
                t.k_person2id AS dst,
                array_append(path, t.k_person2id) AS path,
                max(CASE WHEN t.k_person2id = :person2Id::bigint THEN 1 ELSE 0 END) OVER (ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS endReached
            FROM knows t
            JOIN Paths s
              ON s.dst = t.k_person1id
            WHERE s.endReached = 0
              AND NOT EXISTS (SELECT * FROM Paths s2 WHERE s2.dst = :person2Id::bigint)
    ),
    SelectedPaths(dst, path) AS (
            SELECT dst, path
            FROM Paths
            WHERE dst = :person2Id::bigint
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
