package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.jetty.util.ArrayQueue;

import com.caines.cultural.server.SDao;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodeContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeContainer() {

	}
	public List<String> file = new ArrayList<>();
	public Set<String> hs = new HashSet<>();
	public List<String> tags = new ArrayList<>();
	public int nextLink = 0;
	@GwtIncompatible("")
	public void setup(String url, String[] tagsA) {
		this.url = url;
		tags.addAll(Arrays.asList(tagsA));
		SDao.getCodeContainerDao().put(this);
		// ...
		try {
		    URL urlU = new URL(url);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(urlU.openStream()));
		    String line;		    
		     
		    while ((line = reader.readLine()) != null) {
		    	file.add(line);
		    	//System.out.println(line);
		    	//q.add(line);
		    	
		    	if(line.contains("import")||line.trim().startsWith("*")||line.trim().length()==0){
		    		continue;		    		
		    		//q.remove(line);
		    	}
		    	for(String b : line.split("[^A-Za-z0-9]")){
		    		hs.add(b);
		    	}
		        
		    }
		    
		    reader.close();

		} catch (MalformedURLException e) {
		    // ...
		} catch (IOException e) {
		    // ...
		}
		hs.remove("");
	}

	@Id
	public Long id;
	@GwtIncompatible("")
	Ref<CodePath> associatedCodePath;
	public String url;
	public int nextLine;
	
	
	@GwtIncompatible("")
	public Ref<CodeContainer> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeContainer> getRef(Long id) {
		return Ref.create(Key.create(CodeContainer.class, id));
	}

	@GwtIncompatible("")
	public static void main(String[] args) {
		new CodeContainer().setup("https://raw.githubusercontent.com/terrac/rikandroid/master/src/rik/shared/CRPC.java", null);
	}
}
