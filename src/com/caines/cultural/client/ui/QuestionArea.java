package com.caines.cultural.client.ui;

import com.caines.cultural.client.NextQuestion;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ViewGroups;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	int count = 0;

	public QuestionArea(final AsyncCallback<Tuple<Group,Question>> asyncCallback) {
		initWidget(uiBinder.createAndBindUi(this));
		// timer=new ClockTimer(clock);
		ClickHandler ch = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Button l = (Button) event.getSource();
				String answer = l.getText();
				SimpleFront.basicService.answerQuestion(NextQuestion.qRef,
						answer, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								if (count >= 5) {
									TopArea.singleton.setupProfile();
									return;
								}

								SimpleFront.basicService.getNextQuestion(asyncCallback);
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});


			}
		};
		answer1.addClickHandler(ch);
		answer2.addClickHandler(ch);
		progressBar.setProgress(20);
	}

	public void setQuestion(Question result) {
		count++;

		progressBar.setProgress(count * 20);

		question.setInnerText(result.question);

		boolean rand = Math.random() > .5;
		if (result.answer1.equals("Yes")
				|| result.answer2.equals("Yes")) {
			setupAnswer(answer2, "No");
			setupAnswer(answer1, "Yes");

		} else if (result.answer1.equals("Correct")
				|| result.answer2.equals("Correct")) {
			setupAnswer(answer2, "Incorrect");
			setupAnswer(answer1, "Correct");

		} else if (rand) {
			setupAnswer(answer2, result.answer2);
			setupAnswer(answer1, result.answer1);
		} else {
			setupAnswer(answer1, result.answer2);
			setupAnswer(answer2, result.answer1);
		}
		if (TopArea.group.seconds != 0) {
			startClock();
		} else {
			hideClock();
		}

		// timer.scheduleRepeating(1000);
		// timer.count= 60;

	}

	public void setupAnswer(Button b, String answer2) {
		b.getElement().setInnerHTML(
				"<pre style='border:none;background-color:inherit'>"
						+ SafeHtmlUtils.fromString(answer2).asString()
						+ "</pre>");
	}

	@UiField
	PreElement question;

	@UiField
	Button answer1;

	@UiField
	Button answer2;

	@UiField(provided = true)
	ProgressBar progressBar = new ProgressBar(5);

	public void questionsFinished() {
		question.setInnerText("No More Questions For this group");
		// suggest another related group
		answer1.setVisible(false);
		answer2.setVisible(false);
		hideClock();
		TopArea.singleton.setupProfile();
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
