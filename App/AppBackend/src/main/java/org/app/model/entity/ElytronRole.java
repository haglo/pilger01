package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity()
@NamedQueries({ @NamedQuery(name = ElytronRole.QUERY_GET_ALL, query = "SELECT r FROM ElytronRole r") })
public class ElytronRole implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GET_ALL = "ElytronRole.GetAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String rolename;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}



}
