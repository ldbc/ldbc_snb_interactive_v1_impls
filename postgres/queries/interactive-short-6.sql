/* IS6. Forum of a message
\set messageId 824633720985
 */
WITH RECURSIVE chain(parent, child) as(
    SELECT replyof, messageid FROM message where messageid = :messageId
    UNION ALL
    SELECT p.replyof, p.messageid FROM message p, chain c where p.messageid = c.parent 
)
selectforumid,title, personid, firstname, lastname
from message, person, forum
where messageid = (select coalesce(min(parent), :messageId) from chain)
  and forumid =forumid andmoderatorid = personid;
;
