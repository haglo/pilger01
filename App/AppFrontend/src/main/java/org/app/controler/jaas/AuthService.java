package org.app.controler.jaas;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.app.controler.AccountService;
import org.app.model.entity.Account;

@RequestScoped
public class AuthService {


	@Inject
	private AccountService accountService;

	private Account account;
	private String MessageForAuthentication = "Login Succesfull";

	private boolean validateUser(String username) {
		account = new Account();

		try {
			account = accountService.findByUserName(username);
		} catch (Exception e) {
			MessageForAuthentication = "Username " + username + " not exist" ;
			return false;
		}
		return true;
	}

	private boolean validatePassword(String password) {

		if (password.equals(account.getPassword())) {
			return true;
		} else {
			MessageForAuthentication = "Password not correct";
			return false;
		}

	}

	public boolean validateAccount(String username, String password) {
		boolean validUser = false;
		boolean validPassword = false;
		boolean authentic = false;

		validUser = validateUser(username);
		
		if (validUser) {
			validPassword = validatePassword(password);
		}

		if (validUser == true && validPassword == true) {
			authentic = true;
		}

		return authentic;
	}


	public String getMessageForAuthentication() {
		return MessageForAuthentication;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
