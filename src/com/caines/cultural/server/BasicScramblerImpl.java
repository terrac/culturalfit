package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.server.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
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
	public ScramblerQuestion getNextLines() {
		// Pull next from algorithm
		LoginInfo li = login();

		List<CodeContainer> cl = SDao.getCodeContainerDao().getQuery().list();

		CodeContainer c = cl.get(new Random().nextInt(cl.size()));
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
				if (b.contains(nextLink)) {

					if (line1 == -1) {
						line1 = a;
					} else if (line2 == -1) {
						line2 = a;
						c.nextLine = a + 1;
						break;
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
		sq.code1 = c.file.get(line1);
		sq.code2 = c.file.get(line2);
		sq.rawFile = c.file;
		sq.rawFile2 = c.file;
		sq.tag = c.tags.get(0);
		sq.url = ""+c.id;
		li.gUser.cv.cp1 = CodePointer.getCodePointer(c, line1);
		li.gUser.cv.cp2 = CodePointer.getCodePointer(c, line2);
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
		
		SContainer sContainer = new SContainer();
		CodeContainer codeContainer = SDao.getCodeContainerDao().get(Long.parseLong(associatedUrl));
		List<CodePointer> cpList=SDao.getCodePointerDao().getQuery().filter("user",li.gUser).filter("container",codeContainer).list();
		
		sContainer.rawFile=codeContainer.file;
		sContainer.hs = codeContainer.hs;
		List<CodeLinkContainer> cll = new ArrayList<CodeLinkContainer>(CodeLinkContainerUtil.generateCodeLinkContainer(SDao.getRef(codeContainer), li.gUser).values());
		return new Tuple<CodeContainer,List<CodeLinkContainer>>(codeContainer,cll);
	}

	

}
