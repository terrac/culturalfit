package com.caines.cultural.server.datautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caines.cultural.server.SDao;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Ref;

public class CodeLinkContainerUtil {
	
	public static Map<String, CodeLinkContainer> generateCodeLinkContainer(Ref<CodeContainer> c,GUser gu) {
		// 
		Map<String,CodeLinkContainer> cMap = new HashMap<>();
		
		for(CodeLink cl :SDao.getCodeLinkDao().getQuery().filter("codeContainer", c).list()){			
			CodeLinkContainer clc = cMap.get(cl.cp1.line);
			if(clc == null){
				clc = new CodeLinkContainer();
				cMap.put(cl.cp1.line, clc);
				clc.name = cl.cp1.line;
			}
			if(cl.notLinked > cl.linked+ cl.highlyLinked){
				addCL(cl, clc.notLinkedPointers);
			} else if (cl.linked > cl.highlyLinked){				
				addCL(cl, clc.linkedPointers);
			} else {
				addCL(cl, clc.highlyLinkedPointers);
			}
			
		}
		return cMap;
	}

	public static void addCL(CodeLink cl, List<Integer> linkedPointers) {
		if(!linkedPointers.contains(cl.cp1.lineNumber))
			linkedPointers.add(cl.cp1.lineNumber);
		if(!linkedPointers.contains(cl.cp2.lineNumber))
			linkedPointers.add(cl.cp2.lineNumber);
	}
}
