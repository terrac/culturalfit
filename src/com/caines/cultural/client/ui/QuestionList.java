package com.caines.cultural.client.ui;

import java.util.List;

import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.client.ui.employer.EmployerTopArea;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class QuestionList extends Composite {

	private static QuestionListUiBinder uiBinder = GWT
			.create(QuestionListUiBinder.class);

	interface QuestionListUiBinder extends UiBinder<Widget, QuestionList> {
	}

	public QuestionList() {
		initWidget(uiBinder.createAndBindUi(this));
		load();
	}
	private final class RemoveTemporaryQuestion implements AsyncCallback<Void> {
		private final HorizontalPanel hp;

		private RemoveTemporaryQuestion(HorizontalPanel hp) {
			this.hp = hp;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			temporaryQuestions.remove(hp);
			table.redraw();
		}
	}

	@UiField
	DivElement grid;

	@UiField
	public VerticalPanel temporaryQuestions;

	CellTable<Question> table = new CellTable<Question>();
	
	public void load() {
		
		SimpleFront.basicService.getTemporaryQuestions(new AsyncCallback<List<Question>>() {
			
			@Override
			public void onSuccess(List<Question> result) {
				
				
				for(final Question q : result){
					String html = "<img src='/assets/person.png'>";
					html += q.question+"<br>"+q.answer1+"<br>"+q.answer2;
					
					final HorizontalPanel hp = new HorizontalPanel();		
					EmployerTopArea.addListItem(html,hp);
					Button ok = new Button("Add");
					Button reject = new Button("Reject");
					ok.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							SimpleFront.basicService.addPermanentQuestion(q.id, true, new RemoveTemporaryQuestion(hp));
						}
					});
					
					hp.add(ok);
					hp.add(reject);
					
					temporaryQuestions.add(hp);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

		
		// Create a CellTable.
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a text column to show the name.
		TextColumn<Question> nameColumn = new TextColumn<Question>() {
			@Override
			public String getValue(Question object) {
				return object.question;
			}
		};
		
		table.addColumn(nameColumn, "Question");

		TextColumn<Question> rightColumn = new TextColumn<Question>() {
			@Override
			public String getValue(Question object) {
				return object.answer1;
			}
		};
		
		table.addColumn(rightColumn, "Right Answer");

		TextColumn<Question> wrongColumn = new TextColumn<Question>() {
			@Override
			public String getValue(Question object) {
				return object.answer2;
			}
		};
		
		table.addColumn(wrongColumn, "Wrong Answer");

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a text column to show the name.
		TextColumn<Question> disabled = new TextColumn<Question>() {
			@Override
			public String getValue(Question object) {
				return ""+object.disabled;
			}
		};
		
		table.addColumn(disabled, "Disabled");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<Question> selectionModel = new SingleSelectionModel<Question>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Question selected = selectionModel.getSelectedObject();
						selected.disabled = !selected.disabled;
						selectionModel.clear();
						table.redraw();
						SimpleFront.basicService.disableQuestion(selected.id, new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				});

		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.

		SimpleFront.basicService
				.getQuestionList(new AsyncCallback<List<Question>>() {

					@Override
					public void onSuccess(List<Question> result) {
						table.setRowCount(result.size(), true);
						table.setRowData(0, result);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

		// Add it to the root panel.
		// grid.appendChild(table.getElement());
		TopArea.content.add(table);
	}

}
