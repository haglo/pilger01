package org.app.view.help;

import javax.annotation.PostConstruct;
import org.app.helper.I18n;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(I18n.HELP_VIEW)
@UIScoped
public class HelpView extends HorizontalLayout implements View {


	public HelpView() {
		setMargin(new MarginInfo(false, true, true, true));
	}

	@PostConstruct
	void init() {
		setSizeFull();
		setWidth("1000px");

		VerticalLayout content = new VerticalLayout();
		final HorizontalLayout navigationBar = new HorizontalLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		content.addComponent(headingLabel());
		content.addComponent(someText());
		content.addComponent(navigationBar);
		addComponent(content);
		setDefaultComponentAlignment(Alignment.TOP_CENTER);
	}

	private Label headingLabel() {
		return new Label("Help without HTML");
	}

	private Label someText() {
		Label label = new Label("Help <b>with HTML</b>");
		label.setContentMode(ContentMode.HTML);
		return label;
	}



}
