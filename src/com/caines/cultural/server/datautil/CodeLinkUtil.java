package com.caines.cultural.server.datautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.caines.cultural.server.SDao;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Ref;

public class CodeLinkUtil {

	public static CodeLink getCodeLink(CodePointer cp1, CodePointer cp2) {
		CodeLink cl=SDao.getCodeLinkDao().get(""+cp1.id+cp2.id);
		if(cl == null){
			cl = new CodeLink();
			cl.cp1 = cp1;
			cl.cp2 = cp2;
			cl.id = ""+cp1.id+cp2.id;
			SDao.getCodeLinkDao().put(cl);
		}
		return cl;
	}
}
