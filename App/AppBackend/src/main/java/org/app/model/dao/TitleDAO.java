package org.app.model.dao;

import java.util.List;

import org.app.model.entity.Title;
import org.app.model.entity.Title_AUD;

public interface TitleDAO {

	public Title create(Title title);

	public Title update(Title title);

	public void remove(Integer id);

	public List<Title> findAll();

	public Title findByID(Integer id);

	public List<Title> findByPriority(Integer listPrio);
	
	public List<Title_AUD> findAudById(Integer id);

}