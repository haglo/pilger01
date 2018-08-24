package org.app.view.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.app.controler.PersonService;
import org.app.helper.I18n;
import org.app.model.dao.PersonDAO;
import org.app.model.entity.Address;
import org.app.model.entity.Communication;
import org.app.model.entity.CommunicationType;
import org.app.model.entity.ElytronRole;
import org.app.model.entity.Person;
import org.app.model.entity.Title;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class CommunicationView extends VerticalLayout {

	private Person selectedPerson;
	private I18n i18n;
	private Grid<Communication> grid;
	private ListDataProvider<Communication> communicationDataProvider;
	private Set<Communication> communicationList;

	private TextField newCommunicationField = new TextField();
	private TextField newCommentField = new TextField();

	public CommunicationView(Person person, PersonService service) {
		setMargin(new MarginInfo(false, false, false, true));
		setSizeFull();
		
		this.selectedPerson = person;

		i18n = new I18n();
		communicationList = new TreeSet<Communication>();
		communicationList = selectedPerson.getCommunications();
		
		communicationDataProvider = DataProvider.ofCollection(communicationList);
		grid = new Grid<Communication>();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setDataProvider(communicationDataProvider);
		
		grid.addColumn(Communication::getCommunication).setCaption(i18n.PERSON_COMMUNICATION).setEditorComponent(newCommunicationField,
				Communication::setCommunication);

		grid.addColumn(Communication::getComment).setCaption(i18n.BASIC_COMMENT).setEditorComponent(newCommentField,
				Communication::setComment);

		Button add = new Button("+");
		Button delete = new Button("-");

		CssLayout communicationNavBar = new CssLayout(add, delete);
		communicationNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		addComponent(grid);
		addComponent(communicationNavBar);
	}
}