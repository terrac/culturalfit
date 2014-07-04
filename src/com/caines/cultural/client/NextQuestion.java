package com.caines.cultural.client;

import com.caines.cultural.client.ui.QuestionArea;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NextQuestion {
	
	AsyncCallback<Question> questionCallback = new AsyncCallback<Question>() {
		
		@Override
		public void onSuccess(Question result) {
			if(result == null){
				questionArea.questionsFinished();
				return;
			}
			if(qPanel.getParent() == null){
				initialSetup();
			}
			qRef = result.id;
			questionArea.setQuestion(result);
		}
		
		@Override
		public void onFailure(Throwable caught) {
			
		}
	};

	HorizontalPanel qPanel = new HorizontalPanel();
	final Label q = new Label("");
	final Label qa = new Label("");
	final Label qa2 = new Label("");
	{
		
		qPanel.addStyleName("paddedPanel");
		qPanel.add(qa);
		qPanel.add(qa2);
		questionArea= new QuestionArea(questionCallback);
	}
	QuestionArea questionArea;
	VerticalPanel content = new VerticalPanel();
	public static Long qRef;
	public void showNextQuestion(VerticalPanel vp) {
		if(vp != null){
			content = vp;
		}
		SimpleFront.basicService.getNextQuestion(questionCallback);
	}
	public void initialSetup() {
		content.clear();
		content.add(q);
		content.add(qPanel);
		content.add(questionArea);
	}



}
