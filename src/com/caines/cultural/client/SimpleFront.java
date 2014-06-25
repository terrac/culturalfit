package com.caines.cultural.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.client.ui.employer.EmployerTopArea;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

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
			basicService.setCurrentGroup(sb.getText(), new AsyncCallback<Void>() {
				
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
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	public final static GreetingServiceAsync basicService = GWT
			.create(GreetingService.class);
	
	public final static EmployerServiceAsync employerService = GWT
			.create(EmployerService.class);
	
	static boolean employer = false;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Button employerSwitch = new Button("Switch to Employer");
		
		RootPanel.get("employerSwitch").add(employerSwitch);
		
		employerSwitch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!employer){
					employerSwitch.setText("Switch to Employee");
					RootPanel.get().clear();
					RootPanel.get().add(new EmployerTopArea());
				} else {
					employerSwitch.setText("Switch to Employer");
					RootPanel.get().clear();
					RootPanel.get().add(new TopArea());
					
				}
				employer = !employer;
			}
		});
		RootPanel.get().add(new TopArea());
		onMyButtonClick();
		
	}
	public static native void onMyButtonClick() /*-{
		$wnd.setupSelectNothing();
	}-*/;
}
