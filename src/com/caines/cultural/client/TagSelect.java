package com.caines.cultural.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caines.cultural.shared.datamodel.Tag;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TagSelect extends Composite {
	
	public TagSelect() {
		initWidget(vp);
		vp.add(tags);
		vp.add(tagList);
		setup(new UpdateTag());
		
	}
	private final GreetingServiceAsync tagService = GWT
			.create(GreetingService.class);

	TextBox tags = new TextBox();
	VerticalPanel vp = new VerticalPanel();
	FlowPanel tagList = new FlowPanel();
	Map<Label,String> tagMap = new HashMap();
	public TagSelect(final TagAction ta) {
		setup(ta);
	}
	public void setup(final TagAction ta) {
		tags.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				TextBox t = (TextBox) event.getSource();
				
				tagService.getTopTags(t.getText(),new AsyncCallback<List<Tag>>() {
					
					@Override
					public void onSuccess(List<Tag> result) {
						for(Tag t : result){
							Label l = new Label(t.name+" "+t.amount);
							tagList.add(l);
							l.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									String name = tagMap.get(event.getSource());
									ta.execute(name);
								}
							});
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}

	public static interface TagAction{
		public void execute(String name);
	}
	public class UpdateTag implements TagAction{

		@Override
		public void execute(String name) {
			tags.setText(tags.getText()+name);
		}
		
	}
}
