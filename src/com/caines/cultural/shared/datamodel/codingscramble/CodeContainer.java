package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.jetty.util.ArrayQueue;

import com.caines.cultural.server.SDao;
import com.google.common.annotations.GwtIncompatible;
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
	public List<String> tags = new ArrayList<>();
	public void setup(String url, String[] tagsA) {
		tags.addAll(Arrays.asList(tagsA));
		SDao.getCodeContainerDao().put(this);
		// ...
		try {
		    URL urlU = new URL(url);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(urlU.openStream()));
		    String line;
		    Queue<String> q = new ArrayBlockingQueue<>(10);
		    int count = 0;		    
		     
		    while ((line = reader.readLine()) != null) {
		    	file.add(line);
		    	//System.out.println(line);
		    	q.add(line);
		    	if(q.size() > 5){
		    		q.remove();
		    		int success = 3;
		    		for(String a : q){
			        	if(a.trim().length() < 20){
			        		success--;
			        	}
			        }
		    		if(success >0){
		    			CodeQuestionPointer cqp = new CodeQuestionPointer(getRef(),count-5);
		    			SDao.getCodeQuestionPointerDao().put(cqp);
		    			for(String a :q){
		    				System.out.println(a);	
		    			}
		    			
		    			System.out.println("aoue");
		    			q.clear();
		    			//add questionpointer at the top line
		    		}
		    	}
		    	count++;
		        
		    }
		    
		    reader.close();

		} catch (MalformedURLException e) {
		    // ...
		} catch (IOException e) {
		    // ...
		}
		
	}

	@Id
	public Long id;

	Ref<CodePath> associatedCodePath;
	
	
	@GwtIncompatible("")
	public Ref<CodeContainer> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeContainer> getRef(Long id) {
		return Ref.create(Key.create(CodeContainer.class, id));
	}

	
	public static void main(String[] args) {
		new CodeContainer().setup("https://raw.githubusercontent.com/terrac/rikandroid/master/src/rik/shared/CRPC.java", null);
	}
}
