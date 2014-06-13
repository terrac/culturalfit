package com.caines.cultural.client;

import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.suggestion.ItemSuggestOracle.ItemSuggestCallback;
import com.caines.cultural.shared.FieldVerifier;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;

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
	VerticalPanel content =  new VerticalPanel();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VerticalPanel vp = new VerticalPanel();
		RootPanel.get().add(vp);
		
		HorizontalPanel hp = new HorizontalPanel();
		Button b = new Button("Profile");
		Button b1 = new Button("Add Question");
		Button b2 = new Button("Next Question");
		hp.add(b);
		hp.add(b1);
		hp.add(b2);
		vp.add(hp);
		vp.add(content);
		
		b1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addQuestion(content);
			}
		});
		b2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showNextQuestion(content);
			}
		});
		
	}

	public void addQuestion(final VerticalPanel vp) {
		content.clear();
		final Button addGroup = new Button("Add Group");
		
		final SuggestBox sb = new SuggestBox(new ItemSuggestOracle() {
			
			@Override
			public void onNumberSuggestions(int amount) {
				if(amount == 0){
					addGroup.setText("Add Group");
				} else {
					addGroup.setText("Edit Group");
				}
			}
			@Override
			public void requestSuggestions(Request request, Callback callback) {
			    SuggestService.Util.getInstance().getGroup(request, new ItemSuggestCallback(request, callback));
			}
		});
		addGroup.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				greetingService.addGroup(sb.getValue(),null);
			}
		});
		vp.add(sb);
		
		final TagSelect ts = new TagSelect();
		vp.add(ts);
		final TextArea ta = new TextArea();
		final TextArea ta1 = new TextArea();
		final TextArea ta2 = new TextArea();
		vp.add(ta);
		vp.add(ta1);
		vp.add(ta2);

		SubmitButton sb1 = new SubmitButton("Add Question");
		sb1.addClickHandler(new ClickHandler() {
			
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
		content.clear();
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

		final Label clock = new Label("60");
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
	}
}
