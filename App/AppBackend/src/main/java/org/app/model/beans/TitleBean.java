package org.app.model.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.model.audit.RevInfo;
import org.app.model.dao.TitleDAO;
import org.app.model.entity.Person;
import org.app.model.entity.Person_AUD;
import org.app.model.entity.Title;
import org.app.model.entity.Title_AUD;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

@Stateless
@Remote(TitleDAO.class)
public class TitleBean implements TitleDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Title create(Title title) {
		em.persist(title);
		return title;
	}

	@Override
	public Title update(Title title) {
		try {
			return em.merge(title);
		} finally {
			em.flush();
		}
	}

	@Override
	public void remove(Integer id) {
		Title toBeDeleted = findByID(id);
		em.remove(toBeDeleted);
	}

	@Override
	public List<Title> findAll() {
		return em.createNamedQuery(Title.QUERY_FIND_ALL, Title.class).getResultList();
	}

	@Override
	public Title findByID(Integer id) {
		return em.find(Title.class, id);
	}

	@Override
	public List<Title> findByPriority(Integer listPrio) {
		return em.createNamedQuery(Title.QUERY_FIND_BY_PRIORITY, Title.class).setParameter("listPrio", listPrio)
				.getResultList();
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<Title_AUD> findAudById(Integer id) {
		List<Title_AUD> listAuditedEntities = new ArrayList<Title_AUD>();

		AuditReader auditReader = AuditReaderFactory.get(em);
		List<Object[]> revDatas = auditReader.createQuery().forRevisionsOfEntity(Title.class, false, false)
				.add(AuditEntity.id().eq(id)).getResultList();

		for (Object[] revData : revDatas) {
			listAuditedEntities.add(
					new Title_AUD((Title) revData[0], (RevInfo) revData[1], (RevisionType) revData[2]));
		}
		return listAuditedEntities;

	}

}
