package org.app.model.dao;

import java.util.List;
import org.app.model.entity.Communication;

public interface CommunicationDAO {

	public Communication create(Communication communication);

	public Communication update(Communication communication);

	public void remove(Integer id);

	public Communication findByID(Integer id);

	public List<Communication> findAll();
}