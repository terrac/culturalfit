package com.caines.cultural.server.datautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.caines.cultural.server.SDao;
import com.caines.cultural.server.datamodel.codingscramble.CodeTag;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.google.common.annotations.GwtIncompatible;

public class CodeContainerUtil {
	
	static List<String> javaList = Arrays.asList(new String[]{"com","org","public","class","static","private","final","new","String"});
	static List<String> javaScriptList = Arrays.asList(new String[]{"var","slice", "concat", "push", "indexOf", "function","window", "return", "new", "for","this","document"});
	
	@GwtIncompatible("")
	public static void setup(CodeContainer c,String url, String[] tagsA) {
		List<String> toIgnore = new ArrayList<>();
		if(url.endsWith(".java")){
			toIgnore = javaList;
		}
		if(url.endsWith(".js")){
			toIgnore = javaScriptList;
		}
		
		
		c.url = url;
		List<String> totalTags = new ArrayList<>();
		for(String b : tagsA){
			
			String tag = "";
			for(String d : b.split("/")){
				tag += d+"/";
				totalTags.add(tag.substring(0, tag.length()-1));
			}
		}
		c.tags.addAll(totalTags);
		SDao.getCodeContainerDao().put(c);
		for(String t : c.tags){
			CodeTag ct = SDao.getCodeTagDao().get(t);
			if(ct == null)
				ct =new CodeTag(t);
			ct.codeContainerList.add(SDao.getRef(c));
			SDao.getCodeTagDao().put(ct);
		}
		
		// ...
		try {
		    URL urlU = new URL(url);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(urlU.openStream()));
		    String line;		    
		     
		    while ((line = reader.readLine()) != null) {
		    	c.file.add(line);
		    	//System.out.println(line);
		    	//q.add(line);
		    	
		    	if(line.contains("import")||line.trim().startsWith("*")||line.trim().length()==0||line.contains("//")){
		    		continue;		    		
		    		//q.remove(line);
		    	}
		    	for(String b : line.split("[^A-Za-z0-9]")){
		    		if(!c.hs.contains(b)&&!toIgnore.contains(b))
		    			c.hs.add(b);
		    	}
		        
		    }
		    
		    reader.close();

		} catch (MalformedURLException e) {
		    // ...
		} catch (IOException e) {
		    // ...
		}
		c.hs.remove("");
	}

}
