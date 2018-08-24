package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.app.model.entity.enums.DefaultLanguage;
import org.app.model.entity.enums.DefaultTheme;

@Entity
@NamedQueries({ @NamedQuery(name = Settings.QUERY_FIND_ALL, query = "SELECT c FROM Settings c") })
public class Settings extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String QUERY_FIND_ALL = "Settings.FindAll";

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

	private String defaultWindowWidth;

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

	public String getDefaultWindowWidth() {
		return defaultWindowWidth;
	}

	public void setDefaultWindowWidth(String defaultWindowWidth) {
		defaultWindowWidth = defaultWindowWidth;
	}

}
