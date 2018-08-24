package org.app.view.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.app.controler.PersonService;
import org.app.helper.I18n;
import org.app.model.dao.PersonDAO;
import org.app.model.entity.Address;
import org.app.model.entity.Person;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AddressView extends VerticalLayout {

	private I18n i18n;
	private Person selectedPerson;
	private PersonDAO personDAO;
	private Grid<Address> grid;
	private ListDataProvider<Address> addressDataProvider;
	private Address selectedAddress;
	private Set<Address> selectedAddresses;
	private Set<Address> personAddresses;

	private SaveModus saveModus;
	private TextField txfStreet = new TextField();
	private TextField txfPostbox = new TextField();
	private TextField txfZip = new TextField();
	private TextField txfCity = new TextField();
	private TextField txfComment = new TextField();

	public AddressView(Person person, PersonService service) {
		setMargin(new MarginInfo(false, true, false, false));
		setSizeFull();
		saveModus = SaveModus.UPDATE;
		this.selectedPerson = person;
		this.personDAO = service.getPersonDAO();
		this.selectedAddresses = new HashSet<Address>();

		i18n = new I18n();
		personAddresses = new LinkedHashSet<Address>();
		personAddresses = selectedPerson.getAddresses();

		addressDataProvider = DataProvider.ofCollection(personAddresses);
		grid = new Grid<Address>();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setDataProvider(addressDataProvider);

		grid.addSelectionListener(event -> {
			selectedAddresses = event.getAllSelectedItems();
		});

		grid.getEditor().setEnabled(true);
		grid.getEditor().addSaveListener(event -> {
			selectedAddress = new Address();
			selectedAddress = event.getBean();
			if (saveModus == SaveModus.UPDATE) {
				selectedPerson.updateAddress(selectedAddress);
				personDAO.update(selectedPerson);
				resetGrid();
			}
			if (saveModus == SaveModus.NEW) {
				selectedPerson.addAddress(selectedAddress);
				personDAO.update(selectedPerson);
//				personDAO.addAddress(selectedAddress, selectedPerson);
//				service.addAddress(selectedAddress, selectedPerson);
				resetGrid();
			}
		});

		grid.getEditor().addCancelListener(event -> {
			if (saveModus == SaveModus.NEW) {
				resetGrid();
			}
		});

		grid.addColumn(address -> address.getId()).setCaption(i18n.BASIC_ID).setId(i18n.BASIC_ID).setHidden(true);
		grid.addColumn(address -> address.getPostbox()).setCaption(i18n.PERSON_POSTBOX).setEditorComponent(txfPostbox,
				Address::setPostbox);
		grid.addColumn(address -> address.getStreet()).setCaption(i18n.PERSON_STREET).setEditorComponent(txfStreet,
				Address::setStreet);
		grid.addColumn(address -> address.getZip()).setCaption(i18n.PERSON_ZIPCODE).setEditorComponent(txfZip,
				Address::setZip);
		grid.addColumn(address -> address.getCity()).setCaption(i18n.PERSON_CITY).setEditorComponent(txfCity,
				Address::setCity);
		grid.addColumn(address -> address.getComment()).setCaption(i18n.BASIC_COMMENT).setEditorComponent(txfComment,
				Address::setComment);

		Button add = new Button("+");
		add.addClickListener(event -> addRow());

		Button delete = new Button("-");
		delete.addClickListener(event -> deleteRow());

		CssLayout addressNavBar = new CssLayout(add, delete);
		addressNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		addComponent(grid);
		addComponent(addressNavBar);
	}

	private void addRow() {
		saveModus = SaveModus.NEW;
		Address newAddress = new Address();
		newAddress.setPerson(selectedPerson);
		personAddresses.add(newAddress);
		addressDataProvider.refreshAll();
		
		grid.sort(i18n.BASIC_ID, SortDirection.ASCENDING);
		grid.getEditor().editRow(personAddresses.size() - 1);
		txfPostbox.focus();
	}

	private void deleteRow() {
		if (selectedAddresses.size() == 0) {
			Notification.show(i18n.NOTIFICATION_NO_ITEM);
			return;
		}
		for (Address entityItem : selectedAddresses) {
			selectedPerson.removeAddress(entityItem);
		}
		personDAO.update(selectedPerson);
		resetGrid();
	}

	private enum SaveModus {
		NEW, UPDATE
	}

	public void resetGrid() {
		saveModus = SaveModus.UPDATE;
		addressDataProvider.refreshAll();
	}

}