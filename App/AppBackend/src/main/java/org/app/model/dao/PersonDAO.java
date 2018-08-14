package org.app.model.dao;

import java.util.List;
import org.app.model.entity.Person;
import org.app.model.entity.Person_AUD;

public interface PersonDAO {

	public Person create(Person person);

	public Person update(Person person);

	public void remove(Integer id);

	public Person findByID(Integer id);

	public List<Person> findAll();

	public List<Person_AUD> findAudById(Integer personId);

}