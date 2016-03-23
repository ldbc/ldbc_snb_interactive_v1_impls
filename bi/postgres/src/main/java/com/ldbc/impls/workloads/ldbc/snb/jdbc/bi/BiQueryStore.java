package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

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
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;

public class BiQueryStore {
	public static enum QueryType { 
		Query1("query1.txt"),
		Query2("query2.txt"),
		Query3("query3.txt"),
		Query4("query4.txt"),
		Query5("query5.txt"),
		Query6("query6.txt"),
		Query7("query7.txt"),
		Query8("query8.txt"),
		Query9("query9.txt"),
		Query10("query10.txt"),
		Query12("query12.txt"),
		Query13("query13.txt"),
		Query14("query14.txt"),
		Query15("query15.txt"),
		Query16("query16.txt"),
		Query17("query17.txt"),
		Query18("query18.txt"),
		Query19("query19.txt"),
		Query20("query20.txt"),
		Query21("query21.txt"),
		Query22("query22.txt"),
		Query23("query23.txt"),
		Query24("query24.txt"),;
		
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
			.replace("--1--", convertDate(operation.dateA()))
			.replace("--2--", convertDate(operation.dateB()))
			.replace("--3--", convertStringList(operation.countries()))
			.replace("--4--", convertDate(operation.endOfSimulationTime()))
			.replace("--5--", operation.messageThreshold()+"");
	}
	
	public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
		return queries.get(QueryType.Query3)
			.replace("--1--", convertDate(operation.range1Start()))
			.replace("--2--", convertDate(operation.range1End()))
			.replace("--3--", convertDate(operation.range2Start()))
			.replace("--4--", convertDate(operation.range2End()));
	}
	
	public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
		return queries.get(QueryType.Query4)
			.replace("--1--", operation.tagClass()+"")
			.replace("--2--", operation.country()+"");
	}
	
	public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
		return queries.get(QueryType.Query5)
			.replace("--1--", operation.country()+"");
	}
	
	public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
		return queries.get(QueryType.Query6)
			.replace("--1--", operation.tag()+"");
	}
	
	public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
		return queries.get(QueryType.Query7)
			.replace("--1--", operation.tag()+"");
	}
	
	public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
		return queries.get(QueryType.Query8)
			.replace("--1--", operation.tag()+"");
	}
	
	public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
		return queries.get(QueryType.Query9)
			.replace("--1--", operation.tagClassA()+"")
			.replace("--2--", operation.tagClassB()+"")
			.replace("--3--", operation.threshold()+"");
	}
	
	public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
		return queries.get(QueryType.Query10)
			.replace("--1--", operation.tag()+"");
	}
	
	public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
		return queries.get(QueryType.Query12)
			.replace("--1--",convertDate(operation.date()))
			.replace("--2--", operation.likeCount()+"");
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
			.replace("--1--",operation.person()+"")
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
			.replace("--2--", operation.tagClassA()+"")
			.replace("--3--", operation.tagClassB()+"");
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
			.replace("--1--", operation.countryX())
			.replace("--2--", operation.countryY());
	}
	
	public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
		return queries.get(QueryType.Query23)
			.replace("--1--",operation.country());
	}

	public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
		return queries.get(QueryType.Query24)
			.replace("--1--",operation.tagClass());
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
		//return "to_timestamp("+timestamp+")::timestamp";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return "'"+sdf.format(new Date(timestamp))+"'::timestamp";
	}
	
	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}
}
