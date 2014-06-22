package com.caines.cultural.client.ui;

import com.caines.cultural.client.GroupEdit;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.ItemSuggestion;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.suggestion.ItemSuggestOracle.ItemSuggestCallback;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public class GroupSelect extends Composite {

	private static GroupSelectUiBinder uiBinder = GWT
			.create(GroupSelectUiBinder.class);

	interface GroupSelectUiBinder extends UiBinder<Widget, GroupSelect> {
	}

	@UiField
	Button addGroup;
	
	@UiField(provided = true) // MAKE SURE YOU HAVE THIS LINE
	SuggestBox suggestBox;
	public GroupSelect() {
		
		final ItemSuggestOracle oracle = new ItemSuggestOracle() {
			@Override
			public void checkExists(Response retValue2) {

				addGroup.setText("Add Group");
				for(Suggestion su:retValue2.getSuggestions()){
					ItemSuggestion isu = (ItemSuggestion) su;
					if(isu.getDisplayString().equals(suggestBox.getText())){
						if(isu.canEdit){
							addGroup.setText("Edit Group");	
						} else {
							addGroup.setText("Created");								
						}
					}
				}
			}
			
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
					addGroup.setText("Edit Group");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
		suggestBox = new SuggestBox(oracle);
		suggestBox.getElement().addClassName("input-xlarge search-query");
		
		initWidget(uiBinder.createAndBindUi(this));
		
		addGroup.addClickHandler(new GroupAddHandler(suggestBox));

	}
	private final class GroupAddHandler implements ClickHandler {
		private final SuggestBox sb;

		private GroupAddHandler(SuggestBox sb) {
			this.sb = sb;
		}

		@Override
		public void onClick(ClickEvent event) {
			Button b = (Button) event.getSource();
			if(b.getText().equals("Edit Group")){
				new GroupEdit().editGroup(TopArea.content);
				return;
			}
			SimpleFront.basicService.addGroup(sb.getValue(),new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					addGroup.setText("Edit Group");
					TopArea.setGroupName(sb.getValue());
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

}
