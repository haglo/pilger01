package org.app.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Address> addresses = new HashSet<Address>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Communication> communications = new HashSet<Communication>();

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void addAddress(Address address) {
		address.setPerson(this);
		getAddresses().add(address);
	}

//	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void removeAddress(Address address) {
		getAddresses().remove(address);
		address.setPerson(null);
	}

//	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void updateAddress(Address address) {
		for (Address entry : addresses) {
			if (entry.getUuid().equals(address.getUuid())) {
				entry.setPerson(this);
				getAddresses().remove(entry);
				getAddresses().add(entry);
			}
		}
	}

	public Set<Communication> getCommunications() {
		return communications;
	}

	public void setCommunications(Set<Communication> communications) {
		this.communications = communications;
	}

	public void addCommunication(Communication communication) {
		communications.add(communication);
		communication.setPerson(this);
	}

	public void removeCommunication(Communication communication) {
		communications.remove(communication);
		communication.setPerson(null);
	}

}
