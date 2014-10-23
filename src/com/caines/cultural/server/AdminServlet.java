package com.caines.cultural.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.server.datautil.CodeContainerUtil;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;

public class AdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		addCodeContainer("https://raw.githubusercontent.com/apache/hadoop-common/trunk/hadoop-mapreduce-project/hadoop-mapreduce-examples/src/main/java/org/apache/hadoop/examples/AggregateWordCount.java", new String[]{"java/hadoop","java/hadoop/aggregateCount"});
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/anagrams/AnagramJob.java", new String[]{"java/hadoop/anagram"});
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/anagrams/AnagramMapper.java", new String[]{"java/hadoop/anagram"});
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/anagrams/AnagramReducer.java", new String[]{"java/hadoop/anagram"});
		
		
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/geolocation/GeoLocationJob.java", new String[]{"java/hadoop/GeoLocation"});
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/geolocation/GeoLocationMapper.java", new String[]{"java/hadoop/GeoLocation"});
		addCodeContainer("https://hadoop-map-reduce-examples.googlecode.com/svn/trunk/hadoop-examples/src/com/hadoop/examples/geolocation/GeoLocationReducer.java", new String[]{"java/hadoop/GeoLocation"});
		
		addCodeContainer("https://raw.githubusercontent.com/jquery/jquery/master/src/core.js", new String[]{"jquery/core"});
		
		
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
		
		
	}

	
	
	public void addCodeContainer(String url, String[] tagsA) {
		CodeContainer cc = new CodeContainer();
		CodeContainerUtil.setup(cc,url, tagsA);
		SDao.getCodeContainerDao().put(cc);
	}
}
