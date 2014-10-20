package com.caines.cultural.server.datautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.caines.cultural.server.SDao;
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
		
		for(CodePointer cp :SDao.getCodePointerDao().getQuery().filter("container", c).filter("user",gu).list()){
			CodeLinkContainer clc = cMap.get(cp.line);
			if(clc == null){
				clc = new CodeLinkContainer();
				cMap.put(cp.line, clc);
				clc.name = cp.line;
			}
			if(!clc.linkedPointers.contains(cp.lineNumber))
				clc.linkedPointers.add(cp.lineNumber);
		}
		return cMap;
	}
}
