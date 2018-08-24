package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity()
@NamedQueries({ @NamedQuery(name = ElytronRole.QUERY_FIND_ALL, query = "SELECT r FROM ElytronRole r"),
		@NamedQuery(name = ElytronRole.QUERY_FIND_BY_ROLENAME, query = "SELECT c FROM ElytronRole c WHERE c.rolename =  :rolename")

})
public class ElytronRole extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_ALL = "ElytronRole.FindAll";
	public static final String QUERY_FIND_BY_ROLENAME = "ElytronRole.FindByUserName";

	private String rolename;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
