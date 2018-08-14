package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

@Entity
@NamedQueries({ @NamedQuery(name = Title.QUERY_FIND_ALL, query = "SELECT c FROM Title c"),
		@NamedQuery(name = Title.QUERY_FIND_BY_PRIORITY, query = "SELECT c FROM Title c WHERE c.listPrio =  :listPrio") })
@Audited
public class Title extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_ALL = "Title.FindAll";
	public static final String QUERY_FIND_BY_PRIORITY = "Title.FindByPriority";

	@NotNull
	private int listPrio;

	@Column(unique = true, nullable = false)
	private String title;

	public int getListPrio() {
		return listPrio;
	}

	public void setListPrio(int listPrio) {
		this.listPrio = listPrio;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
