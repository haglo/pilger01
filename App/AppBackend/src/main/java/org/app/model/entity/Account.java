package org.app.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
		{ 	@NamedQuery(name = Account.QUERY_FIND_ALL, query = "SELECT c FROM Account c"),
			@NamedQuery(name = Account.QUERY_FIND_BY_USERNAME, query = "SELECT c FROM Account c WHERE c.username =  :username") 
		})
public class Account extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_ALL = "Account.FindAll";
	public static final String QUERY_FIND_BY_USERNAME = "Account.FindByUserName";

	private String username;

	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
