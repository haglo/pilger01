package org.app.view;

import java.util.Locale;
import java.util.Optional;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import org.app.controler.ElytronUserService;
import org.app.controler.SessionService;
import org.app.helper.I18n;
import org.app.model.audit.LoggedInUser;
import org.app.model.dao.ElytronUserDAO;
import org.app.model.entity.Account;
import org.app.model.entity.ElytronUser;
import org.app.view.login.LoginView;
import org.app.view.person.PersonView;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("default")
@CDIUI("")
public class MainUI extends UI {
	@Inject
	CDIViewProvider viewProvider;

	@Inject
	PersonView personView;

	@Inject
	LoginView loginView;

	@Inject
	JaasAccessControl accessControl;

	@Inject
	ElytronUserService elytronUserService;
	
	@Inject
	SessionService sessionService;

	private Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		System.setProperty("java.security.auth.login.config", "jaas.config");
		if (accessControl.isUserSignedIn()) {
			sessionService.setCurrentUser(elytronUserService.getElytronUserDAO().findByName(accessControl.getPrincipalName()));
			setupMainLayout();
		} else {
			setContent(loginView);
		}
	}

	public void loginSuccessful() {
		setupMainLayout();
	}

	private void setupMainLayout() {
		final VerticalLayout mainLayout = new VerticalLayout();
		final HorizontalLayout menuView = new HorizontalLayout();
		final HorizontalLayout contentView = new HorizontalLayout();
		contentView.setSizeFull();
		contentView.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		TopMainMenu topNavBar = new TopMainMenu(accessControl);
		menuView.addComponent(topNavBar);

		mainLayout.addComponent(menuView);
		mainLayout.addComponent(contentView);

		mainLayout.setComponentAlignment(menuView, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(contentView, Alignment.TOP_CENTER);

		mainLayout.setExpandRatio(menuView, 0.10f);
		mainLayout.setExpandRatio(contentView, 0.90f);

		mainLayout.setSizeFull();

		setContent(mainLayout);

		navigator = new Navigator(this, contentView);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(loginView);

		String initialState = Optional.ofNullable(navigator.getState()).filter(state -> !state.trim().isEmpty())
				.orElse(I18n.PERSON_VIEW);
		navigator.navigateTo(initialState);
	}

	@Override
	public void setLocale(Locale locale) {
		super.setLocale(locale);
	}


	@Override
	public Locale getLocale() {
		return super.getLocale();
	}

	public void setTheme(String theme) {
		super.setTheme(theme);
	}

	public AccessControl getAccessControl() {
		return accessControl;
	}

}