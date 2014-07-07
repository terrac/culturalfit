package com.caines.cultural.client;

import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddQuestion {
	public void addQuestion(final VerticalPanel vp) {
		vp.clear();
		

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Question:"));
		hp.add(tq);

		vp.add(hp);
		hp = new HorizontalPanel();
		hp.add(new Label("Correct Answer:"));
		hp.add(ta1);

		vp.add(hp);
		hp = new HorizontalPanel();
		hp.add(new Label("Incorrect answer:"));
		hp.add(ta2);
		vp.add(hp);

		hp = new HorizontalPanel();
		hp.add(new Label("Tags:"));
		final SuggestBox ts = new SuggestBox(new TagSelect());

		hp.add(ts);
		//vp.add(hp);

		SubmitButton sb1 = new SubmitButton("Add Question");
		vp.add(sb1);
		sb1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SimpleFront.basicService.addQuestion(tq.getText(),
						ta1.getText(), ta2.getText(), ts.getValue(),
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


}