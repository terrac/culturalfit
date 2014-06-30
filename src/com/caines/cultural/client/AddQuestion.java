package com.caines.cultural.client;

import com.caines.cultural.shared.datamodel.Group;
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
		

		final TextArea ta = new TextArea();
		final TextArea ta1 = new TextArea();
		final TextArea ta2 = new TextArea();

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Question:"));
		hp.add(ta);

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
				SimpleFront.basicService.addQuestion(ta.getText(),
						ta1.getText(), ta2.getText(), ts.getValue(),
						new AsyncCallback<String>() {

							@Override
							public void onSuccess(String result) {
								Window.alert("Submitted");
								tq.setText("");
								ta.setText("");
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

	Label tq = new Label();
	Label ta1 = new Label();
	Label ta2 = new Label();

	public void editQuestion(final VerticalPanel vp,final Long questionKey) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label("Groupname:"));
		hp.add(new Label(""));
		vp.add(hp);

		SimpleFront.basicService.getQuestionToEdit(questionKey,
				new AsyncCallback<Question>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Question result) {
						tq.setText(result.question);
						ta1.setText(result.answer1);
						ta2.setText(result.answer2);
					}

				});
		hp = new HorizontalPanel();
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
		vp.add(hp);

		SubmitButton sb1 = new SubmitButton("Add Question");
		vp.add(sb1);
		sb1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SimpleFront.basicService.updateQuestion(questionKey,ts.getValue(),new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
}
