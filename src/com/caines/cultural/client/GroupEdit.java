package com.caines.cultural.client;

import com.caines.cultural.client.ui.QuestionList;
import com.caines.cultural.client.ui.TopArea;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupEdit {
	public void editGroup(boolean creator, Group group) {
		TopArea.content.clear();
		if (group.addMoreQuestions) {
			// add question link
			addQuestionButton();
		} else {
			TopArea.content.add(new Label("Group has 25 active questions so no more can be currently added"));
		}
		if (creator) {
			TopArea.content.add(new QuestionList());
		} else {
			if (group.addMoreQuestions) {
				new AddQuestion().addQuestion();
			}
		}

	}

	public void addQuestionButton() {
		Button b = new Button("Add Question");
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new AddQuestion().addQuestion();
			}
		});
		// edit question list
		TopArea.content.add(b);
	}

}
