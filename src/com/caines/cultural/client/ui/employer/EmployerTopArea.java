package com.caines.cultural.client.ui.employer;

import java.util.List;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.client.EmployerServiceAsync;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ui.GroupSelect;
import com.caines.cultural.client.ui.ProfileGroups;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EmployerTopArea extends Composite {

	private static EmployerTopAreaUiBinder uiBinder = GWT
			.create(EmployerTopAreaUiBinder.class);

	interface EmployerTopAreaUiBinder extends UiBinder<Widget, EmployerTopArea> {
	}

	public EmployerTopArea() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		SimpleFront.basicService
		.getUserProfile(new AsyncCallback<UserProfile>() {

			@Override
			public void onSuccess(UserProfile result) {
				ProfileGroups.setSelectedLocation(result, location);
				//zipCode.setValue(result.getZipCodeDisplay());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
		updateList();
	}

	@UiField
	UListElement listGroups;
	
	@UiField
	Anchor findEmployees;
	
	
	@UiField
	ListBox location;
	
	@UiField
	GroupSelect groupSelect;

	@UiField
	Button submit;
	
	
//	@UiHandler("submit")
//	void onClick(ChangeEvent e) {
//		updateList();
//	}
//	@UiHandler("radius")
//	void onClick(ChangeEvent e) {
//	
//	}
//	@UiHandler("zipCode")
//	void onClick(ValueChangeEvent<String> e) {
//	
//	}
	public final static EmployerServiceAsync employerService = GWT
			.create(EmployerService.class);
	
	public void updateList(){
		
		employerService.getBasicQueryWithFilters(location.getValue(location.getSelectedIndex()),new AsyncCallback<List<UserProfile>>() {
			
			@Override
			public void onSuccess(List<UserProfile> result) {
				for(UserProfile up : result){
					LIElement liElement = Document.get()
							.createLIElement();
					liElement.setInnerHTML(up.name);
					liElement
							.addClassName("list-group-item list-width");
					listGroups.appendChild(liElement);	
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert("fail");
			}
		});
	}
//
//	public EmployerTopArea(String firstName) {
//		initWidget(uiBinder.createAndBindUi(this));
//		button.setText(firstName);
//	}
//
//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}
//
//	public void setText(String text) {
//		button.setText(text);
//	}
//
//	public String getText() {
//		return button.getText();
//	}

}
