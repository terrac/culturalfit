package com.caines.cultural.client;

import com.caines.cultural.client.ui.QuestionArea;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NextQuestion {
	

	
	final Label q = new Label("");
	final Label qa = new Label("");
	final Label qa2 = new Label("");
	
	QuestionArea questionArea;
	VerticalPanel content = new VerticalPanel();
	
	public void showNextQuestion(VerticalPanel vp) {
		if(vp != null){
			content = vp;
		}
		content.clear();
		
		
		content.add(q);
		HorizontalPanel qPanel = new HorizontalPanel();
		qPanel.addStyleName("paddedPanel");
		qPanel.add(qa);
		qPanel.add(qa2);
		content.add(qPanel);

				
		final AsyncCallback<Question> questionCallback = new AsyncCallback<Question>() {
			
			@Override
			public void onSuccess(Question result) {
				if(result == null){
					questionArea.questionsFinished();
					return;
				}
				questionArea.setQuestion(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		};
		questionArea= new QuestionArea(questionCallback);
		content.add(questionArea);
		
		
		
		
		SimpleFront.basicService.getNextQuestion(questionCallback);


	}



}
