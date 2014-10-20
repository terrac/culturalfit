package com.caines.cultural.server.datautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.google.common.annotations.GwtIncompatible;

public class CodeContainerUtil {
	@GwtIncompatible("")
	public static void setup(CodeContainer c,String url, String[] tagsA) {
		c.url = url;
		c.tags.addAll(Arrays.asList(tagsA));
		SDao.getCodeContainerDao().put(c);
		// ...
		try {
		    URL urlU = new URL(url);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(urlU.openStream()));
		    String line;		    
		     
		    while ((line = reader.readLine()) != null) {
		    	c.file.add(line);
		    	//System.out.println(line);
		    	//q.add(line);
		    	
		    	if(line.contains("import")||line.trim().startsWith("*")||line.trim().length()==0){
		    		continue;		    		
		    		//q.remove(line);
		    	}
		    	for(String b : line.split("[^A-Za-z0-9]")){
		    		if(!c.hs.contains(b))
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
