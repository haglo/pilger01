package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.app.model.entity.enums.DefaultLanguage;
import org.app.model.entity.enums.DefaultTheme;

@Entity
@NamedQueries({ @NamedQuery(name = ElytronUser.QUERY_FIND_ALL, query = "SELECT c FROM ElytronUser c"),
		@NamedQuery(name = ElytronUser.QUERY_FIND_BY_USERNAME, query = "SELECT c FROM ElytronUser c WHERE c.username =  :username") })
public class ElytronUser extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_ALL = "ElytronUser.FindAll";
	public static final String QUERY_FIND_BY_USERNAME = "ElytronUser.FindByUserName";

	@Column(unique = true, length = 45)
	private String username;

	/**
	 * Einbinden: Entity Role über ComboBox
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ELYTRON_ROLE_ID")
	private ElytronRole rolename;
	
	/**
	 * Einbinden: Enum Language über ComboBox
	 */
	@Enumerated(EnumType.STRING)
	private DefaultLanguage defaultLanguage;

	/**
	 * Einbinden: Enum Theme über ComboBox
	 */
	@Enumerated(EnumType.STRING)
	private DefaultTheme defaultTheme;

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ElytronRole getRolename() {
		return rolename;
	}

	public void setRolename(ElytronRole rolename) {
		this.rolename = rolename;
	}

	public DefaultLanguage getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(DefaultLanguage defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public DefaultTheme getDefaultTheme() {
		return defaultTheme;
	}

	public void setDefaultTheme(DefaultTheme defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

}
