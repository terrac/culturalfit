package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.client.SimpleFront.SetGroup;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.ui.QuestionArea;
import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NextQuestion {
	

	abstract static class SetupTopGroups implements AsyncCallback<List<Group>> {
		HorizontalPanel hp = new HorizontalPanel();
		

		@Override
		public void onSuccess(List<Group> result) {
			for(Group g : result){
				final Button b = new Button();
				b.setText(g.name);
				b.addClickHandler(new SetGroup(b){
					@Override
					void onSuccess() {
						new NextQuestion().showNextQuestion(TopArea.content);
					}
				});
				hp.add(b);
			}
			addPage(hp);
		}
		
		public abstract void addPage(HorizontalPanel hp);
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
	}

	
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
		
		showOrSelectGroups(vp);
		
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

	public static void showOrSelectGroups(VerticalPanel vp) {
		final Button addGroup = new Button("Select Group");
		
		final SuggestBox sb = new SuggestBox(new ItemSuggestOracle() {
			
			@Override
			public void requestSuggestions(Request request, Callback callback) {
			    SuggestService.Util.getInstance().getGroup(request, new ItemSuggestCallback(request, callback));
			}
		});
		final SetupTopGroups callback = new SetupTopGroups() {
			
			@Override
			public void addPage(HorizontalPanel hp) {
				hp.add(sb);
				hp.add(addGroup);
			}
		};
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {
			
			@Override
			public void onSuccess(Group result) {
				if(result != null){
					callback.hp.add(new Label(result.name));
				} else {
					SimpleFront.basicService.getTopGroups(callback);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

		vp.add(callback.hp);
		addGroup.addClickHandler(new SetGroup(sb){
			void onSuccess(){}
		});
	}

}
