package com.caines.cultural.client.ui;

import com.caines.cultural.client.NextQuestion;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ViewGroups;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuestionArea extends Composite {

	private static QuestionAreaUiBinder uiBinder = GWT
			.create(QuestionAreaUiBinder.class);

	interface QuestionAreaUiBinder extends UiBinder<Widget, QuestionArea> {
	}

	// private final class ClockTimer extends Timer {
	// private final Label clock;
	// int count = 60;
	//
	// private ClockTimer(Label clock) {
	// this.clock = clock;
	// }
	//
	// @Override
	// public void run() {
	// count--;
	// if(count <= 0){
	// cancel();
	// }
	// clock.setText(""+count);
	// }
	// }
	//
	// @UiField
	// Label clock;
	//
	//
	// ClockTimer timer;

	public QuestionArea(final AsyncCallback<Question> asyncCallback) {
		initWidget(uiBinder.createAndBindUi(this));
		answer1.getElement().addClassName("btn");
		// timer=new ClockTimer(clock);
		ClickHandler ch = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Button l = (Button) event.getSource();
				String answer = l.getText();
				SimpleFront.basicService.answerQuestion(NextQuestion.qKey,
						answer, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
				SimpleFront.basicService.getNextQuestion(asyncCallback);
			}
		};
		answer1.addClickHandler(ch);
		answer2.addClickHandler(ch);
	}

	public void setQuestion(Question result) {
		question.setText(result.question);
		if (Math.random() > .5) {
			answer1.setText(result.answer1);
			answer2.setText(result.answer2);
		} else {
			answer1.setText(result.answer2);
			answer2.setText(result.answer1);
		}
		startClock();
		// timer.scheduleRepeating(1000);
		// timer.count= 60;

	}

	@UiField
	Label question;

	@UiField
	Button answer1;

	@UiField
	Button answer2;

	public void questionsFinished() {
		question.setText("No More Questions For this group");
		// suggest another related group
		answer1.setVisible(false);
		answer2.setVisible(false);
		hideClock();
		// Window.alert("No more questions");
		new ViewGroups().groupArea(null);
	}

	public static native void startClock() /*-{
		$wnd.startClock();
	}-*/;

	public static native void hideClock() /*-{
		$wnd.hideClock();
	}-*/;

}
