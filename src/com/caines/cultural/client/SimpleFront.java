package com.caines.cultural.client;

import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.client.ui.employer.EmployerTopArea;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleFront implements EntryPoint {

	public static abstract class SetGroup implements ClickHandler {
		private final HasText sb;

		public SetGroup(HasText sb) {
			this.sb = sb;
		}

		public abstract void onSuccess();

		@Override
		public void onClick(ClickEvent event) {
			basicService.setCurrentGroup(sb.getText(),
					new AsyncCallback<Group>() {

						@Override
						public void onSuccess(Group result) {
							SetGroup.this.onSuccess();
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
		}
	}

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	public final static GreetingServiceAsync basicService = GWT
			.create(GreetingService.class);

	static boolean employer = false;
	Button employerSwitch = new Button("Switch to Employer");
	public static SimpleFront singleton;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		singleton = this;
		HorizontalPanel hp = new HorizontalPanel();
		final Anchor temporary = new Anchor("Make Permanent");
		final Anchor logIn = new Anchor("Log In");
		temporary.getElement().setClassName("btn btn-success");
		logIn.getElement().setClassName("btn");
		hp.add(temporary);
		String temporary = Cookies.getCookie("temporary");
		temporary.setVisible("true".equals(temporary));
		hp.add(logIn);
		
//		employerSwitch.getElement().setClassName("btn");		
//		hp.add(employerSwitch);
		RootPanel.get("employerSwitch").add(hp);

//		employerSwitch.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				if (!employer) {
//					moveToEmployer();
//				} else {
//					moveToJobSeeker();
//
//				}
//				employer = !employer;
//			}
//		});
		//SimpleFront.basicService.getCurrentGroup(new SetupTopArea(logIn));
		TopArea ta = new TopArea();
		if(ta.setup()){
			RootPanel.get("top").add(ta);
		}
		onMyButtonClick();

		SimpleFront.basicService
				.getLogInOutString(new AsyncCallback<Tuple<Group,Tuple<String, Boolean>>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(
							Tuple<Group, Tuple<String, Boolean>> result) {
						if (result.b.b) {
							logIn.setText("Log In");
						} else {
							logIn.setText("Log Out");
						}
						logIn.setHref(result.b.a);
						TopArea.setGroupName(result.a.name);
						TopArea.setGroup(result.a);
					}

				});
//		String checkWelcome = Cookies.getCookie("welcome");
//		if (checkWelcome == null) {
//			new WelcomePopup().show();
//			Cookies.setCookie("welcome", "true");
//		}

	}

	public void moveToEmployer() {
		employerSwitch.setText("Switch to Employee");
		RootPanel.get("top").clear();
		RootPanel.get("top").add(new EmployerTopArea());
	}

	public void moveToJobSeeker() {
		employerSwitch.setText("Switch to Employer");
		RootPanel.get("top").clear();
		TopArea ta = new TopArea();
		ta.setup();
		RootPanel.get("top").add(ta);
	}

	public static native void onMyButtonClick() /*-{
		$wnd.setupSelectNothing();
	}-*/;

}
