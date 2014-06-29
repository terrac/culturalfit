package com.caines.cultural.client.ui;

import com.caines.cultural.client.GroupEdit;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.ItemSuggestion;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.suggestion.ItemSuggestOracle.ItemSuggestCallback;
import com.caines.cultural.client.ui.callback.SuccessAlertCallback;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestBox.DefaultSuggestionDisplay;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GroupSelect extends Composite {

	private static GroupSelectUiBinder uiBinder = GWT
			.create(GroupSelectUiBinder.class);

	private final class SetGroupOnClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Button b = (Button) event.getSource();
			TopArea.setGroupName(b.getText());
			SimpleFront.basicService.setCurrentGroup(b.getText(), new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	interface GroupSelectUiBinder extends UiBinder<Widget, GroupSelect> {
	}

//	@UiField
//	Button manageGroup;
	@UiField
	HorizontalPanel gContainer;
	
	@UiField
	DivElement wellDiv;
	
	@UiField
	public Button selectGroup;
	boolean suggestHasValidGroup = true;
	@UiField(provided = true) // MAKE SURE YOU HAVE THIS LINE
	public SuggestBox suggestBox;
	
	public GroupSelect() {
		this(true);
	}
	@UiConstructor
	public GroupSelect(boolean hasButtons) {
		final ItemSuggestOracle oracle = new ItemSuggestOracle() {			
			@Override
			public void requestSuggestions(Request request, Callback callback) {
			    SuggestService.Util.getInstance().getGroup(request, new ItemSuggestCallback(request, callback));
			}
		};
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {
			
			
			@Override
			public void onSuccess(Group result) {
				if(result != null){
					suggestBox.setValue(result.name);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
		suggestBox = new SuggestBox(oracle);
		
		suggestBox.getElement().addClassName("input-xlarge search-query");
		
		initWidget(uiBinder.createAndBindUi(this));
		if(!hasButtons){

			wellDiv.setClassName("");
			gContainer.setVisible(false);
			selectGroup.setVisible(false);
		}
		//addGroup.setVisible(false);
		
		String[] buttons = new String[]{"Java Beginner Questions","Java Advanced Questions"};
		for(String a : buttons){
			Button b = new Button(a);
			TopArea.content.add(b);
			b.getElement().setClassName("btn btn-default");
			gContainer.add(b);
			b.addClickHandler(new SetGroupOnClick());
		}
		
		selectGroup.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!suggestHasValidGroup){
					return;
				}
				TopArea.setGroupName(suggestBox.getText());
				SimpleFront.basicService.setCurrentGroup(suggestBox.getText(), new SuccessAlertCallback("Selected"));
			}
		});
	}
	
}
