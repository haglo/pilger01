package org.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.envers.Audited;

@Entity
@Audited
@NamedQueries({ @NamedQuery(name = Person.QUERY_GET_ALL, query = "SELECT c FROM Person c") })
public class Person extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_GET_ALL = "Person.GetAll";

	private String firstName;

	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
