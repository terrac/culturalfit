package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PopupGroupList extends PopupPanel {

	public PopupGroupList(final UserProfile up) {		
		super(true);
		final VerticalPanel vp = new VerticalPanel();
		setWidget(vp);
		
		vp.addStyleName("list-group");
		SimpleFront.basicService.getUserGroupList(up.user,new AsyncCallback<List<UserGroup>>() {
			
			@Override
			public void onSuccess(List<UserGroup> result) {
				for(UserGroup ug: result){
					Label label = new Label(ug.name+" "+ug.getPercent()+"%");					
					label.addStyleName("list-group-item list-width");
					vp.add(label);
				}
				
				setupButtons(vp);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void setupButtons(final VerticalPanel vp) {
		HorizontalPanel hp = new HorizontalPanel();

		Button b = new Button("Contact");
		hp.add(b);
		
		Button c = new Button("Close");
		hp.add(c);
		
		b.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Not implemented!");
			}
		});
		c.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopupGroupList.this.hide();
			}
		});
		vp.add(hp);
	}

	public PopupGroupList() {
		
	}

}
