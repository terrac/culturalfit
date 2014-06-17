package com.caines.cultural.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupEdit {
	public void editGroup(final VerticalPanel vp) {
		vp.clear();
		//add question link
		NextQuestion.showOrSelectGroups(vp);
		Button b = new Button("Add Question");
		b.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				new AddQuestion().addQuestion(vp);
			}
		});
		//edit question list
		vp.add(b);
	}


}
