package com.caines.cultural.client.ui;

import com.caines.cultural.client.AddQuestion;
import com.caines.cultural.client.GroupEdit;
import com.caines.cultural.client.NextQuestion;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.TagSelect;
import com.caines.cultural.client.ViewGroups;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.shared.datamodel.Group;
import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TopArea extends Composite{

	private static TopAreaUiBinder uiBinder = GWT.create(TopAreaUiBinder.class);

	interface TopAreaUiBinder extends UiBinder<Widget, TopArea> {
	}

	public TopArea() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button profile;
	@UiField
	Button viewGroups;
	@UiField
	Button nextQuestion;
	@UiField
	public
	static VerticalPanel content;

	
	

	@UiHandler("profile")
	void onClick(ClickEvent e) {
	}

	@UiHandler("viewGroups")
	void onClickView(ClickEvent e) {
		new ViewGroups().groupArea(content);
	}

	@UiHandler("nextQuestion")
	void onClickNext(ClickEvent e) {
		new NextQuestion().showNextQuestion(content);
	}

	
	
}
