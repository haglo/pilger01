package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.app.model.entity.enums.DefaultLanguage;
import org.app.model.entity.enums.DefaultTheme;

@Entity
@NamedQueries({ 
		@NamedQuery(name = ElytronUser.QUERY_FIND_ALL, query = "SELECT c FROM ElytronUser c"),
		@NamedQuery(name = ElytronUser.QUERY_FIND_ALL_EXPANDED, query = "SELECT c FROM ElytronUser c join fetch c.elytronRole"),
		@NamedQuery(name = ElytronUser.QUERY_FIND_BY_USERNAME, query = "SELECT c FROM ElytronUser c WHERE c.username =  :username")
		})
public class ElytronUser extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_ALL = "ElytronUser.FindAll";
	public static final String QUERY_FIND_ALL_EXPANDED = "ElytronUser.FindAllExpanded";
	public static final String QUERY_FIND_BY_USERNAME = "ElytronUser.FindByUserName";

	@Column(unique = true)
	private String username;

	/**
	 * Einbinden: Entity Role über ComboBox
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ELYTRON_ROLE_ID")
	private ElytronRole elytronRole;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ElytronRole getElytronRole() {
		return elytronRole;
	}

	public void setElytronRole(ElytronRole elytronRole) {
		this.elytronRole = elytronRole;
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
