package org.app.view.account;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.app.controler.ElytronRoleService;
import org.app.controler.ElytronUserService;
import org.app.helper.I18n;
import org.app.model.beans.ElytronRoleBean;
import org.app.model.entity.ElytronUser;
import org.app.model.entity.Title;
import org.app.model.entity.enums.DefaultLanguage;
import org.app.model.entity.enums.DefaultTheme;
import org.app.model.entity.ElytronRole;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(I18n.ELYTRON_USER_VIEW)
public class ElytronUserView extends HorizontalLayout implements View {

	@Inject
	ElytronUserService elytronUserService;
	
	@Inject
	ElytronRoleService elytronRoleService;

	private ElytronUser user;
	private Set<ElytronUser> selectedUsers;
	private TextField txfRolename = new TextField();
	private TextField txfComment = new TextField();
	private ComboBox<ElytronRole> cbxRole = new ComboBox<>();
	private ComboBox<DefaultLanguage> cbxLanguage = new ComboBox<>();
	private ComboBox<DefaultTheme> cbxTheme = new ComboBox<>();
	private Grid<ElytronUser> grid;

	public ElytronUserView() {
		setMargin(new MarginInfo(false, true, true, true));
	}

	@PostConstruct
	void init() {
		setSizeFull();
		setWidth("1200px");

		VerticalLayout content = new VerticalLayout();
		selectedUsers = new HashSet<>();
		List<ElytronUser> elytronUserList = elytronUserService.getElytronUserDAO().findAllExpanded();
		elytronUserList.sort(Comparator.comparing(ElytronUser::getId));

		List<ElytronRole> elytronRoleList = elytronRoleService.getElytronRoleDAO().findAll();

		DataProvider<ElytronUser, ?> dataProvider = DataProvider.ofCollection(elytronUserList);

		grid = new Grid<ElytronUser>();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.addSelectionListener(event -> {
			selectedUsers = event.getAllSelectedItems();
		});

		grid.getEditor().setEnabled(true);
		grid.getEditor().addSaveListener(event -> {
			user = event.getBean();
			updateRow(user);
		});

		cbxRole.setPageLength(8);
		cbxRole.setEmptySelectionAllowed(false);
		cbxRole.setItems(elytronRoleList);
		cbxRole.setItemCaptionGenerator(ElytronRole::getRolename);

		cbxLanguage.setPageLength(8);
		cbxLanguage.setEmptySelectionAllowed(false);
		cbxLanguage.setItems(EnumSet.allOf(DefaultLanguage.class));

		cbxTheme.setPageLength(8);
		cbxTheme.setEmptySelectionAllowed(false);
		cbxTheme.setItems(EnumSet.allOf(DefaultTheme.class));

		grid.setDataProvider(dataProvider);
		grid.addColumn(ElytronUser::getUsername).setCaption("Benutzername")
				.setEditorComponent(txfRolename, ElytronUser::setUsername).setId("Benutzername");
		grid.addColumn(ElytronUser::getElytronRole).setCaption("Rolle")
				.setRenderer(role -> role != null ? role.getRolename() : null, new TextRenderer())
				.setEditorComponent(cbxRole, ElytronUser::setElytronRole);
		grid.addColumn(ElytronUser::getDefaultLanguage).setCaption("Sprache").setEditorComponent(cbxLanguage,
				ElytronUser::setDefaultLanguage);
		grid.addColumn(ElytronUser::getDefaultTheme).setCaption("Design").setEditorComponent(cbxTheme,
				ElytronUser::setDefaultTheme);
		grid.addColumn(ElytronUser::getComment).setCaption("Kommentar")
				.setEditorComponent(txfComment, ElytronUser::setComment).setId("Kommentar");

		Button delete = new Button("-");
		delete.addClickListener(event -> deleteRow());

		CssLayout accountNavBar = new CssLayout(delete);
		accountNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		content.addComponent(grid);
		content.addComponent(accountNavBar);
		addComponent(content);
		setDefaultComponentAlignment(Alignment.TOP_CENTER);
	}

	private void deleteRow() {
		if (selectedUsers.size() == 0) {
			return;
		}
		for (ElytronUser entry : selectedUsers) {
			if (entry.getId()==1) {
				Notification.show("Can not delete User NUT - New User Template");
				continue;
			}
			elytronUserService.getElytronUserDAO().remove(entry.getId());
		}
		refreshGrid();
	}

	public void updateRow(ElytronUser puser) {
		elytronUserService.getElytronUserDAO().update(puser);
		refreshGrid();
	}

	public void refreshGrid() {
		List<ElytronUser> list = elytronUserService.getElytronUserDAO().findAllExpanded();
		grid.sort("Benutzername", SortDirection.ASCENDING);
		grid.setItems(list);
	}

	public ElytronUserService getElytronUserService() {
		return elytronUserService;
	}

}
