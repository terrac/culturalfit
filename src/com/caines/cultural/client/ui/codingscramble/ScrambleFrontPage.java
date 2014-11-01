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
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
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


	interface ScrambleFrontPageUiBinder extends
			UiBinder<Widget, ScrambleFrontPage> {
	}

	public ScrambleFrontPage() {
		initWidget(uiBinder.createAndBindUi(this));
		update();

	}
	boolean between;
	@UiField
	Button b1;
	@UiField
	Button b2;
	@UiField
	Button b3;

	@UiField
	Button tag1;
	@UiField
	Button tag2;

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
		basicService.getNextLines(Window.Location.getParameter("tag"),
				new AsyncCallback<ScramblerQuestion>() {

					@Override
					public void onSuccess(final ScramblerQuestion result) {
						if (result == null) {
							return;
						}
						currentTag.setText(result.filename);
						// currentTag.setHref("/viewer/"+result.tag+"?url="+result.url);
						currentTag.setHref("/viewer/" + result.url + "/");
						link.addClickHandler(new ClickHandler() {
							

							@Override
							public void onClick(ClickEvent event) {
								between = !between;
								setupTopCode(result);
							}
						});
						setupTopCode(result);
						setupCode(result.line2, result.rawFile, "code2",
								result.linkedText, -1);
						link.setHref("#");
						

						// link2.setHref("#");
						// link2.setText("See File");

						tag1.setText(result.tag1);
						tag1.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								Window.Location.assign(Window.Location
										.createUrlBuilder()
										.setParameter("tag", result.tag1)
										.buildString());
							}
						});

						tag2.setText(result.tag2);
						tag2.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								Window.Location.assign(Window.Location
										.createUrlBuilder()
										.setParameter("tag", result.tag2)
										.buildString());
							}
						});
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
		basicService.linkCode("highlyLinked", callback);
	}

	@UiHandler("b2")
	void onClickBottom(ClickEvent e) {
		basicService.linkCode("linked", callback);
	}

	@UiHandler("b3")
	void onClickNoLink(ClickEvent e) {
		basicService.linkCode("notLinked", callback);
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

	public void setupCode(int line, List<String> file, String name,
			String toHighlight, int second) {
		Element preElement = DOM.getElementById(name);
		StringBuilder c = new StringBuilder();
		int count = 0;
		for (String a : file) {
			boolean b = second != -1 && second > count&&line < count;
			if (line == -1 || line + 1 == count || line - 1 == count
					|| line == count || b) {
				if (line == count) {
					a = a.replace(toHighlight, "<u>" + toHighlight + "</u>");
				}
				c.append(a);
				c.append("\n");
			}
			count++;
		}
		preElement.setInnerText("");

		preElement.setInnerHTML(c.toString());

		preElement.removeClassName("prettyprinted");
		runPretty();
		
	}

	public void setupTopCode(final ScramblerQuestion result) {
		if (between) {
			setupCode(result.line1, result.rawFile,
					"code", result.linkedText,
					result.line2);
			link.setText("See plus two lines");
		} else {
			setupCode(result.line1, result.rawFile,
					"code", result.linkedText,
					-1);
			link.setText("See Between");
		}
	}

	public static native void runPretty() /*-{
		$wnd.prettyPrint();
	}-*/;

	AsyncCallback<Void> callback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			update();
		}
	};

}
