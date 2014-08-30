package com.caines.cultural.client;

import com.caines.cultural.client.ui.QuestionArea;
import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NextQuestion {
	
	AsyncCallback<Tuple<Group,Question>> questionCallback = new AsyncCallback<Tuple<Group,Question>>() {
		
		@Override
		public void onSuccess(Tuple<Group,Question> t) {
			if(t== null||t.b == null){
				questionArea.questionsFinished();
				return;
			}
			if(qPanel.getParent() == null){
				initialSetup();
			}
			TopArea.setGroup(t.a);
			qRef = t.b.id;
			questionArea.setQuestion(t.b);
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
	public static Long qRef;
	public void showNextQuestion() {
		
		SimpleFront.basicService.getNextQuestion(questionCallback);
	}
	public void initialSetup() {
		TopArea.content.clear();
		TopArea.content.add(q);
		TopArea.content.add(qPanel);
		TopArea.content.add(questionArea);
	}



}
