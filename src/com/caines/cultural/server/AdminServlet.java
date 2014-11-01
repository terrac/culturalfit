package com.caines.cultural.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.server.datautil.CodeContainerUtil;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainerFile;

public class AdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		SDao.getCodeLinkDao().deleteAll(SDao.getCodeLinkDao().getQuery().list());
		SDao.getCodeContainerDao().deleteAll(SDao.getCodeContainerDao().getQuery().list());
		for(CodeContainerFile ccf : SDao.getCodeContainerFileDao().getQuery().list()){
			CodeContainerUtil.setup(url, tagsA);
		}
//		addCodeContainer("https://raw.githubusercontent.com/apache/hadoop-common/trunk/hadoop-mapreduce-project/hadoop-mapreduce-examples/src/main/java/org/apache/hadoop/examples/AggregateWordCount.java", new String[]{"java/hadoop","java/hadoop/aggregateCount"});
//		addCodeContainer("https://raw.githubusercontent.com/apache/mahout/master/examples/src/main/java/org/apache/mahout/cf/taste/example/bookcrossing/BookCrossingDataModel.java", new String[]{"java/hadoop/mahout"});					
//		addCodeContainer("https://raw.githubusercontent.com/jquery/jquery/master/src/core.js", new String[]{"javascript/jquery/core"});
//		addCodeContainer("https://raw.githubusercontent.com/google/flatbuffers/master/src/flatc.cpp", new String[]{"C++/serialization"});
//		addCodeContainer("https://raw.githubusercontent.com/torvalds/linux/master/kernel/locking/locktorture.c", new String[]{"C/linux/kernel"});		
		addCodeContainer("https://raw.githubusercontent.com/ehsan/ogre/master/OgreMain/src/OgreAnimation.cpp", new String[]{"C++/ogre3d/animation"});		
//		addCodeContainer("https://raw.githubusercontent.com/nebez/floppybird/gh-pages/js/main.js", new String[]{"javascript/flappybird","games"});
//		https://raw.githubusercontent.com/NimbusFoundry/Foundry/gh-pages/app/app.js
//			https://raw.githubusercontent.com/nathanmarz/storm-starter/master/src/jvm/storm/starter/WordCountTopology.java	
		
		
		
		
		
		//addCodeContainer("https://github.com/wesnoth/wesnoth/blob/master/src/editor/editor_display.cpp", new String[]{"C++/wesnoth","games"});
		
		
		
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
//		addCodeContainer("", new String[]{""});
		
		
	}

	
	
	public void addCodeContainer(String url, String[] tagsA) {
		CodeContainerUtil.setup(url, tagsA);
	}
}
