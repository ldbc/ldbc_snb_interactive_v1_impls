package com.ldbc.impls.workloads.ldbc.snb.bi;

import com.ldbc.driver.DbException;
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
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public abstract class BiQueryStore {

	public enum QueryType {
		Query1(1),
		Query2(2),
		Query3(3),
		Query4(4),
		Query5(5),
		Query6(6),
		Query7(7),
		Query8(8),
		Query9(9),
		Query10(10),
		Query11(11),
		Query12(12),
		Query13(13),
		Query14(14),
		Query15(15),
		Query16(16),
		Query17(17),
		Query18(18),
		Query19(19),
		Query20(20),
		Query21(21),
		Query22(22),
		Query23(23),
		Query24(24),
		Query25(25),
		;
		
		QueryType(int number) {
			this.number = number;
		}
		
		int number;
	};

	protected abstract Converter getConverter();

	protected Map<QueryType, String> queries;

	public BiQueryStore(String path, String prefix, String postfix) throws DbException {
		queries = new HashMap<>();
		for (QueryType queryType : QueryType.values()) {
			queries.put(
				queryType,
				loadQueryFromFile(path, prefix + queryType.number + postfix)
			);
		}
	}

	public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
		return queries.get(QueryType.Query1)
				.replace("$date", getConverter().convertDate(operation.date()));
	}

	public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
		return queries.get(QueryType.Query2)
				.replace("$date1", getConverter().convertDate(operation.date1()))
				.replace("$date2", getConverter().convertDate(operation.date2()))
				.replace("$country1", getConverter().convertString(operation.country1()))
				.replace("$country2", getConverter().convertString(operation.country2()));
	}

	public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
		return queries.get(QueryType.Query3)
				.replace("$year", Integer.toString(operation.year()))
				.replace("$month", Integer.toString(operation.month()));
	}

	public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
		return queries.get(QueryType.Query4)
				.replace("$tagClass", getConverter().convertString(operation.tagClass()))
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
		return queries.get(QueryType.Query5)
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
		return queries.get(QueryType.Query6)
				.replace("$tag", getConverter().convertString(operation.tag()));
	}

	public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
		return queries.get(QueryType.Query7)
				.replace("$tag", getConverter().convertString(operation.tag()));
	}

	public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
		return queries.get(QueryType.Query8)
				.replace("$tag", getConverter().convertString(operation.tag()));
	}

	public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
		return queries.get(QueryType.Query9)
				.replace("$tagClass1", getConverter().convertString(operation.tagClass1()))
				.replace("$tagClass2", getConverter().convertString(operation.tagClass2()))
				.replace("$threshold", Integer.toString(operation.threshold()));
	}

	public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
		return queries.get(QueryType.Query10)
				.replace("$tag", getConverter().convertString(operation.tag()));
	}

	public String getQuery11(LdbcSnbBiQuery11UnrelatedReplies operation) {
		return queries.get(QueryType.Query11)
				.replace("$country", getConverter().convertString(operation.country()))
				.replace("$blacklist", getConverter().convertStringList(operation.blackList()));
	}

	public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
		return queries.get(QueryType.Query12)
				.replace("$date", getConverter().convertDate(operation.date()))
				.replace("$likeThreshold", Integer.toString(operation.likeThreshold()));
	}

	public String getQuery13(LdbcSnbBiQuery13PopularMonthlyTags operation) {
		return queries.get(QueryType.Query13)
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery14(LdbcSnbBiQuery14TopThreadInitiators operation) {
		return queries.get(QueryType.Query14)
				.replace("$begin", getConverter().convertDate(operation.beginDate()))
				.replace("$end", getConverter().convertDate(operation.endDate()));
	}

	public String getQuery15(LdbcSnbBiQuery15SocialNormals operation) {
		return queries.get(QueryType.Query15)
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery16(LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
		return queries.get(QueryType.Query16)
				.replace("$personId", Long.toString(operation.personId()))
				.replace("$country", getConverter().convertString(operation.country()))
				.replace("$tagClass", getConverter().convertString(operation.tagClass()))
				.replace("$minPathDistance", Integer.toString(operation.minPathDistance()))
				.replace("$maxPathDistance", Integer.toString(operation.maxPathDistance()));
	}

	public String getQuery17(LdbcSnbBiQuery17FriendshipTriangles operation) {
		return queries.get(QueryType.Query17)
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery18(LdbcSnbBiQuery18PersonPostCounts operation) {
		return queries.get(QueryType.Query18)
				.replace("$date", getConverter().convertDate(operation.date()))
				.replace("$lengthThreshold", Integer.toString(operation.lengthThreshold()))
				.replace("$languages", getConverter().convertStringList(operation.languages()));
	}

	public String getQuery19(LdbcSnbBiQuery19StrangerInteraction operation) {
		return queries.get(QueryType.Query19)
				.replace("$date", getConverter().convertDate(operation.date()))
				.replace("$tagClass1", getConverter().convertString(operation.tagClass1()))
				.replace("$tagClass2", getConverter().convertString(operation.tagClass2()));
	}

	public String getQuery20(LdbcSnbBiQuery20HighLevelTopics operation) {
		return queries.get(QueryType.Query20)
				.replace("$tagClasses", getConverter().convertStringList(operation.tagClasses()));
	}

	public String getQuery21(LdbcSnbBiQuery21Zombies operation) {
		return queries.get(QueryType.Query21)
				.replace("$country", getConverter().convertString(operation.country()))
				.replace("$endDate", getConverter().convertDate(operation.endDate()));
	}

	public String getQuery22(LdbcSnbBiQuery22InternationalDialog operation) {
		return queries.get(QueryType.Query22)
				.replace("$country1", getConverter().convertString(operation.country1()))
				.replace("$country2", getConverter().convertString(operation.country2()));
	}

	public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
		return queries.get(QueryType.Query23)
				.replace("$country", getConverter().convertString(operation.country()));
	}

	public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
		return queries.get(QueryType.Query24)
				.replace("$tagClass", getConverter().convertString(operation.tagClass()));
	}

	public String getQuery25(LdbcSnbBiQuery25WeightedPaths operation) {
		return queries.get(QueryType.Query25)
				.replace("$person1Id", Long.toString(operation.person1Id()))
				.replace("$person2Id", Long.toString(operation.person2Id()))
				.replace("$startDate", getConverter().convertDate(operation.startDate()))
				.replace("$endDate", getConverter().convertDate(operation.endDate()));
	}

	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}

}
