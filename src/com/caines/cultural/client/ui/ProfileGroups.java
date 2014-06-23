package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
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
	TextBox zipCode;

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
		for (int a = 10; a < 250; a += 10) {
			salary.addItem(a + ",000", "" + a * 100);
		}
		SimpleFront.basicService
				.getUserProfile(new AsyncCallback<UserProfile>() {

					@Override
					public void onSuccess(UserProfile result) {
						userProfile = result;
						for (int a = 0; a < salary.getItemCount(); a++) {
							if (a * 10000 == userProfile.salary) {
								salary.setSelectedIndex(a);
							}
						}
						zipCode.setValue("" + userProfile.zipCode);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
		salary.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				userProfile.salary = Integer.parseInt(salary.getValue(salary
						.getSelectedIndex()));
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
		zipCode.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				userProfile.zipCode = zipCode.getValue();
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
