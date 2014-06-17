package com.caines.cultural.client;

import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.shared.datamodel.Group;
import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ViewGroups {
	VerticalPanel content;

	public void groupArea(final VerticalPanel vp) {
		content = TopArea.content;
		content.clear();
		final Button addGroup = new Button("Add Group");
		
		final ItemSuggestOracle oracle = new ItemSuggestOracle() {
			
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
		};
		final SuggestBox sb = new SuggestBox(oracle);
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {
			
			@Override
			public void onSuccess(Group result) {
				if(result != null){
					sb.setValue(result.name);
					oracle.onNumberSuggestions(1);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
		addGroup.addClickHandler(new GroupAddHandler(sb));
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Groupname:"));
		hp.add(sb);
		hp.add(addGroup);
		content.add(hp);
		
		
		
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
				new GroupEdit().editGroup(content);
				return;
			}
			SimpleFront.basicService.addGroup(sb.getValue(),new AsyncCallback<Void>() {
				
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

}
