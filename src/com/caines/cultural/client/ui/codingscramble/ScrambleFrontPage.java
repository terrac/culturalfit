package com.caines.cultural.client.ui.codingscramble;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.client.BasicScramblerServiceAsync;

import com.caines.cultural.shared.container.ScramblerQuestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ScrambleFrontPage extends Composite {

	public final static BasicScramblerServiceAsync basicService = GWT
			.create(BasicScramblerService.class);

	private static ScrambleFrontPageUiBinder uiBinder = GWT
			.create(ScrambleFrontPageUiBinder.class);

	interface ScrambleFrontPageUiBinder extends
			UiBinder<Widget, ScrambleFrontPage> {
	}

	public ScrambleFrontPage() {
		initWidget(uiBinder.createAndBindUi(this));
		update();

	}

	@UiField
	Button topButton;
	@UiField
	Button bottomButton;

	public void update() {
		

		// 5 or 10 lines above and below the scrambled area
		//
		basicService.getNextQuestion(new AsyncCallback<ScramblerQuestion>() {
			
			@Override
			public void onSuccess(ScramblerQuestion result) {
				Element preElement = DOM.getElementById("code");
				preElement.setInnerText("");
				for(String a : result.q1){
					preElement.setInnerText(preElement.getInnerText()+a+"\n");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@UiHandler("topButton")
	void onClick(ClickEvent e) {
		basicService.answerQuestion("Correct", new AsyncCallback<Void> (){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				update();
			}});
	}

	@UiHandler("bottomButton")
	void onClickBottom(ClickEvent e) {
		basicService.answerQuestion("Incorrect", new AsyncCallback<Void> (){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				update();
			}});		
	}

}
