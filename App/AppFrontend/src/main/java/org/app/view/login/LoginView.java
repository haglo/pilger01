package org.app.view.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.app.controler.jaas.AuthService;
import org.app.helper.I18n;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
@CDIView(I18n.LOGIN_VIEW)
@UIScoped
public class LoginView extends VerticalLayout implements View {
	
	@Inject 
	AuthService authService;

	private Button loginButton;
	private VerticalLayout centeringLayout;

	public LoginView() {
		setSpacing(true);
		setStyleName("pilger-login-view");
	}

	@PostConstruct
	void init() {
		Component loginForm = buildLoginForm();
		centeringLayout = new VerticalLayout();
		centeringLayout.addComponent(loginForm);
		addComponent(centeringLayout);
	}

	private Component buildLoginForm() {
		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();
		loginForm.setMargin(false);

		Label welcomeMessage = new Label("Sign In");
		TextField username = new TextField("");
		PasswordField password = new PasswordField("");

		loginButton = new Button("Login", event -> {
			try {
				if (preAuthentication(username.getValue(), password.getValue())) {
					JaasAccessControl.login(username.getValue(), password.getValue());
					Notification.show(authService.getMessageForAuthentication());
					Page page = Page.getCurrent();
					page.reload();					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		loginButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		loginForm.addComponent(welcomeMessage);
		loginForm.addComponent(username);
		loginForm.addComponent(password);
		loginForm.addComponent(loginButton);

		return loginForm;
	}
	
	private boolean preAuthentication(String username, String password) {
		return authService.validateAuthenticationValues(username, password);
	}

}