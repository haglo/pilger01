package org.app.model.entity;

import java.io.Serializable;

import org.app.model.audit.RevInfo;
import org.hibernate.envers.RevisionType;


public class Person_AUD implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Person person;
	private RevInfo revision;
	private RevisionType revtype;
	
	public Person_AUD() {
		
	}

	public Person_AUD(Person person, RevInfo revision, RevisionType revtype) {
		this.person = person;
		this.revision =revision;
		this.revtype = revtype;
	}

	public Person getPerson() {
		return person;
	}

	public RevInfo getRevision() {
		return revision;
	}

	public RevisionType getRevType() {
		return revtype;
	}

	public Person_AUD getAuditQueryResult(Object[] item) {
		if (item == null || item.length < 3) {
			return null;
		}

		person = (Person) item[0];
		revision = (RevInfo) item[1];
		revtype = (RevisionType) item[2];

		Person_AUD person_aud = new Person_AUD(person, revision, revtype);

		return person_aud;
	}
}
