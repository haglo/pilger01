package org.app.model.audit;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import org.app.model.entity.ElytronUser;

@Stateful
@SessionScoped
public class LoggedInUser  {

	private Integer currentUserID;
	
	private ElytronUser elytronUser;
	
	public ElytronUser getElytronUser() {
		return elytronUser;
	}

	public void setElytronUser(ElytronUser elytronUser) {
		this.elytronUser = elytronUser;
	}


	public Integer getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(Integer currentUserID) {
		this.currentUserID = currentUserID;
	}

}
