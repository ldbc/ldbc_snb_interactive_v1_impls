package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.ldbc.driver.DbException;
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
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

public abstract class InteractiveTest extends SnbTest {

    public InteractiveTest(BaseDb db) throws DbException {
        super(db, new LdbcSnbInteractiveWorkload());
    }

    @Test
    public void testQueries() throws DbException, IOException {
        run(db, new LdbcQuery1(30786325579101L, "Ian", LIMIT));
        run(db, new LdbcQuery2(19791209300143L, new Date(1354060800000L), LIMIT));
        run(db, new LdbcQuery3(15393162790207L, "Puerto_Rico", "Republic_of_Macedonia", new Date(1291161600000L), 30, LIMIT));
        run(db, new LdbcQuery4(10995116278874L, new Date(1338508800000L), 28, LIMIT));
        run(db, new LdbcQuery5(15393162790207L, new Date(1344643200000L), LIMIT));
        run(db, new LdbcQuery6(30786325579101L, "Shakira", LIMIT));
        run(db, new LdbcQuery7(26388279067534L, LIMIT));
        run(db, new LdbcQuery8(2199023256816L, LIMIT));
        run(db, new LdbcQuery9(32985348834013L, new Date(1346112000000L), LIMIT));
        run(db, new LdbcQuery10(30786325579101L, 7, LIMIT));
        run(db, new LdbcQuery11(30786325579101L, "Puerto_Rico", 2004, LIMIT));
        run(db, new LdbcQuery12(19791209300143L, "BasketballPlayer", LIMIT));
        run(db, new LdbcQuery13(32985348833679L, 26388279067108L));
        run(db, new LdbcQuery14(32985348833679L, 2199023256862L));

        run(db, new LdbcShortQuery1PersonProfile(32985348833679L));
        run(db, new LdbcShortQuery2PersonPosts(32985348833679L, LIMIT));
        run(db, new LdbcShortQuery3PersonFriends(32985348833679L));
        run(db, new LdbcShortQuery4MessageContent(2061584476422L));
        run(db, new LdbcShortQuery5MessageCreator(2061584476422L));
        run(db, new LdbcShortQuery6MessageForum(2061584476422L));
        run(db, new LdbcShortQuery7MessageReplies(2061584476422L));

//        run(db, new LdbcUpdate1AddPerson());
//        run(db, new LdbcUpdate2AddPostLike());
//        run(db, new LdbcUpdate3AddCommentLike());
//        run(db, new LdbcUpdate4AddForum());
//        run(db, new LdbcUpdate5AddForumMembership());
//        run(db, new LdbcUpdate6AddPost());
//        run(db, new LdbcUpdate7AddComment());
//        run(db, new LdbcUpdate8AddFriendship());

        db.close();
        workload.close();
    }

}

