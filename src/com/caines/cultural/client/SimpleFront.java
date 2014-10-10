package com.caines.cultural.client;

import com.caines.cultural.client.ui.codingscramble.ScrambleFrontPage;
import com.caines.cultural.client.ui.codingscramble.ScrambleProfile;
import com.caines.cultural.client.ui.codingscramble.ScramblerSubmit;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleFront implements EntryPoint {


	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	public final static BasicScramblerServiceAsync scramblerService = GWT
			.create(BasicScramblerService.class);

	
	public static SimpleFront singleton;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		setupFrontPage();
	}
	public static void setupFrontPage() {
		final RootPanel rootPanel = RootPanel.get("content");
		rootPanel.add(new ScrambleFrontPage());
		
		
		
		
	}
	public static RootPanel setupTopOfSidePage() {
		final RootPanel rootPanel = RootPanel.get("content"); 
		rootPanel.clear();
		Button button = new Button("To Frontpage");
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rootPanel.clear();
				setupFrontPage();
			}
		});
		rootPanel.add(button);
		return rootPanel;
	}
	public static void addUrl() {
		RootPanel.get("content");
		final RootPanel rootPanel = setupTopOfSidePage();
		rootPanel.add(new ScramblerSubmit());
	}
	
	
}
