package com.caines.cultural.server;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeQuestionPointer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BasicScramblerImpl extends RemoteServiceServlet implements BasicScramblerService {

	private LoginInfo login() {
		return LoginService.login(getThreadLocalRequest(), getThreadLocalResponse());
	}
	@Override
	public ScramblerQuestion getNextQuestion() {
		//Pull next from algorithm
		LoginInfo li = login();
		CodeQuestionPointer cqp=li.gUser.currentAlgorithm.get().getNextQuestion();
		ScramblerQuestion sq=cqp.getQuestion();
		
		return null;
	}

	@Override
	public void answerQuestion(String id) {
		//Take current question algorithm
		//if current question algorithm correct answer == id then increment answered and set
		//correct answers and total
	}

	@Override
	public void addCodePage(String url, String tags) {
		String[] tagsA = tags.split(" ");
		CodeContainer cc=new CodeContainer();
		cc.setup(url, tagsA);
		SDao.getCodeContainerDao().put(cc);
	}

}
