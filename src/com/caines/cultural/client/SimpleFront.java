package com.caines.cultural.client;

import com.caines.cultural.shared.FieldVerifier;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleFront implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final VerticalPanel vp = new VerticalPanel();
		RootPanel.get().add(vp);
		if(true){
			addQuestion(vp);
		}
		if(false){
			showNextQuestion(vp);
		}
		
	}

	public void addQuestion(final VerticalPanel vp) {
		final TagSelect ts = new TagSelect();
		vp.add(ts);
		final TextArea ta = new TextArea();
		final TextArea ta1 = new TextArea();
		final TextArea ta2 = new TextArea();
		final Label clock = new Label("60");
		vp.add(ta);
		vp.add(ta1);
		vp.add(ta2);
		vp.add(clock);
		new Timer() {
			int count = 60;
			@Override
			public void run() {
				count--;
				if(count <= 0){
					cancel();
				}
				clock.setText(""+count);
			}
		}.scheduleRepeating(1000);;;
		SubmitButton sb = new SubmitButton("Add Question");
		sb.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				greetingService.addQuestion(ts.tags.getText(),ta.getText(),ta1.getText(),ta2.getText(),new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						//what
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}

	public void showNextQuestion(final VerticalPanel vp) {
		final Label q = new Label("");
		final Label qa = new Label("");
		final Label qa2 = new Label("");
		vp.add(q);
		vp.add(qa);
		vp.add(qa2);
		
		
		final AsyncCallback<Question> questionCallback = new AsyncCallback<Question>() {
			
			@Override
			public void onSuccess(Question result) {
				q.setText(result.question);
				qa.setText(result.answer1);
				qa2.setText(result.answer2);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		};
		greetingService.getNextQuestion(questionCallback);
		ClickHandler ch = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Label l = (Label) event.getSource();
				String answer=l.getElement().getAttribute("answerId");
				greetingService.answerQuestion(answer, null);
				greetingService.getNextQuestion(questionCallback);
			}
		};
		qa.addClickHandler(ch);
		qa2.addClickHandler(ch);
		
	}
}
