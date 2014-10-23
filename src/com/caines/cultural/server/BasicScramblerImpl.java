package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.server.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.caines.cultural.server.datamodel.codingscramble.CodeTag;
import com.caines.cultural.server.datamodel.codingscramble.history.UserRecordingLink;
import com.caines.cultural.server.datautil.CodeContainerUtil;
import com.caines.cultural.server.datautil.CodeLinkContainerUtil;
import com.caines.cultural.server.datautil.CodeLinkUtil;
import com.caines.cultural.server.datautil.HistoryUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.container.SContainer;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class BasicScramblerImpl extends RemoteServiceServlet implements
		BasicScramblerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LoginInfo login() {
		return LoginService.login(getThreadLocalRequest(),
				getThreadLocalResponse());
	}

	@Override
	public void addCodePage(String url, String tags) {
		String[] tagsA = tags.split("[,\\s]");
		CodeContainer cc = new CodeContainer();
		CodeContainerUtil.setup(cc,url, tagsA);
		SDao.getCodeContainerDao().put(cc);
	}

	@Override
	public List<CodeUserDetails> getProfileContent() {

		return CodeUserDetails.getByUser(login());
	}

	@Override
	public UserInfo getUserInfo() {

		LoginInfo login = login();
		if (login == null) {
			return null;
		}

		UserInfo userInfo = new UserInfo();
		// userInfo.isAdmin = login.gUser.isAdmin();
		userInfo.isAdmin = true;
		return userInfo;
	}

	@Override
	public ScramblerQuestion getNextLines(String tag) {
		// Pull next from algorithm
		LoginInfo li = login();

		CodeTag ct = SDao.getCodeTagDao().get(tag);
		CodeContainer c = null;
		
		if(ct != null){
			c = ct.codeContainerList.get(new Random().nextInt(ct.codeContainerList.size())).get();
		} else {
			List<CodeContainer> cl = SDao.getCodeContainerDao().getQuery().list();
			c = cl.get(new Random().nextInt(cl.size()));
		}
		 
		 
		// run through h

		int line1 = -1;
		int line2 = -1;
		String nextLink = null;
		while (line2 == -1) {
			if (c.hs.size() <= c.nextLink) {
				c.nextLink = 0;
			}
			nextLink = c.hs.get(c.nextLink);

			for (int a = c.nextLine; a < c.file.size(); a++) {
				String b = c.file.get(a);
				String btrim = b.trim();
				if(btrim.startsWith("import")||btrim.startsWith("package")||b.contains("//")){
					continue;
				}
				if (b.contains(nextLink)) {
					boolean tooSmall = false;
					if(nextLink.length() < 3){
						tooSmall = true;
						for(String m : btrim.split("[^A-Za-z0-9]")){
							if(m.equals(nextLink)){
								tooSmall = false;
								break;
							}
						}
					}
					if(!tooSmall){
						if (line1 == -1) {
							line1 = a;
						} else if (line2 == -1) {
							line2 = a;
							c.nextLine = a + 1;
							break;
						}	
					}
					
				}
			}

			if (line2 == -1) {
				line1 = -1;
				c.nextLink++;
				c.nextLine = 0;

			}
		}

		ScramblerQuestion sq = new ScramblerQuestion();
		sq.linkedText = nextLink;
		sq.line1 = line1;
		sq.line2 = line2;
		sq.rawFile = c.file;
		sq.rawFile2 = c.file;
		sq.tag = c.tags.get(0);
		sq.filename = c.url.substring(c.url.lastIndexOf("/"));
		sq.url = ""+c.id;
		li.gUser.cv.cp1 = CodePointer.getCodePointer(c, line1,nextLink);
		li.gUser.cv.cp2 = CodePointer.getCodePointer(c, line2,nextLink);
		//List<Key<CodeTag>> list = SDao.getCodeTagDao().getQuery().limit(1000).keys().list();
		List<Key<CodeTag>> list1 = SDao.getCodeTagDao().getQuery().filter("main",true).limit(1000).keys().list();
		sq.tag1 = SDao.getCodeTagDao().get(list1.get(new Random().nextInt(list1.size())).getName()).tag;
		sq.tag2 = SDao.getCodeTagDao().get(list1.get(new Random().nextInt(list1.size())).getName()).tag;
		SDao.getGUserDao().put(li.gUser);
		System.out.println(c.hs);
		System.out.println(nextLink);

		SDao.getCodeContainerDao().put(c);
		return sq;
	}

	@Override
	public void linkCode(String linkType) {
		LoginInfo li = login();

		CodeLink cl=CodeLinkUtil.getCodeLink(li.gUser.cv.cp1,li.gUser.cv.cp2);
		if("highlyLinked".equals(linkType)){
			cl.highlyLinked++;
		}
		if("linked".equals(linkType)){
			cl.linked++;
		}
		if("notLinked".equals(linkType)){
			cl.notLinked++;
		}
		UserRecordingLink url=HistoryUtil.getHistory(cl,li.gUser);
		
	}

	@Override
	public Tuple<CodeContainer, List<CodeLinkContainer>> getContainer(String associatedUrl) {
		LoginInfo li = login();
		
		CodeContainer codeContainer = SDao.getCodeContainerDao().get(Long.parseLong(associatedUrl));
		
		List<CodeLinkContainer> cll = new ArrayList<CodeLinkContainer>(CodeLinkContainerUtil.generateCodeLinkContainer(SDao.getRef(codeContainer), li.gUser).values());
		return new Tuple<CodeContainer,List<CodeLinkContainer>>(codeContainer,cll);
	}

	

}
