package org.app.view;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;

import org.app.helper.I18n;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
public class TopMainMenu extends CustomComponent {

	private Button logOutButton;
	private JaasAccessControl accessControl;

	public TopMainMenu() {

	}
	public TopMainMenu(JaasAccessControl ac) {
		accessControl = new JaasAccessControl();
		accessControl = ac;
		setupLayout();
	}

	Button personViewButton = new Button("Addresses", e -> UI.getCurrent().getNavigator().navigateTo(I18n.PERSON_VIEW));

	Button masterDetailViewButton = new Button("Stammdaten", e -> UI.getCurrent().getNavigator().navigateTo(I18n.MASTER_DETAIL_VIEW));

	Button accountViewButton = new Button("Accounts",
			e -> UI.getCurrent().getNavigator().navigateTo(I18n.ACCOUNT_VIEW));

	Button userViewButton = new Button("Users",
			e -> UI.getCurrent().getNavigator().navigateTo(I18n.ELYTRON_USER_VIEW));

	Button helpViewButton = new Button("Help", e -> UI.getCurrent().getNavigator().navigateTo(I18n.HELP_VIEW));

	private Button logoutButton() {
		logOutButton = new Button("Logout", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					accessControl.logout();
					getUI().getSession().close();
					getUI().getPage().reload();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		logOutButton.setIcon(VaadinIcons.SIGN_OUT);
		logOutButton.addStyleName("icon-align-top");
		return logOutButton;
	}

	private void setupLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		personViewButton.setIcon(VaadinIcons.FAMILY);
		personViewButton.addStyleName("icon-align-top");
		masterDetailViewButton.setIcon(VaadinIcons.NEWSPAPER);
		masterDetailViewButton.addStyleName("icon-align-top");
		accountViewButton.setIcon(VaadinIcons.USER);
		accountViewButton.addStyleName("icon-align-top");
		userViewButton.setIcon(VaadinIcons.USER);
		userViewButton.addStyleName("icon-align-top");
		helpViewButton.setIcon(VaadinIcons.QUESTION);
		helpViewButton.addStyleName("icon-align-top");

		layout.addComponent(personViewButton);
		layout.addComponent(masterDetailViewButton);
		layout.addComponent(accountViewButton);
		layout.addComponent(userViewButton);
		layout.addComponent(helpViewButton);
		layout.addComponent(logoutButton());
		setCompositionRoot(layout);		
	}
}
