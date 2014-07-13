package com.caines.cultural.client.ui;

import com.caines.cultural.client.GroupEdit;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupSelect extends Composite {

	private static GroupSelectUiBinder uiBinder = GWT
			.create(GroupSelectUiBinder.class);

	AsyncCallback<Group> setSuggestBox = new AsyncCallback<Group>() {

		@Override
		public void onSuccess(Group result) {
			if (result == null) {
				return;
			}
			TopArea.setGroup(result);
			suggestBox.setText(result.name);
		}

		@Override
		public void onFailure(Throwable caught) {

		}
	};

	private final class SetGroupOnClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			final Button b = (Button) event.getSource();
			SimpleFront.basicService
					.setCurrentGroup(b.getText(), setSuggestBox);
		}
	}

	interface GroupSelectUiBinder extends UiBinder<Widget, GroupSelect> {
	}

	// @UiField
	// Button manageGroup;
	@UiField
	HorizontalPanel gContainer;

	@UiField
	DivElement wellDiv;

	@UiField
	public Button selectGroup;

	@UiField
	public Button editGroup;

	boolean suggestHasValidGroup = true;
	@UiField(provided = true)
	public SuggestBox suggestBox;

	public GroupSelect() {
		this(true);
	}

	@UiConstructor
	public GroupSelect(boolean hasButtons) {
		final ItemSuggestOracle oracle = new ItemSuggestOracle() {
			@Override
			public void requestSuggestions(Request request, Callback callback) {
				// if(request.getQuery().length() < 3){
				// return;
				// }
				SuggestService.Util.getInstance().getGroup(request,
						new ItemSuggestCallback(request, callback));
			}
		};
		SimpleFront.basicService.getCurrentGroup(new AsyncCallback<Group>() {

			@Override
			public void onSuccess(Group result) {
				if (result != null) {
					suggestBox.setValue(result.name);
				}
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});
		suggestBox = new SuggestBox(oracle);
		suggestBox.setText(TopArea.groupNameS);
		suggestBox.getElement().addClassName("input-xlarge search-query");

		initWidget(uiBinder.createAndBindUi(this));

		editGroup.getElement().setClassName("btn btn-primary");
		editGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SimpleFront.basicService.editGroup(suggestBox.getValue(),
						new AsyncCallback<Tuple<Group, String>>() {

							@Override
							public void onSuccess(Tuple<Group, String> result) {
								TopArea.setGroup(result.a);
								// TopArea.setGroup(a);
								if ("Created".equals(result)) {
									Window.alert("Created");
								} else {
									boolean owner = "owner".equals(result.b);
									new GroupEdit().editGroup(owner);
								}
							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});
			}
		});
		if (!hasButtons) {

			wellDiv.setClassName("");
			gContainer.setVisible(false);
			selectGroup.setVisible(false);
			editGroup.setVisible(false);

		} else {
			String[] buttons = new String[] { "programming", "hobbies" };
			for (String a : buttons) {
				Button b = new Button(a);
				TopArea.content.add(b);
				b.getElement().setClassName("btn btn-default");
				gContainer.add(b);
				b.addClickHandler(new SetGroupOnClick());
			}
		}
		// addGroup.setVisible(false);

		selectGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SimpleFront.basicService.setCurrentGroup(suggestBox.getText(),
						setSuggestBox);
			}
		});
	}

}
