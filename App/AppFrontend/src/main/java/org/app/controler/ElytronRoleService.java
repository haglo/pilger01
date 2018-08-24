
package org.app.controler;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import org.app.model.audit.LoggedInUser;
import org.app.model.dao.ElytronRoleDAO;
import org.app.model.dao.ElytronUserDAO;
import org.app.model.entity.ElytronUser;


/*
 * Managed Bean
 * In MVC the Controler-Part
 */
@RequestScoped
public class ElytronRoleService implements Serializable {

 	private static final long serialVersionUID = 1L;

	@EJB
	private ElytronRoleDAO elytronRoleDAO;

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
	
	public ElytronRoleDAO getElytronRoleDAO() {
		return elytronRoleDAO;
	}
}
