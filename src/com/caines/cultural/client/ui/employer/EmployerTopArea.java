package com.caines.cultural.client.ui.employer;

import java.util.List;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.client.EmployerServiceAsync;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ui.GroupSelect;
import com.caines.cultural.client.ui.PopupGroupList;
import com.caines.cultural.client.ui.ProfileGroups;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

public class EmployerTopArea extends Composite {

	private static EmployerTopAreaUiBinder uiBinder = GWT
			.create(EmployerTopAreaUiBinder.class);

	interface EmployerTopAreaUiBinder extends UiBinder<Widget, EmployerTopArea> {
	}

	public EmployerTopArea() {
		initWidget(uiBinder.createAndBindUi(this));

		
		SimpleFront.basicService
				.getUserProfile(new AsyncCallback<UserProfile>() {

					@Override
					public void onSuccess(UserProfile result) {
						ProfileGroups.updateProfileData(location, null);
						updateList();

						// zipCode.setValue(result.getZipCodeDisplay());
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				updateList();
			}
		});
	}

	@UiField
	VerticalPanel listGroups;

	@UiField
	Anchor findEmployees;

	@UiField
	ListBox location;

	@UiField
	GroupSelect groupSelect;

	@UiField
	Button submit;

	
	// @UiHandler("submit")
	// void onClick(ChangeEvent e) {
	// updateList();
	// }
	// @UiHandler("radius")
	// void onClick(ChangeEvent e) {
	//
	// }
	// @UiHandler("zipCode")
	// void onClick(ValueChangeEvent<String> e) {
	//
	// }
	public final static EmployerServiceAsync employerService = GWT
			.create(EmployerService.class);

	public void updateList() {
		if (location.getSelectedIndex() == -1) {
			return;
		}
		employerService.getBasicQueryWithFilters(
				location.getValue(location.getSelectedIndex()),
				new AsyncCallback<Tuple<List<UserProfile>, List<UserGroup>>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window.alert("fail");
					}

					@Override
					public void onSuccess(
							Tuple<List<UserProfile>, List<UserGroup>> result) {
						// list-group
						listGroups.clear();
						listGroups.addStyleName("list-group");
						for (int a = 0; a < result.a.size(); a++) {
							Label label = new Label(result.b.get(a)
									.getPercent()
									+ "%  Salary:"
									+ result.a.get(a).getSalaryDisplay());
							final UserProfile up = result.a.get(a);

							label.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									Label l = (Label) event.getSource();
									new PopupGroupList(up).center();
								}
							});
							label.addStyleName("list-group-item list-width");
							listGroups.add(label);
						}

					}
				});
	}

}
