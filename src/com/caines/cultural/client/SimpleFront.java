package com.caines.cultural.client;

import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.client.ui.WelcomePopup;
import com.caines.cultural.client.ui.employer.EmployerTopArea;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
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
					new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
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
		final Anchor logIn = new Anchor("Log In");
		logIn.getElement().setClassName("btn");
		employerSwitch.getElement().setClassName("btn");
		hp.add(logIn);
		hp.add(employerSwitch);
		RootPanel.get("employerSwitch").add(hp);

		employerSwitch.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!employer) {
					moveToEmployer();
				} else {
					moveToJobSeeker();

				}
				employer = !employer;
			}
		});
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {

			@Override
			public void onSuccess(Group result) {
				if (result == null) {
					logIn.setText("Log In");
					return;
				}
				logIn.setText("Log Out");

				TopArea.setGroupName(result.name);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		RootPanel.get().add(new TopArea());
		onMyButtonClick();

		SimpleFront.basicService
				.getLogInOutString(new AsyncCallback<Tuple<String, Boolean>>() {

					@Override
					public void onSuccess(Tuple<String, Boolean> result) {
						if (result.b) {
							logIn.setText("Log In");
						} else {
							logIn.setText("Log Out");
						}
						logIn.setHref(result.a);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
		String checkWelcome = Cookies.getCookie("welcome");
		if (checkWelcome == null) {
			new WelcomePopup().show();
			Cookies.setCookie("welcome", "true");
		}

	}

	public void moveToEmployer() {
		employerSwitch.setText("Switch to Employee");
		RootPanel.get().clear();
		RootPanel.get().add(new EmployerTopArea());
	}

	public void moveToJobSeeker() {
		employerSwitch.setText("Switch to Employer");
		RootPanel.get().clear();
		RootPanel.get().add(new TopArea());
	}

	public static native void onMyButtonClick() /*-{
		$wnd.setupSelectNothing();
	}-*/;

}
