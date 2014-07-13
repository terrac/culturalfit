package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ui.callback.SuccessAlertCallback;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ProfileGroups extends Composite {

	private static ProfileGroupsUiBinder uiBinder = GWT
			.create(ProfileGroupsUiBinder.class);

	private static final class SetupProfileData implements
			AsyncCallback<Tuple<SharedUserProfile, List<Location>>> {
		private ListBox locat;
		private ListBox salary;
		private ListBox vacation;

		public SetupProfileData(ListBox locat, ListBox salary, ListBox vacation) {
			super();
			this.locat = locat;
			this.salary = salary;
			this.vacation = vacation;
		}

		@Override
		public void onSuccess(Tuple<SharedUserProfile, List<Location>> result) {
			for (Location l : ((List<Location>) result.b)) {
				locat.addItem(l.name, "" + l.id);
			}

			SharedUserProfile uP = result.a;
			if (uP == null) {
				return;
			}
			// user = uP.user;
			if (salary != null) {
				for (int a = 0; a < salary.getItemCount(); a++) {
					if (a * 10000 == uP.salary) {
						salary.setSelectedIndex(a);
					}
				}
			}

			if (vacation != null) {
				for (int a = 0; a < vacation.getItemCount(); a++) {
					if (a == uP.vacation) {
						vacation.setSelectedIndex(a);
					}
				}
			}

			String loc = "" + uP.location;
			for (int a = 0; a < locat.getItemCount(); a++) {
				if (locat.getValue(a).equals(loc)) {
					locat.setSelectedIndex(a);
				}
			}
			userId = uP.userid;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			caught.printStackTrace();
			Window.alert("fail");
		}
	}

	interface ProfileGroupsUiBinder extends UiBinder<Widget, ProfileGroups> {
	}

	@UiField
	UListElement listGroups;

	@UiField
	FlexTable listG;

	@UiField
	ListBox salary;

	@UiField
	ListBox location;

	@UiField
	ListBox vacation;

	@UiField
	Button update;

	@UiField
	Button publicButton;

	//
	// @UiField
	// TextBox profileName;
	// <g:Label text="Profile Identifier"></g:Label>
	// <g:TextBox ui:field="profileName"></g:TextBox>

	public ProfileGroups() {
		initWidget(uiBinder.createAndBindUi(this));
		salary.addItem("Not selected", "-1");
		for (int a = 10; a < 250; a += 10) {
			salary.addItem(NumberFormat.getSimpleCurrencyFormat().overrideFractionDigits(0).format(a*1000), "" + a * 1000);
		}

		vacation.addItem("Not selected", "-1");
		for (int a = 6; a < 9; a += 1) {
			vacation.addItem(a + " Weeks", "" + a);
		}
		updateProfileData(location, salary, vacation);

		update.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				int salaryVal = Integer.parseInt(salary.getValue(salary
						.getSelectedIndex()));
				long lVal = Long.parseLong(location.getValue(location
						.getSelectedIndex()));
				SimpleFront.basicService.sendProfile(salaryVal, lVal,
						new SuccessAlertCallback("Updated"));

			}
		});

		SimpleFront.basicService
				.getUserGroupList(new AsyncCallback<List<UserGroup>>() {

					@Override
					public void onSuccess(List<UserGroup> result) {
						if(result == null){
							return;
						}
						listG.getElement().setClassName("list-group");
						listG.setWidth("50%");
						int count = 0;
						for (final UserGroup ug : result) {
							String label = getColorLabel(ug);
//							LIElement liElement = Document.get()
//									.createLIElement();
//							liElement.setInnerHTML();
//							liElement
//									.addClassName();
//							//liElement.appendChild(new Button("Reanswer").getElement());
//							AnchorElement a = Document.get().createAnchorElement();
//							a.appendChild(liElement);
//							a.setAttribute("href", "#");
							
							Label an = new Label();
							an.getElement().setInnerHTML(ug.name+" <span class=badge>"+ug.getPercent()+"%</span>");
							an.getElement().setClassName("list-group-item list-group-item-"+label);
							listG.setWidget(count, 0, an);
							Label an2 = new Label("Answered:"+ug.total);
							an2.getElement().setClassName("list-group-item");
							listG.setWidget(count, 1, an2);
							
							Label an3 = new Label("Try:"+ug.tries);
							an3.getElement().setClassName("list-group-item");
							listG.setWidget(count, 2, an3);
							
							if(ug.getPercent() < 80){
								Button button = new Button("Reanswer");
								button.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										SimpleFront.basicService.addTry(ug.id, new AsyncCallback<Group>() {

											@Override
											public void onFailure(
													Throwable caught) {
												
											}

											@Override
											public void onSuccess(Group result) {
												TopArea.setGroup(result);
												TopArea.singleton.nextQuestion();
											}
										});
									}
								});
								button.getElement().setClassName("btn");
								listG.setWidget(count, 3, button);
								//hp.getWidget(1).getElement().getStyle().setPadding(5, Unit.EM);
							}
							count++;
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void updateProfileData(final ListBox locat,
			final ListBox salary, ListBox vacation) {
		SimpleFront.basicService.getProfileData(new SetupProfileData(locat,
				salary, vacation));
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
	static String userId;

	@UiHandler("publicButton")
	public void onClickPublic(ClickEvent c) {

		Window.Location.assign("/c/profile/" + userId);
	}

	public static String getColorLabel(UserGroup ug) {
		String label = "danger";
		if(ug.getPercent() > 75){
			label = "success";
		}
		else if(ug.getPercent() > 50){
			label = "info";							
		}
		else if(ug.getPercent() > 25){
			label = "warning";
		}
		return label;
	}

}
