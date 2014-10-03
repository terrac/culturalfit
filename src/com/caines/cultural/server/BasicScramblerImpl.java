package com.caines.cultural.server;

import java.util.List;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeQuestionPointer;
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
	public ScramblerQuestion getNextQuestion() {
		// Pull next from algorithm
		LoginInfo li = login();
		if (li.gUser.currentAlgorithm == null) {
			CodeAlgorithm codeAlgorithm = new CodeAlgorithm();
			SDao.getCodeAlgorithmDao().put(codeAlgorithm);
			li.gUser.currentAlgorithm = codeAlgorithm.getRef();
			SDao.getGUserDao().put(li.gUser);
		}
		CodeQuestionPointer cqp = li.gUser.currentAlgorithm.get()
				.getNextQuestion();

		Tuple<ScramblerQuestion, Boolean> t = cqp.getQuestion();
		li.gUser.currentCQP = cqp;
		li.gUser.currentAnswer = t.b;
		SDao.getGUserDao().put(li.gUser);
		return t.a;
	}

	@Override
	public void answerQuestion(String id) {
		LoginInfo li = login();

		boolean success = ("true".equals("id") && li.gUser.currentAnswer)
				|| ("false".equals("id") && !li.gUser.currentAnswer);
		addAnswer(success, li.gUser.currentCQP);
		// Take current question algorithm
		// if current question algorithm correct answer == id then increment
		// answered and set
		// correct answers and total
	}

	private void addAnswer(boolean success, CodeQuestionPointer currentCQP) {
		String mainTag = null;
		LoginInfo li = login();
		
		CodeContainer codeContainer = currentCQP.container.get();
		for (String t :  codeContainer.tags) {
			
			CodeUserDetails cud;
			if (mainTag == null) {
				mainTag = t;
				cud = CodeUserDetails.getByTag(t, mainTag,li);
			} else {
				cud = CodeUserDetails.getByTag(t, mainTag,li);
			}
			int count = cud.tagCount;
			cud.tagCount=count + 1;

			int correct = cud.tagCorrect;
			if (success)
				correct++;
			cud.tagCorrect= correct;

			//average time later
			cud.tagAvgTime = 3.3333;
			SDao.getCodeUserDetailsDao().put(cud);
		}
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

}
