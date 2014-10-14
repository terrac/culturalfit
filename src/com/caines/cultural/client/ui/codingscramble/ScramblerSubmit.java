package com.caines.cultural.client.ui.codingscramble;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ScramblerSubmit extends Composite {

	private static ScramblerSubmitUiBinder uiBinder = GWT
			.create(ScramblerSubmitUiBinder.class);

	interface ScramblerSubmitUiBinder extends UiBinder<Widget, ScramblerSubmit> {
	}

	public ScramblerSubmit() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button submit;

	@UiField
	TextBox urlBox;
	
	@UiField
	TextBox tagsBox;
	
	public ScramblerSubmit(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}

	@UiHandler("submit")
	void onClick(ClickEvent e) {
		ScrambleFrontPage.basicService.addCodePage(urlBox.getText(), tagsBox.getText(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				Window.alert("Success");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}



}
