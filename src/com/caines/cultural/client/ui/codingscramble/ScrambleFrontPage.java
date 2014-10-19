package com.caines.cultural.client.ui.codingscramble;

import java.util.List;

import com.caines.cultural.client.BasicScramblerService;
import com.caines.cultural.client.BasicScramblerServiceAsync;
import com.caines.cultural.client.SimpleFront;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ScrambleFrontPage extends Composite {

	public final static BasicScramblerServiceAsync basicService = GWT
			.create(BasicScramblerService.class);

	private static ScrambleFrontPageUiBinder uiBinder = GWT
			.create(ScrambleFrontPageUiBinder.class);

	private final class AlternateLink implements ClickHandler {
		private final String name;
		private final String code;
		private final String rawFile;
		private final String toHighlight;
		private boolean file = false;

		private AlternateLink(String name, String code, String rawFile,String toHighlight) {
			setupCode(code, name,toHighlight);
			this.name = name;
			this.code = code;
			this.rawFile = rawFile;
			this.toHighlight = toHighlight;
		}

		@Override
		public void onClick(ClickEvent event) {
			file = !file;
			if (file) {
				setupCode(rawFile, name,toHighlight);
				link.setText("See Question");
			} else {
				setupCode(code, name,toHighlight);
				link.setText("See File");
			}
		}
	}

	interface ScrambleFrontPageUiBinder extends
			UiBinder<Widget, ScrambleFrontPage> {
	}

	public ScrambleFrontPage() {
		initWidget(uiBinder.createAndBindUi(this));
		update();

	}

	@UiField
	Button b1;
	@UiField
	Button b2;
	@UiField
	Button b3;

	@UiField
	Anchor link;
	@UiField
	Anchor link2;

	@UiField
	Anchor currentTag;

	@UiField
	Button profile;
	@UiField
	Button logIn;
	@UiField
	Button addUrl;

	public void update() {

		// 5 or 10 lines above and below the scrambled area
		//
		basicService.getNextLines(new AsyncCallback<ScramblerQuestion>() {

			@Override
			public void onSuccess(final ScramblerQuestion result) {
				if (result == null) {
					return;
				}
				currentTag.setText(result.tag);
				//currentTag.setHref("/viewer/"+result.tag+"?url="+result.url);
				currentTag.setHref("/viewer/"+result.url+"/");
				link.addClickHandler(new AlternateLink("code", result.code1,
						result.getRawFile(),result.linkedText));
				link.setHref("#");
				link.setText("See File");

				link2.addClickHandler(new AlternateLink("code2", result.code2,result.getRawFile2(),result.linkedText));
				link2.setHref("#");
				link2.setText("See File");

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		SimpleFront.scramblerService.getUserInfo(new AsyncCallback<UserInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(UserInfo result) {
				// TODO Auto-generated method stub
				if (result == null) {
					logIn.setVisible(true);
				}
				if (!result.isAdmin) {
					return;
				}
				addUrl.setVisible(true);
			}
		});

	}

	@UiHandler("b1")
	void onClick(ClickEvent e) {
		basicService.linkCode(true, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				update();
			}
		});
	}

	@UiHandler("b2")
	void onClickBottom(ClickEvent e) {
		basicService.linkCode(false, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				update();
			}
		});
	}

	@UiHandler("b3")
	void onClickNoLink(ClickEvent e) {

		update();
	}

	@UiHandler("logIn")
	void onClickLogIn(ClickEvent e) {
		Window.Location.assign("/loginRequired");
	}

	@UiHandler("profile")
	void onClickProfile(ClickEvent e) {
		SimpleFront.setupTopOfSidePage().add(new ScrambleProfile());
	}

	@UiHandler("addUrl")
	void onClickAddUrl(ClickEvent e) {
		SimpleFront.addUrl();
	}

	public void setupCode(String code, String name, String toHighlight) {
		Element preElement = DOM.getElementById(name);
		preElement.setInnerText("");

		preElement.setInnerHTML(code.replace(toHighlight, "<u>"+toHighlight+"</u>"));

		preElement.removeClassName("prettyprinted");
		runPretty();
	}

	public static native void runPretty() /*-{
		$wnd.prettyPrint();
	}-*/;
}
