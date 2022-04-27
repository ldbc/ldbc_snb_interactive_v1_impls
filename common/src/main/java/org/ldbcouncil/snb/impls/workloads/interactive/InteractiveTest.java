package org.ldbcouncil.snb.impls.workloads.interactive;

import com.google.common.collect.ImmutableList;
import org.ldbcouncil.snb.driver.Db;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery1;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery10;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery11;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery12;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery2;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery3;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery4;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery5;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery6;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery7;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery8;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery9;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery1PersonProfile;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery2PersonPosts;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery3PersonFriends;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery4MessageContent;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery5MessageCreator;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery6MessageForum;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery7MessageReplies;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcSnbInteractiveWorkload;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate2AddPostLike;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate3AddCommentLike;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate4AddForum;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate5AddForumMembership;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate6AddPost;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate7AddComment;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate8AddFriendship;
import org.ldbcouncil.snb.impls.workloads.SnbTest;
import org.junit.Test;

import java.util.Date;

public abstract class InteractiveTest<D extends Db> extends SnbTest<D>
{

    public InteractiveTest( D db )
    {
        super( db, new LdbcSnbInteractiveWorkload() );
    }

    @Test
    public void testQuery1() throws Exception
    {
        run( db, new LdbcQuery1( 30786325579101L, "Ian", LIMIT ) );
    }

    @Test
    public void testQuery2() throws Exception
    {
        run( db, new LdbcQuery2( 19791209300143L, new Date( 1354060800000L ), LIMIT ) );
    }

    @Test
    public void testQuery3() throws Exception
    {
        run( db, new LdbcQuery3( 15393162790207L, "Puerto_Rico", "Republic_of_Macedonia", new Date( 1291161600000L ), 30, LIMIT ) );
    }

    @Test
    public void testQuery4() throws Exception
    {
        run( db, new LdbcQuery4( 10995116278874L, new Date( 1338508800000L ), 28, LIMIT ) );
    }

    @Test
    public void testQuery5() throws Exception
    {
        run( db, new LdbcQuery5( 15393162790207L, new Date( 1344643200000L ), LIMIT ) );
    }

    @Test
    public void testQuery6() throws Exception
    {
        run( db, new LdbcQuery6( 30786325579101L, "Shakira", LIMIT ) );
    }

    @Test
    public void testQuery7() throws Exception
    {
        run( db, new LdbcQuery7( 26388279067534L, LIMIT ) );
    }

    @Test
    public void testQuery8() throws Exception
    {
        run( db, new LdbcQuery8( 2199023256816L, LIMIT ) );
    }

    @Test
    public void testQuery9() throws Exception
    {
        run( db, new LdbcQuery9( 32985348834013L, new Date( 1346112000000L ), LIMIT ) );
    }

    @Test
    public void testQuery10() throws Exception
    {
        run( db, new LdbcQuery10( 30786325579101L, 7, LIMIT ) );
    }

    @Test
    public void testQuery11() throws Exception
    {
        run( db, new LdbcQuery11( 30786325579101L, "Puerto_Rico", 2004, LIMIT ) );
    }

    @Test
    public void testQuery12() throws Exception
    {
        run( db, new LdbcQuery12( 19791209300143L, "BasketballPlayer", LIMIT ) );
    }

    @Test
    public void testQuery13() throws Exception
    {
        run( db, new LdbcQuery13( 32985348833679L, 26388279067108L ) );
    }

    @Test
    public void testQuery14() throws Exception
    {
        run( db, new LdbcQuery14( 32985348833679L, 2199023256862L ) );
    }

    @Test
    public void testShortQuery1() throws Exception
    {
        run( db, new LdbcShortQuery1PersonProfile( 32985348833679L ) );
    }

    @Test
    public void testShortQuery2() throws Exception
    {
        run( db, new LdbcShortQuery2PersonPosts( 32985348833679L, LIMIT ) );
    }

    @Test
    public void testShortQuery3() throws Exception
    {
        run( db, new LdbcShortQuery3PersonFriends( 32985348833679L ) );
    }

    @Test
    public void testShortQuery4() throws Exception
    {
        run( db, new LdbcShortQuery4MessageContent( 2061584476422L ) );
    }

    @Test
    public void testShortQuery5() throws Exception
    {
        run( db, new LdbcShortQuery5MessageCreator( 2061584476422L ) );
    }

    @Test
    public void testShortQuery6() throws Exception
    {
        run( db, new LdbcShortQuery6MessageForum( 2061584476422L ) );
    }

    @Test
    public void testShortQuery7() throws Exception
    {
        run( db, new LdbcShortQuery7MessageReplies( 2061584476422L ) );
    }

    @Test
    public void testUpdateQuery1() throws Exception
    {
        final LdbcUpdate1AddPerson.Organization university1 = new LdbcUpdate1AddPerson.Organization( 5142L, 2004 );
        run( db, new LdbcUpdate1AddPerson(
                     10995116277777L,
                     "Almira",
                     "Patras",
                     "female",
                     new Date( 425606400000L ), // note that java.util.Date has no timezone
                     new Date( 1291394394934L ),
                     "193.104.227.215",
                     "Internet Explorer",
                     1226L,
                     ImmutableList.of( "ru", "en" ),
                     ImmutableList.of( "Almira10995116277777@gmail.com", "Almira10995116277777@gmx.com" ),
                     ImmutableList.of( 1916L ),
                     ImmutableList.of( university1 ),
                     ImmutableList.of()
             )
        );
    }

    @Test
    public void testUpdateQuery2() throws Exception
    {
        run( db, new LdbcUpdate2AddPostLike( 8796093022239L, 206158430617L, new Date( 1290749436322L ) ) );
    }

    @Test
    public void testUpdateQuery3() throws Exception
    {
        run( db, new LdbcUpdate3AddCommentLike( 4398046511123L, 343597384736L, new Date( 1290725729770L ) ) );
    }

    @Test
    public void testUpdateQuery4() throws Exception
    {
        run( db, new LdbcUpdate4AddForum( 343597383803L, "Album 1 of Wolfgang Bauer", new Date( 1290883501867L ), 10, ImmutableList.of( 4844L ) ) );
    }

    @Test
    public void testUpdateQuery5() throws Exception
    {
        run( db, new LdbcUpdate5AddForumMembership( 343597383798L, 8796093022252L, new Date( 1290748277090L ) ) );
    }

    @Test
    public void testUpdateQuery6() throws Exception
    {
        run( db, new LdbcUpdate6AddPost(
                343597384592L,
                "photo343597384592.jpg",
                new Date( 1290883512867L ),
                "46.21.0.249",
                "Internet Explorer",
                "",
                "",
                0,
                10L,
                343597383803L,
                50L,
                ImmutableList.of()
        ) );
    }

    @Test
    public void testUpdateQuery7() throws Exception
    {
        run( db, new LdbcUpdate7AddComment(
                343597384747L,
                new Date( 1290689294243L ),
                "49.206.89.61",
                "Safari",
                "no way!",
                7,
                10995116277809L,
                0,
                -1,
                343597384736L,
                ImmutableList.of() ) );
    }

    @Test
    public void testUpdateQuery8() throws Exception
    {
        run( db, new LdbcUpdate8AddFriendship( 4398046511147L, 10995116277809L, new Date( 1290907550597L ) ) );
    }
}

