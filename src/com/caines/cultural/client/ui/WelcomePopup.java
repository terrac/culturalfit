package com.caines.cultural.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class WelcomePopup extends DialogBox {
    private static final Binder binder = GWT.create(Binder.class);
    interface Binder extends UiBinder<Widget, WelcomePopup> {
    }
    
    @UiField
    Button submit;
    
    public WelcomePopup() {
        setWidget(binder.createAndBindUi(this));
        submit.getElement().setClassName("btn btn-primary");
        submit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				WelcomePopup.this.hide();
			}
		});
    }
}