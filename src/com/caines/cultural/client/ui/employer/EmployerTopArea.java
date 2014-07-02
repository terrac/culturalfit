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
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EmployerTopArea extends Composite {

	private static EmployerTopAreaUiBinder uiBinder = GWT
			.create(EmployerTopAreaUiBinder.class);

	interface EmployerTopAreaUiBinder extends UiBinder<Widget, EmployerTopArea> {
	}

	public EmployerTopArea() {
		initWidget(uiBinder.createAndBindUi(this));

		submit.getElement().setClassName("btn btn-primary");
		SimpleFront.basicService
				.getUserProfile(new AsyncCallback<SharedUserProfile>() {

					@Override
					public void onSuccess(SharedUserProfile result) {
						ProfileGroups.updateProfileData(location, null,null);
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
				groupSelect.suggestBox.getText()
				,
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
							String text = "("+result.b.get(a)
									.correct
									+ " Correct Answers)  Salary:"
									+ result.a.get(a).getSalaryDisplay()+" "+result.a.get(a).getVacationDisplay();
							final UserProfile up = result.a.get(a);

														//label.addStyleName();
							ClickHandler handler = new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									//Label l = (Label) event.getSource();
									new PopupGroupList(up).center();
								}
							};
							Label link = new Label();
							link.getElement().getStyle().setCursor(Cursor.POINTER); 
							link.getElement().setInnerHTML("<img src='/assets/person.png'>"+text);
							link.getElement().setClassName("list-group-item list-width");
							link.addClickHandler(handler);
							listGroups.add(link);
						}

					}
				});
	}

}
