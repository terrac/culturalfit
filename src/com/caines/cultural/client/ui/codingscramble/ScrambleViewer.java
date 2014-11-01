package com.caines.cultural.client.ui.codingscramble;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
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

	Map<String,Integer> nameColorMap = new HashMap<String, Integer>(); 
	public void setupCode(CodeContainer code, String name, List<CodeLinkContainer> toHighlight) {
		Element preElement = DOM.getElementById(name);
		preElement.setInnerText("");
		for(CodeLinkContainer tH : toHighlight){
			Integer color =  nameColorMap.get(tH.name);
			if(color == null){
				count++;
				if(count > maxCount) count = 1;
				color = count;
				nameColorMap.put(tH.name, color);
				System.out.println(tH.name);
			}
			for(Integer i : tH.linkedPointers){				
				code.cf.get().file.set(i, code.cf.get().file.get(i).replace(tH.name, updateName(tH, color)));
			}
			for(Integer i : tH.highlyLinkedPointers){				
				code.cf.get().file.set(i, code.cf.get().file.get(i).replace(tH.name,updateName(tH, color)));
			}
			for(Integer i : tH.notLinkedPointers){				
				code.cf.get().file.set(i, code.cf.get().file.get(i).replace(tH.name,updateName(tH, color)));
			}
		}
		preElement.setInnerHTML(code.cf.get().getRawFile());
		preElement.removeClassName("prettyprinted");
		ScrambleFrontPage.runPretty();
		GQuery myElement = GQuery.$(".linkedName");
		final Boolean showingAll = true;
		myElement.click(new Function() {
			@Override
			public boolean f(Event e) {
				final String clickedName = GQuery.$(e).text();
				GQuery.$(".linkedName").each(new Function(){
					@Override
					public Object f(Element element, int i) {
						if(showingAll){
							if(!clickedName.equals(GQuery.$(element).text())){								
								element.setClassName(element.getClassName().replace("color", "unused1"));	
							} else {
								element.setClassName(element.getClassName().replace("unused1", "color"));
							}
						}
						
						//GQuery.$(element).unbind("click");
						return null;
					}
				});
				
				return super.f(e);
			}
			
		});
	}

	public String updateName(CodeLinkContainer tH, Integer color) {
		return "<span class='link-color-"+color+" linkedName' >"+tH.name+"</span>";
	}
	int count = 0;
	final int maxCount = 11;
	
	public static native void runPretty() /*-{
	$wnd.prettyPrint();
}-*/;
}
