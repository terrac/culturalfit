package com.caines.cultural.client;

import com.caines.cultural.client.ui.TopArea;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddQuestion {

	public void addQuestion() {
		TopArea.content.clear();

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Question:"));
		hp.add(tq);

		TopArea.content.add(hp);
		TopArea.content.add(ta1.getParent());

		TopArea.content.add(ta2.getParent());

//		hp = new HorizontalPanel();
//		hp.add(new Label("Tags:"));
//		final SuggestBox ts = new SuggestBox(new TagSelect());

//		hp.add(ts);
		// vp.add(hp);

		TopArea.content.add(onlyRightOrWrong);
		TopArea.content.add(yesOrNo);
		
		onlyRightOrWrong.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ta1.getParent().setVisible(!onlyRightOrWrong.getValue());
				ta2.getParent().setVisible(!onlyRightOrWrong.getValue());
				yesOrNo.setVisible(onlyRightOrWrong.getValue());
			}
		});

		SubmitButton sb1 = new SubmitButton("Add Question");
		TopArea.content.add(sb1);

		sb1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (onlyRightOrWrong.getValue()) {
					if (yes.getValue()) {
						ta1.setText("Correct");
						ta2.setText("Incorrect");
					} else {
						ta2.setText("Correct");
						ta1.setText("Incorrect");
					}
				}
				SimpleFront.basicService.addQuestion(tq.getText(),
						ta1.getText(), ta2.getText(),"", //ts.getValue(),
						new AsyncCallback<String>() {

							@Override
							public void onSuccess(String result) {
								Window.alert("Sent");
								tq.setText("");
								ta1.setText("");
								ta2.setText("");
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
	}

	final TextArea tq = new TextArea();
	final TextArea ta1 = new TextArea();
	final TextArea ta2 = new TextArea();
	CheckBox onlyRightOrWrong = new CheckBox("Correct or Incorrect question");
	RadioButton yes = new RadioButton("yesno", "Correct");
	RadioButton no = new RadioButton("yesno", "Incorrect");
	HorizontalPanel yesOrNo = new HorizontalPanel();
	{
		tq.setSize("30em","10em");
		ta1.setSize("30em","10em");
		ta2.setSize("30em","10em");
		
		yesOrNo.add(yes);
		yesOrNo.add(no);
		yesOrNo.setVisible(false);
		yesOrNo.setHeight("5em");
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Correct Answer:"));
		hp.add(ta1);
		
		hp = new HorizontalPanel();
		hp.add(new Label("Incorrect answer:"));
		hp.add(ta2);


	}

	

}