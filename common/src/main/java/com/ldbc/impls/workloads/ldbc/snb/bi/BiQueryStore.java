package com.ldbc.impls.workloads.ldbc.snb.bi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public abstract class BiQueryStore {

	public enum QueryType {
		Query1 ( 1, ImmutableList.of("date")),
		Query2 ( 2, ImmutableList.of("startDate", "endDate", "country1", "country2")),
		Query3 ( 3, ImmutableList.of("year", "month")),
		Query4 ( 4, ImmutableList.of("tagClass", "country")),
		Query5 ( 5, ImmutableList.of("country")),
		Query6 ( 6, ImmutableList.of("tag")),
		Query7 ( 7, ImmutableList.of("tag")),
		Query8 ( 8, ImmutableList.of("tag")),
		Query9 ( 9, ImmutableList.of("tagClass1", "tagClass2", "threshold")),
		Query10(10, ImmutableList.of("tag", "date")),
		Query11(11, ImmutableList.of("country", "blacklist")),
		Query12(12, ImmutableList.of("date", "likeThreshold")),
		Query13(13, ImmutableList.of("country")),
		Query14(14, ImmutableList.of("startDate", "endDate")),
		Query15(15, ImmutableList.of("country")),
		Query16(16, ImmutableList.of("personId", "country", "tagClass", "minPathDistance", "maxPathDistance")),
		Query17(17, ImmutableList.of("country")),
		Query18(18, ImmutableList.of("date", "lengthThreshold", "languages")),
		Query19(19, ImmutableList.of("date", "tagClass1", "tagClass2")),
		Query20(20, ImmutableList.of("tagClasses")),
		Query21(21, ImmutableList.of("country", "endDate")),
		Query22(22, ImmutableList.of("country1", "country2")),
		Query23(23, ImmutableList.of("country")),
		Query24(24, ImmutableList.of("tagClass")),
		Query25(25, ImmutableList.of("person1Id", "person2Id", "startDate", "endDate")),
		;
		
		QueryType(int number, List<String> parameters) {
			this.number = number;
			this.parameters = parameters;
		}

		public int number;
		public List<String> parameters;
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

	protected abstract String prepare(QueryType queryType, Map<String, String> paramaterSubstitutions);

	public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
		return prepare(QueryType.Query1, new ImmutableMap.Builder<String, String>()
				.put("date", getConverter().convertDate(operation.date())).build());
	}

	public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
		return prepare(QueryType.Query2, new ImmutableMap.Builder<String, String>()
				.put("startDate", getConverter().convertDate(operation.startDate()))
				.put("endDate", getConverter().convertDate(operation.endDate()))
				.put("country1", getConverter().convertString(operation.country1()))
				.put("country2", getConverter().convertString(operation.country2())).build());
	}

	public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
		return prepare(QueryType.Query3, new ImmutableMap.Builder<String, String>()
				.put("year", Integer.toString(operation.year()))
				.put("month", Integer.toString(operation.month())).build());
	}

	public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
		return prepare(QueryType.Query4, new ImmutableMap.Builder<String, String>()
				.put("tagClass", getConverter().convertString(operation.tagClass()))
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
		return prepare(QueryType.Query5, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
		return prepare(QueryType.Query6, new ImmutableMap.Builder<String, String>()
				.put("tag", getConverter().convertString(operation.tag())).build());
	}

	public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
		return prepare(QueryType.Query7, new ImmutableMap.Builder<String, String>()
				.put("tag", getConverter().convertString(operation.tag())).build());
	}

	public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
		return prepare(QueryType.Query8, new ImmutableMap.Builder<String, String>()
				.put("tag", getConverter().convertString(operation.tag())).build());
	}

	public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
		return prepare(QueryType.Query9, new ImmutableMap.Builder<String, String>()
				.put("tagClass1", getConverter().convertString(operation.tagClass1()))
				.put("tagClass2", getConverter().convertString(operation.tagClass2()))
				.put("threshold", Integer.toString(operation.threshold())).build());
	}

	public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
		return prepare(QueryType.Query10, new ImmutableMap.Builder<String, String>()
				.put("tag", getConverter().convertString(operation.tag()))
				.put("date", getConverter().convertDate(operation.date())).build());
	}

	public String getQuery11(LdbcSnbBiQuery11UnrelatedReplies operation) {
		return prepare(QueryType.Query11, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country()))
				.put("blacklist", getConverter().convertBlacklist(operation.blacklist())).build());
	}

	public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
		return prepare(QueryType.Query12, new ImmutableMap.Builder<String, String>()
				.put("date", getConverter().convertDate(operation.date()))
				.put("likeThreshold", Integer.toString(operation.likeThreshold())).build());
	}

	public String getQuery13(LdbcSnbBiQuery13PopularMonthlyTags operation) {
		return prepare(QueryType.Query13, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery14(LdbcSnbBiQuery14TopThreadInitiators operation) {
		return prepare(QueryType.Query14, new ImmutableMap.Builder<String, String>()
				.put("startDate", getConverter().convertDate(operation.startDate()))
				.put("endDate", getConverter().convertDate(operation.endDate())).build());
	}

	public String getQuery15(LdbcSnbBiQuery15SocialNormals operation) {
		return prepare(QueryType.Query15, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery16(LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
		return prepare(QueryType.Query16, new ImmutableMap.Builder<String, String>()
				.put("personId", Long.toString(operation.personId()))
				.put("country", getConverter().convertString(operation.country()))
				.put("tagClass", getConverter().convertString(operation.tagClass()))
				.put("minPathDistance", Integer.toString(operation.minPathDistance()))
				.put("maxPathDistance", Integer.toString(operation.maxPathDistance())).build());
	}

	public String getQuery17(LdbcSnbBiQuery17FriendshipTriangles operation) {
		return prepare(QueryType.Query17, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery18(LdbcSnbBiQuery18PersonPostCounts operation) {
		return prepare(QueryType.Query18, new ImmutableMap.Builder<String, String>()
				.put("date", getConverter().convertDate(operation.date()))
				.put("lengthThreshold", Integer.toString(operation.lengthThreshold()))
				.put("languages", getConverter().convertStringList(operation.languages())).build());
	}

	public String getQuery19(LdbcSnbBiQuery19StrangerInteraction operation) {
		return prepare(QueryType.Query19, new ImmutableMap.Builder<String, String>()
				.put("date", getConverter().convertDate(operation.date()))
				.put("tagClass1", getConverter().convertString(operation.tagClass1()))
				.put("tagClass2", getConverter().convertString(operation.tagClass2())).build());
	}

	public String getQuery20(LdbcSnbBiQuery20HighLevelTopics operation) {
		return prepare(QueryType.Query20, new ImmutableMap.Builder<String, String>()
				.put("tagClasses", getConverter().convertStringList(operation.tagClasses())).build());
	}

	public String getQuery21(LdbcSnbBiQuery21Zombies operation) {
		return prepare(QueryType.Query21, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country()))
				.put("endDate", getConverter().convertDate(operation.endDate())).build());
	}

	public String getQuery22(LdbcSnbBiQuery22InternationalDialog operation) {
		return prepare(QueryType.Query22, new ImmutableMap.Builder<String, String>()
				.put("country1", getConverter().convertString(operation.country1()))
				.put("country2", getConverter().convertString(operation.country2())).build());
	}

	public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
		return prepare(QueryType.Query23, new ImmutableMap.Builder<String, String>()
				.put("country", getConverter().convertString(operation.country())).build());
	}

	public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
		return prepare(QueryType.Query24, new ImmutableMap.Builder<String, String>()
				.put("tagClass", getConverter().convertString(operation.tagClass())).build());
	}

	public String getQuery25(LdbcSnbBiQuery25WeightedPaths operation) {
		return prepare(QueryType.Query25, new ImmutableMap.Builder<String, String>()
				.put("person1Id", Long.toString(operation.person1Id()))
				.put("person2Id", Long.toString(operation.person2Id()))
				.put("startDate", getConverter().convertDate(operation.startDate()))
				.put("endDate", getConverter().convertDate(operation.endDate())).build());
	}

	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}

}
