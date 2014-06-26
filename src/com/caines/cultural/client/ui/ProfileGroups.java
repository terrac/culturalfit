package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProfileGroups extends Composite {

	private static ProfileGroupsUiBinder uiBinder = GWT
			.create(ProfileGroupsUiBinder.class);

	interface ProfileGroupsUiBinder extends UiBinder<Widget, ProfileGroups> {
	}

	@UiField
	UListElement listGroups;

	@UiField
	ListBox salary;

	@UiField
	ListBox location;
	
	@UiField
	Button update;

	@UiField
	Button publicButton;
	//
	// @UiField
	// TextBox profileName;
	// <g:Label text="Profile Identifier"></g:Label>
	// <g:TextBox ui:field="profileName"></g:TextBox>

	UserProfile userProfile;

	public ProfileGroups() {
		initWidget(uiBinder.createAndBindUi(this));
		salary.addItem("Not selected", "-1");
		for (int a = 10; a < 250; a += 10) {
			salary.addItem(a + ",000", "" + a * 1000);
		}
		updateProfileData(salary,location);
		SimpleFront.basicService
				.getUserProfile(new AsyncCallback<UserProfile>() {

					@Override
					public void onSuccess(UserProfile result) {
						
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
		
		update.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				userProfile.salary = Integer.parseInt(salary.getValue(salary
						.getSelectedIndex()));
				userProfile.location = Location.getKey(Long.parseLong(location.getValue(location.getSelectedIndex())));
				SimpleFront.basicService.sendProfile(userProfile,
						new AsyncCallback<Void>() {

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
		});

		
		SimpleFront.basicService
				.getUserGroupList(new AsyncCallback<List<UserGroup>>() {

					@Override
					public void onSuccess(List<UserGroup> result) {
						for (UserGroup ug : result) {

							LIElement liElement = Document.get()
									.createLIElement();
							liElement.setInnerHTML(ug.name
									+ "<span class='badge'>" + ug.getPercent()
									+ "%<span class='label'>Answered:"
									+ ug.total + "</span></span>");
							liElement
									.addClassName("list-group-item list-width");
							listGroups.appendChild(liElement);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void updateProfileData(ListBox location,UserProfile userProfile,ListBox salary) {
		SimpleFront.basicService.getProfileData(new AsyncCallback<Tuple<UserProfile,List<Location>>>() {
			
			@Override
			public void onSuccess(Tuple<UserProfile,List<Location>> result) {
				userProfile = (UserProfile) result.a;
				for (int a = 0; a < salary.getItemCount(); a++) {
					if (a * 10000 == userProfile.salary) {
						salary.setSelectedIndex(a);
					}
				}
				for(Location l : ((List<Location>)result.b)){
					location.addItem(l.name,""+ l.id);
				}
				
				String loc = ""+userProfile.location.getId();
				for (int a = 0; a < location.getItemCount(); a++) {
					if(location.getValue(a).equals(loc)){
						location.setSelectedIndex(a);
					}
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// @UiField
	// Button button;
	//
	// public ProfileGroups(String firstName) {
	// initWidget(uiBinder.createAndBindUi(this));
	// button.setText(firstName);
	// }
	//
	// @UiHandler("button")
	// void onClick(ClickEvent e) {
	// Window.alert("Hello!");
	// }

	// public void setText(String text) {
	// button.setText(text);
	// }
	//
	// public String getText() {
	// return button.getText();
	// }
	@UiHandler("publicButton")
	public void onClickPublic(ClickEvent c) {
		Window.Location.assign("/c/profile/"+userProfile.user.getName());
	}
	
}
