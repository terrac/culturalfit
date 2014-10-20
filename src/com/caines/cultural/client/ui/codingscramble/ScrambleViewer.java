package com.caines.cultural.client.ui.codingscramble;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ScrambleViewer extends Composite  {

	private static ScrambleViewerUiBinder uiBinder = GWT
			.create(ScrambleViewerUiBinder.class);

	interface ScrambleViewerUiBinder extends UiBinder<Widget, ScrambleViewer> {
	}

	public ScrambleViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Anchor toSubmit;
	@UiField
	Anchor currentTag;

	public ScrambleViewer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
		init();
	}

	public ScrambleViewer init() {
		String[] u = Window.Location.getHref().split("/");
	
		ScrambleFrontPage.basicService.getContainer(u[4], new AsyncCallback<Tuple<CodeContainer,List<CodeLinkContainer>>>() {
			
			@Override
			public void onSuccess(Tuple<CodeContainer, List<CodeLinkContainer>> result) {
				// TODO Auto-generated method stub
				setupCode(result.a, "code", result.b);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		return this;
	}

//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}


	public void setupCode(CodeContainer code, String name, List<CodeLinkContainer> toHighlight) {
		Element preElement = DOM.getElementById(name);
		preElement.setInnerText("");
		for(CodeLinkContainer tH : toHighlight){
			
			for(Integer i : tH.linkedPointers){
				code.file.set(i, code.file.get(i).replace(tH.name, "<span class=link-color-"+count+">"+toHighlight+"</span>"));
			}
			count++;
			if(count > maxCount) count = 1;				
		}
		preElement.setInnerHTML(code.getRawFile());
		preElement.removeClassName("prettyprinted");
		ScrambleFrontPage.runPretty();
	}
	int count = 0;
	final int maxCount = 11;
}
