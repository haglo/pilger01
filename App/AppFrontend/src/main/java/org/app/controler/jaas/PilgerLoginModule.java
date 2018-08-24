package org.app.controler.jaas;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.app.view.MainUI;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@RequestScoped
public class PilgerLoginModule implements LoginModule {
	
	@Inject
	private AuthService authService;
	
	private boolean succeeded = false;
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map sharedState;
	private Map options;
	private boolean debug = false;

	private String loginFormUserName;
	private String loginFormPassword;

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize(Subject arg0, CallbackHandler arg1, Map arg2, Map arg3) {
		this.subject = arg0;
		this.callbackHandler = arg1;
		this.sharedState = arg2;
		this.options = arg3;
		debug = "true".equalsIgnoreCase((String) options.get("debug"));

	}

	@Override
	public boolean login() throws LoginException {
		if (callbackHandler == null)
			throw new LoginException("Error: no CallbackHandler available");

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("user name: ");
		callbacks[1] = new PasswordCallback("password: ", false);

		try {
			/**
			 * Zugriff auf Callbackhandler
			 */
			callbackHandler.handle(callbacks);

			NameCallback nameCallback = (NameCallback) callbacks[0];
			PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];

			loginFormUserName = nameCallback.getName();
			loginFormPassword = new String(passwordCallback.getPassword());

			if (loginFormPassword == null) {
				loginFormPassword = new String();
			}
		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException("Error: " + uce.getCallback().toString()
					+ " not available to garner authentication information " + "from the user");
		}

		// don't ever do this in a real application!
		
		if (authService.validateAuthenticationValues(loginFormUserName, loginFormPassword)) {
			succeeded = true;
			((MainUI) UI.getCurrent()).loginSuccessful();
			return succeeded;
		} else {
			succeeded = false;
			Notification.show(authService.getMessageForAuthentication());
			return succeeded;
		}
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}


}
