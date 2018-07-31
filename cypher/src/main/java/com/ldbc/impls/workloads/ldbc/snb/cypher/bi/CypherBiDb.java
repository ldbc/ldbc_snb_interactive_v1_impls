package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPaths;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;

import java.util.Map;

public class CypherBiDb extends CypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
        registerOperationHandler(LdbcSnbBiQuery2TopTags.class, BiQuery2.class);
        registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, BiQuery3.class);
        registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, BiQuery4.class);
        registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, BiQuery5.class);
        registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, BiQuery6.class);
        registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, BiQuery7.class);
        registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, BiQuery8.class);
        registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, BiQuery9.class);
        registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
        registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, BiQuery11.class);
        registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, BiQuery12.class);
        registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, BiQuery13.class);
        registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, BiQuery14.class);
        registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, BiQuery15.class);
        registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
        registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
        registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
        registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
        registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, BiQuery20.class);
        registerOperationHandler(LdbcSnbBiQuery21Zombies.class, BiQuery21.class);
        registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, BiQuery22.class);
        registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, BiQuery23.class);
        registerOperationHandler(LdbcSnbBiQuery24MessagesByTopic.class, BiQuery24.class);
        registerOperationHandler(LdbcSnbBiQuery25WeightedPaths.class, BiQuery25.class);
    }

}
