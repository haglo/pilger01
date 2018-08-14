package org.app.model.entity;

import java.io.Serializable;
import org.app.model.audit.RevInfo;
import org.hibernate.envers.RevisionType;

public class Title_AUD implements Serializable {

	private static final long serialVersionUID = 1L;

	private Title reventity;
	private RevInfo revision;
	private RevisionType revtype;

	public Title_AUD() {

	}

	public Title_AUD(Title reventity, RevInfo revision, RevisionType revtype) {
		this.reventity = reventity;
		this.revision = revision;
		this.revtype = revtype;
	}

	public Title getReventity() {
		return reventity;
	}

	public RevInfo getRevision() {
		return revision;
	}

	public RevisionType getRevType() {
		return revtype;
	}

	public Title_AUD getAuditQueryResult(Object[] item) {
		if (item == null || item.length < 3) {
			return null;
		}

		reventity = (Title) item[0];
		revision = (RevInfo) item[1];
		revtype = (RevisionType) item[2];

		Title_AUD auditedEntity = new Title_AUD(reventity, revision, revtype);

		return auditedEntity;
	}
}
