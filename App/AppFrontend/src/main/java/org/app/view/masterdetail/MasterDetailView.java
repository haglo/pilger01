package org.app.view.masterdetail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.app.controler.TitleService;
import org.app.helper.I18n;
import org.app.view.masterdetail.views.TitleView;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView(I18n.MASTER_DETAIL_VIEW)
public class MasterDetailView extends VerticalLayout implements View {

	@Inject
	TitleService titleService;

	// @Inject
	// LandService landService;

	private HorizontalLayout mainView;
	private VerticalLayout masterDetailLeftNavBar;
	private CssLayout masterDetailContent;

	private Button titleButton;
	// private Button landButton;

	public MasterDetailView() {
		setSizeFull();
		setMargin(new MarginInfo(true, true, true, true));
		setWidth("1000px");
	}

	@PostConstruct
	void init() {
		mainView = new HorizontalLayout();
		masterDetailLeftNavBar = new VerticalLayout();
		masterDetailLeftNavBar.setMargin(false);
		masterDetailContent = new CssLayout();
		masterDetailContent.setSizeFull();

		Label head = new Label("Master Detail");

		masterDetailLeftNavBar.addComponent(head);

		masterDetailLeftNavBar.addComponent(showTitleView());
		masterDetailContent.addComponent(new TitleView(titleService));

		mainView.addComponent(masterDetailLeftNavBar);
		mainView.addComponent(masterDetailContent);
		mainView.setSizeFull();

		mainView.setExpandRatio(masterDetailLeftNavBar, 0.2f);
		mainView.setExpandRatio(masterDetailContent, 0.8f);

		addStyleName("navigation-bar");
		addComponent(mainView);

	}

	private Button showTitleView() {
		titleButton = new Button("Title", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				masterDetailContent.removeAllComponents();
				masterDetailContent.addComponent(new TitleView(titleService));
			}
		});
		return titleButton;
	}

}
