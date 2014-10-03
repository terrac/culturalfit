package com.caines.cultural.client.ui.codingscramble;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ScrambleProfile extends Composite {

	private static ScrambleProfileUiBinder uiBinder = GWT
			.create(ScrambleProfileUiBinder.class);

	interface ScrambleProfileUiBinder extends UiBinder<Widget, ScrambleProfile> {
	}

	public ScrambleProfile() {
		initWidget(uiBinder.createAndBindUi(this));
		
		SimpleFront.scramblerService.getProfileContent(new AsyncCallback<List<CodeUserDetails>>() {
			
			@Override
			public void onSuccess(List<CodeUserDetails> result) {
				for(CodeUserDetails cud : result){
					VerticalPanel vp = new VerticalPanel();
					profileContent.add(vp);
					HorizontalPanel hp = new HorizontalPanel();
					hp.add(new Label(cud.tag));
					hp.add(new Label("Count:"+cud.tagCount));
					vp.add(hp);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@UiField
	VerticalPanel profileContent;

//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}


}
