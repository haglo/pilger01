package org.app.view.person;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.app.controler.AccountService;
import org.app.controler.AddressService;
import org.app.controler.PersonService;
import org.app.helper.I18n;
import org.app.model.entity.Person;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(I18n.PERSON_VIEW)
@UIScoped
public class PersonView extends VerticalLayout implements View {

	@Inject
	PersonService personService;
	
	@Inject
	AddressService addressService;


	private I18n i18n;
	private Person selectedPerson;
	private Person newPerson;
	private Set<Person> selectedPersons;
	private SaveModus saveModus;
	private TextField txfFirstName = new TextField();
	private TextField txfLastName = new TextField();
	private TextField txfComment = new TextField();
	private VerticalSplitPanel mainContent;
	private VerticalLayout personContent;
	private Grid<Person> personGrid;
	private HorizontalLayout addressCommunicationContent;
	private AddressView addressView;
	private CommunicationView communicationView;

	public PersonView() {
		i18n = new I18n();
		saveModus = SaveModus.UPDATE;
		setSizeFull();
		setMargin(new MarginInfo(false, true, true, true));
		setWidth("1200px");
	}

	@PostConstruct
	void init() {
		mainContent = new VerticalSplitPanel();
		mainContent.setSplitPosition(50, Unit.PERCENTAGE);
		mainContent.setSizeFull();

		personContent = new VerticalLayout();
		personContent.setWidth("100%");
		addressCommunicationContent = new HorizontalLayout();
		addressCommunicationContent.setWidth("100%");

		saveModus = SaveModus.UPDATE;
		selectedPersons = new HashSet<Person>();
		selectedPerson = new Person();
		newPerson = new Person();

		personGrid = new Grid<Person>();
		personGrid.setWidth("100%");
		personContent.setMargin(false);

		List<Person> personList = personService.getPersonDAO().findAll();
		personList.sort(Comparator.comparing(Person::getLastName));

		DataProvider<Person, ?> dataProvider = DataProvider.ofCollection(personList);
		personGrid.setSelectionMode(SelectionMode.MULTI);

		personGrid.addSelectionListener(event -> {
			selectedPerson = new Person();
			selectedPersons = new HashSet<Person>();
			selectedPersons = event.getAllSelectedItems();
			if (selectedPersons.size() == 0) {
				addressCommunicationContent.removeComponent(addressView);
				addressCommunicationContent.removeComponent(communicationView);
				mainContent.removeComponent(addressCommunicationContent);
			} else {
				selectedPerson = getTheSelectedPerson(selectedPersons);
				if (selectedPerson != null) {
					if (addressCommunicationContent.isAttached()) {
						removeComponent(addressCommunicationContent);
					}
					showAddresses(selectedPerson);
					showCommunication(selectedPerson);
					mainContent.setSecondComponent(addressCommunicationContent);
				}
			}
		});

		personGrid.getEditor().setEnabled(true);
		personGrid.getEditor().addSaveListener(event -> {
			selectedPerson = event.getBean();
			if (saveModus == SaveModus.UPDATE) {
				updateRow(selectedPerson);
			} else {
				createPerson(selectedPerson);
			}
		});

		personGrid.getEditor().addCancelListener(event -> {
			selectedPerson = new Person();
			if (saveModus == SaveModus.UPDATE) {
				selectedPerson = event.getBean();
			} else {
				newPerson = event.getBean();
				deletePerson(newPerson);
			}

		});

		personGrid.setDataProvider(dataProvider);

		personGrid.addColumn(Person::getFirstName).setCaption(i18n.PERSON_SURNAME).setEditorComponent(txfFirstName,
				Person::setFirstName);
		personGrid.addColumn(Person::getLastName).setCaption(i18n.PERSON_LASTNAME).setEditorComponent(txfLastName,
				Person::setLastName);
		personGrid.addColumn(Person::getComment).setCaption(i18n.BASIC_COMMENT).setEditorComponent(txfComment,
				Person::setComment);

		Button add = new Button("+");
		add.addClickListener(event -> {
			saveModus = SaveModus.NEW;
			addRow();
		});

		Button delete = new Button("-");
		delete.addClickListener(event -> deleteRow(selectedPersons));

		Button detail = new Button("", ev -> {
			if (getTheSelectedPerson(selectedPersons) != null) {
				getUI().addWindow(new PersonDetailView(getTheSelectedPerson(selectedPersons), this));
			}
			refreshGrid();

		});
		detail.setIcon(VaadinIcons.PENCIL);

		CssLayout personNavBar = new CssLayout(add, delete, detail);
		personNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		personContent.addComponent(personGrid);
		personContent.addComponent(personNavBar);

		mainContent.setFirstComponent(personContent);
		mainContent.setSecondComponent(addressCommunicationContent);
		addComponent(mainContent);
	}

	private void showAddresses(Person selectedPerson) {

		try {
			if (addressView.isAttached()) {
				removeComponent(addressView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		addressView = new AddressView(selectedPerson, personService);
		addressCommunicationContent.addComponent(addressView);
	}

	private void showCommunication(Person selectedPerson) {

		try {
			if (communicationView.isAttached()) {
				removeComponent(communicationView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		communicationView = new CommunicationView(selectedPerson, personService);
		addressCommunicationContent.addComponent(communicationView);
	}

	private Person getTheSelectedPerson(Set<Person> selectedPersons) {
		selectedPerson = new Person();
		if (selectedPersons.size() > 1) {
			Notification.show(i18n.NOTIFICATION_ONLY_ONE_ITEM);
			return null;
		}
		if (selectedPersons.size() < 1) {
			Notification.show(i18n.NOTIFICATION_EXACT_ONE_ITEM);
			return null;
		}
		if (selectedPersons.size() == 1) {
			for (Person person : selectedPersons) {
				selectedPerson = person;
			}
		}
		return selectedPerson;

	}

	private void addRow() {
		List<Person> list = personService.getPersonDAO().findAll();
		newPerson = new Person();

		newPerson.setFirstName("");
		newPerson.setLastName("");
		newPerson.setComment("");
		list.add(newPerson);
		personGrid.setItems(list);

		personGrid.getEditor().editRow(list.size() - 1);
		txfFirstName.focus();

		createPerson(newPerson);
	}

	private void deleteRow(Set<Person> selectedPersons) {
		if (selectedPersons.size() == 0) {
			Notification.show(i18n.NOTIFICATION_NO_ITEM);
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
			personService.getPersonDAO().create(person);
			refreshGrid();
		} catch (Exception e) {
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

}