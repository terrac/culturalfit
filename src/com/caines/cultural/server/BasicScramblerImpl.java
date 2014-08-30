package com.caines.cultural.server;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;

public class BasicScramblerImpl implements BasicScramblerService {

	@Override
	public ScramblerQuestion getNextQuestion() {
		//Pull next from algorithm
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
		SDao.getCodeContainerDao().put(new CodeContainer(url,tagsA));
	}

}
