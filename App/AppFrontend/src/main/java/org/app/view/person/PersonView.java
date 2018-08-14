package org.app.view.person;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.app.controler.AccountService;
import org.app.controler.PersonService;
import org.app.helper.I18n;
import org.app.model.audit.CustomRevisionListener;
import org.app.model.audit.LoggedInUser;
import org.app.model.beans.ElytronUserBean;
import org.app.model.entity.Person;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(I18n.PERSON_VIEW)
@UIScoped
public class PersonView extends VerticalLayout implements View {
	

	@Inject
	PersonService personService;


	private Person selectedPerson;
	private Person newPerson;
	private Set<Person> selectedPersons;

	private SaveModus saveModus;
	private TextField txfFirstName = new TextField();
	private TextField txfLastName = new TextField();
	private TextField txfComment = new TextField();


	private VerticalLayout mainContent;
	private VerticalLayout personContent;
	private Grid<Person> personGrid;
	private CssLayout personNavBar;
	private HorizontalLayout addressCommunicationContent;

	public PersonView() {
		setSizeFull();
		setMargin(new MarginInfo(false, true, true, true));
	}

	@PostConstruct
	void init() {
		mainContent = new VerticalLayout();
		
		personContent = new VerticalLayout();
		personGrid = new Grid<Person>();
		personNavBar = new CssLayout();
		personNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		addressCommunicationContent = new HorizontalLayout();

		saveModus = SaveModus.UPDATE;
		selectedPersons = new HashSet<Person>();
		selectedPerson = new Person();
		newPerson = new Person();

		personGrid.setWidth("100%");
		mainContent.setMargin(false);
		personContent.setMargin(false);
		addressCommunicationContent.setWidth("100%");

		List<Person> personList = personService.getPersonDAO().findAll();
		personList.sort(Comparator.comparing(Person::getLastName));

		DataProvider<Person, ?> dataProvider = DataProvider.ofCollection(personList);
		personGrid.setSelectionMode(SelectionMode.MULTI);

		personGrid.addSelectionListener(event -> {
			saveModus = SaveModus.UPDATE;
			selectedPerson = new Person();
			selectedPersons = new HashSet<Person>();
			selectedPersons = event.getAllSelectedItems();
			if (selectedPersons.size() != 1) {
				mainContent.removeComponent(addressCommunicationContent);
			} else {
				if (selectedPerson != null) {
					mainContent.addComponent(addressCommunicationContent);
				} else  {
					saveModus = SaveModus.NEW;
				}
			}
		});

		personGrid.getEditor().setEnabled(true);

		personGrid.getEditor().addSaveListener(event -> {
			selectedPerson = event.getBean();
			if (saveModus == SaveModus.UPDATE) {
				updateRow(selectedPerson);
			} else {
				newPerson = new Person();
				newPerson.setFirstName(txfFirstName.getValue());
				newPerson.setLastName(txfLastName.getValue());
				createPerson(newPerson);
			}
		});

		personGrid.getEditor().addCancelListener(event -> {
			if (saveModus == SaveModus.UPDATE) {
				selectedPerson = new Person();
				selectedPerson = event.getBean();
			} else {
				newPerson = new Person();
				newPerson = event.getBean();
				deletePerson(newPerson);
			}

		});

		personGrid.setDataProvider(dataProvider);

		personGrid.addColumn(Person::getFirstName).setCaption("Vorname").setEditorComponent(txfFirstName,
				Person::setFirstName).setId("Vorname");
		personGrid.addColumn(Person::getLastName).setCaption("Nachname").setEditorComponent(txfLastName,
				Person::setLastName).setId("Nachname");
		personGrid.addColumn(Person::getComment).setCaption("Kommentar").setEditorComponent(txfComment,
				Person::setComment).setId("Kommentar");

		Button add = new Button("+");
		add.addClickListener(event -> {
			saveModus = SaveModus.NEW;
			addRow();
		});

		Button delete = new Button("-");
		delete.addClickListener(event -> deleteRow(selectedPersons));

		personNavBar.addComponents(add, delete);

		personContent.addComponent(personGrid);
		personContent.addComponent(personNavBar);

		mainContent.addComponent(personContent);
		mainContent.addComponent(addressCommunicationContent);

		mainContent.setExpandRatio(personContent, 0.7f);
		mainContent.setExpandRatio(addressCommunicationContent, 0.3f);

		addComponent(mainContent);
	}


	private void addRow() {
		List<Person> list = personService.getPersonDAO().findAll();
		newPerson = new Person();

		newPerson.setFirstName("");
		newPerson.setLastName("");
		list.add(newPerson);
		personGrid.setItems(list);

		personGrid.getEditor().editRow(list.size() - 1);
		txfFirstName.focus();
	}

	private void deleteRow(Set<Person> selectedPersons) {
		if (selectedPersons.size() == 0) {
			return;
		}
		for (Person person : selectedPersons) {
			personService.getPersonDAO().remove(person.getId());
		}
		refreshGrid();
	}

	public void updateRow(Person person) {
		personService.getPersonDAO().update(person);
		refreshGrid();
	}

	public void createPerson(Person person) {

		try {
			personService.getPersonDAO().update(person);
			
			refreshGrid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			saveModus = SaveModus.UPDATE;
		}
	}

	public void deletePerson(Person person) {
		try {
			personService.getPersonDAO().remove(person.getId());
			refreshGrid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			saveModus = SaveModus.UPDATE;
		}
	}

	public void refreshGrid() {
		List<Person> personList = personService.getPersonDAO().findAll();
		personGrid.setItems(personList);
	}

	private enum SaveModus {
		NEW, UPDATE
	}

	public PersonService getPersonService() {
		return personService;
	}

}