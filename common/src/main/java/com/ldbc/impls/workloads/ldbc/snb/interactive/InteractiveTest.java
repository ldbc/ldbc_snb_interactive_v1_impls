package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.google.common.collect.ImmutableList;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.junit.Test;

import java.util.Date;

public abstract class InteractiveTest extends SnbTest {

    public InteractiveTest(BaseDb db) {
        super(db, new LdbcSnbInteractiveWorkload());
    }

    @Test
    public void testQuery1() throws Exception {
        run(db, new LdbcQuery1(30786325579101L, "Ian", LIMIT));
    }

    @Test
    public void testQuery2() throws Exception {
        run(db, new LdbcQuery2(19791209300143L, new Date(1354060800000L), LIMIT));
    }

    @Test
    public void testQuery3() throws Exception {
        run(db, new LdbcQuery3(15393162790207L, "Puerto_Rico", "Republic_of_Macedonia", new Date(1291161600000L), 30, LIMIT));
    }

    @Test
    public void testQuery4() throws Exception {
        run(db, new LdbcQuery4(10995116278874L, new Date(1338508800000L), 28, LIMIT));
    }

    @Test
    public void testQuery5() throws Exception {
        run(db, new LdbcQuery5(15393162790207L, new Date(1344643200000L), LIMIT));
    }

    @Test
    public void testQuery6() throws Exception {
        run(db, new LdbcQuery6(30786325579101L, "Shakira", LIMIT));
    }

    @Test
    public void testQuery7() throws Exception {
        run(db, new LdbcQuery7(26388279067534L, LIMIT));
    }

    @Test
    public void testQuery8() throws Exception {
        run(db, new LdbcQuery8(2199023256816L, LIMIT));
    }

    @Test
    public void testQuery9() throws Exception {
        run(db, new LdbcQuery9(32985348834013L, new Date(1346112000000L), LIMIT));
    }

    @Test
    public void testQuery10() throws Exception {
        run(db, new LdbcQuery10(30786325579101L, 7, LIMIT));
    }

    @Test
    public void testQuery11() throws Exception {
        run(db, new LdbcQuery11(30786325579101L, "Puerto_Rico", 2004, LIMIT));
    }

    @Test
    public void testQuery12() throws Exception {
        run(db, new LdbcQuery12(19791209300143L, "BasketballPlayer", LIMIT));
    }

    @Test
    public void testQuery13() throws Exception {
        run(db, new LdbcQuery13(32985348833679L, 26388279067108L));
    }

    @Test
    public void testQuery14() throws Exception {
        run(db, new LdbcQuery14(32985348833679L, 2199023256862L));
    }

    @Test
    public void testShortQuery1() throws Exception {
        run(db, new LdbcShortQuery1PersonProfile(32985348833679L));
    }

    @Test
    public void testShortQuery2() throws Exception {
        run(db, new LdbcShortQuery2PersonPosts(32985348833679L, LIMIT));
    }

    @Test
    public void testShortQuery3() throws Exception {
        run(db, new LdbcShortQuery3PersonFriends(32985348833679L));
    }

    @Test
    public void testShortQuery4() throws Exception {
        run(db, new LdbcShortQuery4MessageContent(2061584476422L));
    }

    @Test
    public void testShortQuery5() throws Exception {
        run(db, new LdbcShortQuery5MessageCreator(2061584476422L));
    }

    @Test
    public void testShortQuery6() throws Exception {
        run(db, new LdbcShortQuery6MessageForum(2061584476422L));
    }

    @Test
    public void testShortQuery7() throws Exception {
        run(db, new LdbcShortQuery7MessageReplies(2061584476422L));
    }

    @Test
    public void testUpdateQuery1() throws Exception {
        db.beginTransaction();
        final LdbcUpdate1AddPerson.Organization university1 = new LdbcUpdate1AddPerson.Organization(1001L, 2013);
        final LdbcUpdate1AddPerson.Organization university2 = new LdbcUpdate1AddPerson.Organization(1002L, 2013);
        final LdbcUpdate1AddPerson.Organization company1 = new LdbcUpdate1AddPerson.Organization(1003L, 2013);
        final LdbcUpdate1AddPerson.Organization company2 = new LdbcUpdate1AddPerson.Organization(1004L, 2013);
        run(db, new LdbcUpdate1AddPerson(
                        1011L,
                        "",
                        "",
                        "",
                        new Date(0),
                        new Date(0),
                        "",
                        "",
                        0,
                        ImmutableList.of("en", "fr"),
                        ImmutableList.of("mail1", "mail2"),
                        ImmutableList.of(1012L, 1013L),
                        ImmutableList.of(university1, university2),
                        ImmutableList.of(company1, company2)
                )
        );
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery2() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate2AddPostLike(1021L, 1022L, new Date(0L)));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery3() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate3AddCommentLike(1031L, 1032L, new Date(0L)));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery4() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate4AddForum(1041L, "", new Date(0L), 1042L, ImmutableList.of(1043L, 1044L)));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery5() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate5AddForumMembership(1051L, 1052L, new Date(0L)));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery6() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate6AddPost(
                1061L,
                "",
                new Date(0L),
                "",
                "",
                "",
                "",
                0,
                1062L,
                1063L,
                1064L,
                ImmutableList.of(1065L, 1066L)
        ));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery7() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate7AddComment(
                1071L,
                new Date(0L),
                "",
                "",
                "",
                0,
                1072L,
                1073L,
                1074L,
                1075L,
                ImmutableList.of(1076L, 1077L)));
        db.rollbackTransaction();
    }

    @Test
    public void testUpdateQuery8() throws Exception {
        db.beginTransaction();
        run(db, new LdbcUpdate8AddFriendship(1081L, 1082L, new Date(0L)));
        db.rollbackTransaction();
    }

}

