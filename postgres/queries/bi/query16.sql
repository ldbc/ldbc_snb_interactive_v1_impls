-- todo add $minPathDistance and $maxPathDistance
with recursive friends(k_person1id, k_person2id) as (
    select k_person1id, k_person2id
    from knows
    where k_person2id in (
        select p_personid
        from person, country
        where p_placeid = ctry_city
          and ctry_name = '$country'
      )
    union
    select f.k_person1id, k.k_person2id from friends f, knows k
    where f.k_person2id = k.k_person1id
      and k.k_person2id in (
        select p_personid
        from person, country
        where p_placeid = ctry_city
          and ctry_name = '$country'
      )
  )
select friends.k_person2id, t_name, count(*)  as cnt
from friends, post, post_tag, tag, tag_tagclass, tagclass
where ps_postid = pst_postid
  and t_tagid = pst_tagid
  and ttc_tagid = pst_tagid
  and ttc_tagclassid = tc_tagclassid
  and tc_name = '$tagClass'
  and ps_creatorid = friends.k_person2id
  and friends.k_person1id = $personId
group by t_name, friends.k_person2id
order by
  cnt desc,
  t_name asc
limit 100;
