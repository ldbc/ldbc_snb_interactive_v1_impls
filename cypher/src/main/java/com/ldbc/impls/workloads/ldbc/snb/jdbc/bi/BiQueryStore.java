package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class BiQueryStore {
	public enum QueryType {
		Query1("bi-1.cypher"),
		Query2("bi-2.cypher"),
		Query3("bi-3.cypher"),
		Query4("bi-4.cypher"),
		Query5("bi-5.cypher"),
		Query6("bi-6.cypher"),
		Query7("bi-7.cypher"),
		Query8("bi-8.cypher"),
		Query9("bi-9.cypher"),
		Query10("bi-10.cypher"),
//		Query11("bi-11.cypher"),
		Query12("bi-12.cypher"),
		Query13("bi-13.cypher"),
		Query14("bi-14.cypher"),
		Query15("bi-15.cypher"),
		Query16("bi-16.cypher"),
		Query17("bi-17.cypher"),
		Query18("bi-18.cypher"),
		Query19("bi-19.cypher"),
		Query20("bi-20.cypher"),
		Query21("bi-21.cypher"),
		Query22("bi-22.cypher"),
		Query23("bi-23.cypher"),
		Query24("bi-24.cypher"),
//		Query25("bi-25.cypher"),
		;
		
		QueryType(String file) {
			fileName = file;
		}
		
		String fileName;
	};
	
	private HashMap<QueryType, String> queries;
	
	public BiQueryStore(String path) throws DbException {
		queries = new HashMap<BiQueryStore.QueryType, String>();
		for (QueryType queryType : QueryType.values()) {
			queries.put(queryType, loadQueryFromFile(path, queryType.fileName));
		}
	}

	public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
		return queries.get(QueryType.Query1)
			.replace("--1--", convertDate(operation.date()));
	}
	
	
	public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
		return queries.get(QueryType.Query2)
			.replace("--1--", convertDate(operation.date1()))
			.replace("--2--", convertDate(operation.date2()))
			.replace("--3--", operation.country1())
			.replace("--4--", operation.country2());
	}
	
	public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
		return queries.get(QueryType.Query3)
			.replace("--1--", convertDate(operation.year()))
			.replace("--2--", convertDate(operation.month()));
	}
	
	public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
		return queries.get(QueryType.Query4)
			.replace("--1--", operation.tagClass())
			.replace("--2--", operation.country());
	}
	
	public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
		return queries.get(QueryType.Query5)
			.replace("--1--", operation.country());
	}
	
	public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
		return queries.get(QueryType.Query6)
			.replace("--1--", operation.tag());
	}
	
	public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
		return queries.get(QueryType.Query7)
			.replace("--1--", operation.tag());
	}
	
	public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
		return queries.get(QueryType.Query8)
			.replace("--1--", operation.tag());
	}
	
	public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
		return queries.get(QueryType.Query9)
			.replace("--1--", operation.tagClassA())
			.replace("--2--", operation.tagClassB())
			.replace("--3--", Integer.toString(operation.threshold()));
	}
	
	public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
		return queries.get(QueryType.Query10)
			.replace("--1--", operation.tag());
	}
	
	public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
		return queries.get(QueryType.Query12)
			.replace("--1--", convertDate(operation.date()))
			.replace("--2--", Integer.toString(operation.likeThreshold()));
	}
	
	public String getQuery13(LdbcSnbBiQuery13PopularMonthlyTags operation) {
		return queries.get(QueryType.Query13)
			.replace("--1--",operation.country());
	}
	
	public String getQuery14(LdbcSnbBiQuery14TopThreadInitiators operation) {
		return queries.get(QueryType.Query14)
			.replace("--1--",convertDate(operation.beginDate()))
			.replace("--2--",convertDate(operation.endDate()));
	}
	
	public String getQuery15(LdbcSnbBiQuery15SocialNormals operation) {
		return queries.get(QueryType.Query15)
			.replace("--1--",operation.country());
	}
	
	public String getQuery16(LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
		return queries.get(QueryType.Query16)
			.replace("--1--", Long.toString(operation.person()))
			.replace("--2--",operation.country())
			.replace("--3--",operation.tagClass());
	}
	
	public String getQuery17(LdbcSnbBiQuery17FriendshipTriangles operation) {
		return queries.get(QueryType.Query17)
			.replace("--1--",operation.country());
	}
	
	public String getQuery18(LdbcSnbBiQuery18PersonPostCounts operation) {
		return queries.get(QueryType.Query18)
			.replace("--1--",convertDate(operation.date()));
	}
	
	public String getQuery19(LdbcSnbBiQuery19StrangerInteraction operation) {
		return queries.get(QueryType.Query19)
			.replace("--1--",convertDate(operation.date()))
			.replace("--2--", operation.tagClassA())
			.replace("--3--", operation.tagClassB());
	}
	
	public String getQuery20(LdbcSnbBiQuery20HighLevelTopics operation) {
		return queries.get(QueryType.Query20)
			.replace("--1--", convertStringList(operation.tagClasses()));
	}
	
	public String getQuery21(LdbcSnbBiQuery21Zombies operation) {
		return queries.get(QueryType.Query21)
			.replace("--1--", operation.country())
			.replace("--2--",convertDate(operation.endDate()));
	}
	
	public String getQuery22(LdbcSnbBiQuery22InternationalDialog operation) {
		return queries.get(QueryType.Query22)
			.replace("--1--", operation.country1())
			.replace("--2--", operation.country2());
	}
	
	public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
		return queries.get(QueryType.Query23)
			.replace("--1--",operation.country());
	}

	public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
		return queries.get(QueryType.Query24)
			.replace("--1--",operation.tagClass());
	}

	public String getQuery25(LdbcSnbBiQuery25WeightedPaths operation) {
		throw new UnsupportedOperationException( );
//		return queries.get(QueryType.Query25)
//				.replace("--1--",operation.tagClass());
	}
	
	
	private String convertStringList(List<String> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+="'"+values.get(i)+"'";
		}
		return res;
	}


	private String convertDate(long timestamp) {
		return Long.toString(timestamp);
	}
	
	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}
}
