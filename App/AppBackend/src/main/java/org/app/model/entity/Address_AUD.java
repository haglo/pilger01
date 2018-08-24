package org.app.model.entity;

import java.io.Serializable;

import org.app.model.audit.RevInfo;
import org.hibernate.envers.RevisionType;


public class Address_AUD implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Address entity;
	private RevInfo revision;
	private RevisionType revtype;
	
	public Address_AUD() {
		
	}

	public Address_AUD(Address entity, RevInfo revision, RevisionType revtype) {
		this.entity = entity;
		this.revision =revision;
		this.revtype = revtype;
	}

	public Address getEntity() {
		return entity;
	}

	public RevInfo getRevision() {
		return revision;
	}

	public RevisionType getRevType() {
		return revtype;
	}

	public Address_AUD getAuditQueryResult(Object[] item) {
		if (item == null || item.length < 3) {
			return null;
		}

		entity = (Address) item[0];
		revision = (RevInfo) item[1];
		revtype = (RevisionType) item[2];

		Address_AUD person_aud = new Address_AUD(entity, revision, revtype);

		return person_aud;
	}
}
