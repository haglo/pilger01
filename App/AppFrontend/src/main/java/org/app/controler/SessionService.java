package org.app.controler;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.app.model.audit.LoggedInUser;
import org.app.model.entity.Account;
import org.app.model.entity.ElytronUser;

/*
 * Managed Bean
 * In MVC the Controler-Part
 */
@SessionScoped
public class SessionService implements Serializable {

	@Inject
	SettingsService settingsService;
	
	@Inject
	LoggedInUser loggedInUser;
	
	private static final long serialVersionUID = 1L;
	
	private ElytronUser currentUser = new ElytronUser();
	private Locale currentLocale;
	private String currentTheme;

	public ElytronUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ElytronUser currentUser) {
		this.currentUser = currentUser;
		loggedInUser.setElytronUser(currentUser);
		loggedInUser.setCurrentUserID(currentUser.getId());
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	public String getCurrentTheme() {
		return currentTheme;
	}

	public void setCurrentTheme(String currentTheme) {
		this.currentTheme = currentTheme;
	}

}
