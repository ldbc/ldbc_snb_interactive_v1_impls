package com.ldbc.impls.workloads.ldbc.snb.bi.jdbc.db;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
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
		Query10("query10.txt");		
		
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
			.replace("--1--", operation.date()+"");
	}
	
	
	public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
		return queries.get(QueryType.Query2)
			.replace("--1--", operation.dateA()+"")
			.replace("--2--", operation.dateB()+"");
	}
	
//	public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
//		return queries.get(QueryType.Query3)
//			.replace("--1--", operation.year()+"")
//			.replace("--2--", operation.month()+"");
//	}
	
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
			.replace("--2--", operation.tagClassB()+"");
	}
	
	public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
		return queries.get(QueryType.Query9)
			.replace("--1--", operation.tag()+"");
	}
	
//	
//	private String convertDate(Date date) {
//		return date.toString();
//	}
	
	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}
}
