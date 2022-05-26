with recursive
path(src, dst) as (
    select k_person1id, k_person2id from knows
),
shorts(cur, prev, dir, len, iter) as (
    (
        select dst, src, 0, 0, 0
        from path
        where src = :person1Id
        union all
        select src, dst, 1, 0, 0
        from path
        where dst = :person2Id
    )
    union all
    (
        with
        ss as (select * from shorts),
        found as (
            select * from ss s1, ss s2
            where s1.cur = s2.cur and s1.dir = 0 and s2.dir = 1
        )
        select cur, prev, dir, len, ss.iter + 1 from ss where not exists (select * from found)
        union all
        select p.dst, p.src, s.dir, s.len + 1, s.iter + 1
        from path p, ss s
        where s.dir = 0
            and p.src = s.cur
            and not exists (select * from found)
            and not exists (select * from ss s2 where s2.cur = p.dst and s2.dir = s.dir and s2.len <= s.len + 1 and (s2.len < s.len + 1 or p.src = s2.prev))
        union all
        select p.src, p.dst, s.dir, s.len + 1, s.iter + 1
        from path p, ss s
        where s.dir = 1
            and p.dst = s.cur
            and not exists (select * from found)
            and not exists (select * from ss s2 where s2.cur = p.src and s2.dir = s.dir and s2.len <= s.len + 1 and (s2.len < s.len + 1 or p.dst = s2.prev))
    )

),
ss(cur, prev, dir, len) as (
    select cur, prev, dir, len
    from shorts
    where iter = (select max(iter) from shorts)
),
inters2(v, l0, l1) as (
    select s1.cur, s1.len, s2.len from ss s1, ss s2
    where s1.cur = s2.cur and s1.dir = 0 and s2.dir = 1
),
-- handle paths with odd length
-- we don't need both middle nodes of paths
inters3(v, l0, l1) as (
    select v, l0, l1
    from inters2 i
    where not exists (
        select * from ss where ss.cur = i.v and ss.dir = 0 and exists (select * from inters2 i2 where i2.v = ss.prev)
    )
),
inters(v, l0, l1) as (
    select v, l0, l1 from inters3 where l0 + l1 = (select min(l0 + l1) from inters3)
),
path0(s, cur, path, l0, l1, w) as (
    select i.v, i.v, ARRAY[i.v], l0, l1, 0 from inters i
    union all
    select s, ss.prev, array_prepend(ss.prev, path), l0 - 1, l1, w + coalesce(score, 0)
    from path0 p, ss left join (
        select sum(score) as score
        from (
        select (case when msg.m_c_replyof is null then 1.0 else 0.5 end) AS score
        from Message msg, Message reply
        where reply.m_c_replyof = msg.m_messageid AND msg.m_creatorid = ss.prev AND reply.m_creatorid = ss.cur
        union all
        select (case when msg.m_c_replyof is null then 1.0 else 0.5 end) AS Score
        from Message msg, Message reply
        where reply.m_c_replyof = msg.m_messageid AND msg.m_creatorid = ss.cur AND reply.m_creatorid = ss.prev
        )
    ) t on true
    where p.cur = ss.cur and p.l0 = ss.len and ss.dir = 0
),
path0r(v, path, l, w) as (
    select s, path, l1, w
    from path0
    where l0 = -1
),
path1(v, path, l) as (
    select v, path, l, w from path0r
    union all
    select ss.prev, array_append(path, ss.prev), l - 1, w + coalesce(score, 0)
    from path1 p, ss left join (
        select sum(score) as score
        from (
        select (case when msg.m_c_replyof is null then 1.0 else 0.5 end) AS score
        from Message msg, Message reply
        where reply.m_c_replyof = msg.m_messageid AND msg.m_creatorid = ss.prev AND reply.m_creatorid = ss.cur
        union all
        select (case when msg.m_c_replyof is null then 1.0 else 0.5 end) AS Score
        from Message msg, Message reply
        where reply.m_c_replyof = msg.m_messageid AND msg.m_creatorid = ss.cur AND reply.m_creatorid = ss.prev
        )
    ) t on true
    where p.v = ss.cur and p.l = ss.len and ss.dir = 1
),
finalPaths(path, w) as (
    select path, w from path1 where l = -1
)
select distinct * from finalPaths;
