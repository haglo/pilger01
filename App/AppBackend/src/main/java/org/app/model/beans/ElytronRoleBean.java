package org.app.model.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.model.entity.ElytronRole;

@Stateless
public class ElytronRoleBean {

	@PersistenceContext
	private EntityManager em;

	public ElytronRole findByID(Integer id) {
		return em.find(ElytronRole.class, id);
	}

	public List<ElytronRole> findAll() {
		return em.createNamedQuery(ElytronRole.QUERY_GET_ALL, ElytronRole.class).getResultList();
	}

}
