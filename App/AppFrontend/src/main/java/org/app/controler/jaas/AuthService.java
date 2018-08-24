package org.app.controler.jaas;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.app.controler.AccountService;
import org.app.controler.ElytronUserService;
import org.app.controler.SessionService;
import org.app.model.dao.ElytronRoleDAO;
import org.app.model.entity.Account;
import org.app.model.entity.ElytronUser;

@RequestScoped
public class AuthService {

	@EJB
	private ElytronRoleDAO elytronRoleDAO;

	@Inject
	private AccountService accountService;

	@Inject
	private ElytronUserService elytronUserService;

	@Inject
	private SessionService sessionService;

	private Account account;
	private ElytronUser elytronUser;
	private String MessageForAuthentication = "Login Succesfull";

	private boolean validateLoginUser(String username) {
		account = new Account();

		try {
			account = accountService.findByUserName(username);
		} catch (Exception e) {
			MessageForAuthentication = "Username " + username + " not exist";
			return false;
		}
		return true;
	}

	private boolean validateLoginPassword(String password) {

		if (password.equals(account.getPassword())) {
			return true;
		} else {
			MessageForAuthentication = "Password not correct";
			return false;
		}

	}

	private boolean validateElytronUser(String username) {

		boolean result = false;
		boolean elytronUserExists = false;
		elytronUser = new ElytronUser();

		List<ElytronUser> elytronUserList = elytronUserService.getElytronUserDAO().findAll();
		for (ElytronUser entry : elytronUserList) {
			if (entry.getUsername().equals(username)) {
				elytronUserExists = true;
			}
		}

		if (elytronUserExists) {
			sessionService.setCurrentUser(elytronUser);
			result = true;
		}

		if (!elytronUserExists) {
			try {
				ElytronUser nutElytronUser = new ElytronUser();
				nutElytronUser = elytronUserService.getElytronUserDAO().findByID(1);

				elytronUser.setUsername(username);
				elytronUser.setElytronRole(nutElytronUser.getElytronRole());
				elytronUser.setDefaultLanguage(nutElytronUser.getDefaultLanguage());
				elytronUser.setDefaultTheme(nutElytronUser.getDefaultTheme());

				elytronUserService.getElytronUserDAO().create(elytronUser);
				sessionService.setCurrentUser(elytronUser);
				result = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public boolean validateAuthenticationValues(String username, String password) {
		boolean validUser = false;
		boolean validPassword = false;
		boolean validElytronUser = false;
		boolean authentic = false;

		validUser = validateLoginUser(username);

		if (validUser == true) {
			validPassword = validateLoginPassword(password);
		}

		if (validUser == true && validPassword == true) {
			validElytronUser = validateElytronUser(username);
		}

		if (validUser == true && validPassword == true && validElytronUser == true) {
			authentic = true;
		}
		return authentic;
	}

	public String getMessageForAuthentication() {
		return MessageForAuthentication;
	}

//	public Account getAccount() {
//		return account;
//	}
//
//	public void setAccount(Account account) {
//		this.account = account;
//	}

}
