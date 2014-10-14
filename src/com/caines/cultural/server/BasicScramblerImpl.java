package com.caines.cultural.server;

import java.util.List;
import java.util.Random;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLink;
import com.caines.cultural.shared.datamodel.codingscramble.CodePointer;
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
		cc.setup(url, tagsA);
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
			nextLink = c.hs.toArray(new String[0])[c.nextLink];
			
			for (int a = c.nextLine; a < c.file.size(); a++) {
				String b = c.file.get(a);
				if (b.contains(nextLink)) {

					if (line1 == -1) {
						line1 = a;
					} else if (line2 == -1) {
						line2 = a;
						c.nextLine = a+1;
						break;
					}
				}
			}

			if (line2 == -1) {
				line1 = -1;
				c.nextLink++;
				c.nextLine =0;
				
			}
		}

		ScramblerQuestion sq = new ScramblerQuestion();
		sq.linkedText = nextLink;
		sq.code1 = c.file.get(line1);
		sq.code2 = c.file.get(line2);
		sq.rawFile = c.file;
		sq.rawFile2 = c.file;

		li.gUser.cv.cp1 = CodePointer.getCodePointer(c, line1);
		li.gUser.cv.cp2 = CodePointer.getCodePointer(c, line2);
		SDao.getGUserDao().put(li.gUser);
		System.out.println(c.hs);
		System.out.println(nextLink);

		SDao.getCodeContainerDao().put(c);
		return sq;
	}

	@Override
	public void linkCode(boolean next) {
		LoginInfo li = login();

		CodeLink cl = new CodeLink();
		cl.cp1 = li.gUser.cv.cp1;
		cl.cp2 = li.gUser.cv.cp2;
		cl.isNext = next;
		SDao.getCodeLinkDao().put(cl);
		// for (String t : codeContainer.tags) {
		//
		// CodeUserDetails cud;
		// if (mainTag == null) {
		// mainTag = t;
		// cud = CodeUserDetails.getByTag(t, mainTag,li);
		// } else {
		// cud = CodeUserDetails.getByTag(t, mainTag,li);
		// }
		// int count = cud.tagCount;
		// cud.tagCount=count + 1;
		//
		// int correct = cud.tagCorrect;
		// if (success)
		// correct++;
		// cud.tagCorrect= correct;
		//
		// //average time later
		// cud.tagAvgTime = 3.3333;
		// SDao.getCodeUserDetailsDao().put(cud);
	}

}
