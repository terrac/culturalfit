package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.NextQuestion;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ViewGroups;
import com.caines.cultural.client.SimpleFront.SetGroup;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TopArea extends Composite {

	private static TopAreaUiBinder uiBinder = GWT.create(TopAreaUiBinder.class);

	interface TopAreaUiBinder extends UiBinder<Widget, TopArea> {
	}

	static TopArea singleton;

	public TopArea() {
		singleton = this;

	}

	public boolean setup() {
		if(Window.Location.getPath().contains("employer")){
			SimpleFront.singleton.moveToEmployer();
			return false;
		} else {
			initWidget(uiBinder.createAndBindUi(this));
			nextQuestion();
			return true;
		}
	}

	@UiField
	Label groupName;

	@UiField
	Anchor profile;
	@UiField
	Anchor viewGroups;
	@UiField
	Anchor nextQuestion;
	@UiField
	public static VerticalPanel content;

	public static void setGroupName(String name) {
		String text = "CulturalFit/" + name.replace(' ', '-');
		singleton.groupName.setText(text);
	}

	@UiHandler("profile")
	void onClick(ClickEvent e) {
		setupProfile();

	}

	public void setupProfile() {
		removeActive();
		profile.getElement().getParentElement().addClassName("active");
		content.clear();
		content.add(new ProfileGroups());
	}

	@UiHandler("viewGroups")
	void onClickView(ClickEvent e) {
		removeActive();
		viewGroups.getElement().getParentElement().addClassName("active");
		content.clear();
		content.add(new GroupSelect(content));
		new ViewGroups().groupArea(content);
	}

	@UiHandler("nextQuestion")
	void onClickNext(ClickEvent e) {
		nextQuestion();
	}

	public void nextQuestion() {
		removeActive();
		nextQuestion.getElement().getParentElement().addClassName("active");
		new NextQuestion().showNextQuestion(content);
	}

	public void removeActive() {
		profile.getElement().getParentElement().removeClassName("active");
		viewGroups.getElement().getParentElement().removeClassName("active");
		nextQuestion.getElement().getParentElement().removeClassName("active");
	}

	public static void showOrSelectGroups(VerticalPanel vp) {
		final Button addGroup = new Button("Select Group");

		final SuggestBox sb = new SuggestBox(new ItemSuggestOracle() {

			@Override
			public void requestSuggestions(Request request, Callback callback) {
				SuggestService.Util.getInstance().getGroup(request,
						new ItemSuggestCallback(request, callback));
			}
		});
		final SetupTopGroups callback = new SetupTopGroups() {

			@Override
			public void addPage(HorizontalPanel hp) {
				hp.add(sb);
				hp.add(addGroup);
			}
		};
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {

			@Override
			public void onSuccess(Group result) {
				if (result != null) {
					callback.hp.add(new Label(result.name));
				} else {
					SimpleFront.basicService.getTopGroups(callback);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		vp.add(callback.hp);
		addGroup.addClickHandler(new SetGroup(sb) {
			public void onSuccess() {
			}
		});
	}

	abstract static class SetupTopGroups implements AsyncCallback<List<Group>> {
		HorizontalPanel hp = new HorizontalPanel();

		@Override
		public void onSuccess(List<Group> result) {
			for (Group g : result) {
				final Button b = new Button();
				b.setText(g.name);
				b.addClickHandler(new SetGroup(b) {
					@Override
					public void onSuccess() {
						new NextQuestion().showNextQuestion(TopArea.content);
					}
				});
				hp.add(b);
			}
			addPage(hp);
		}

		public abstract void addPage(HorizontalPanel hp);

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

}
