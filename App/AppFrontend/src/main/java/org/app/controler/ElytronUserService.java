
package org.app.controler;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import org.app.model.audit.LoggedInUser;
import org.app.model.dao.ElytronUserDAO;
import org.app.model.entity.ElytronUser;


/*
 * Managed Bean
 * In MVC the Controler-Part
 */
@RequestScoped
public class ElytronUserService {

	@EJB
	private ElytronUserDAO elytronUserDAO;

	private boolean isEditing = false;


	public boolean getEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public void toggleEditing() {
		this.isEditing = !this.isEditing;
	}
	
	public ElytronUserDAO getElytronUserDAO() {
		return elytronUserDAO;
	}
}
