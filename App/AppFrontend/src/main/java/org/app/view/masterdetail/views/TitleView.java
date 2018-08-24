package org.app.view.masterdetail.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.app.controler.TitleService;
import org.app.helper.I18n;
import org.app.model.entity.Title;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(I18n.TITLE_VIEW)
public class TitleView extends VerticalLayout implements View {

	private I18n i18n;
	private ListDataProvider<Title> dataProvider;
	private Title selectedTitle;
	private Title prevEntry;
	private Title nextEntry;
	private Set<Title> selectedTitles;

	private TextField newValueField = new TextField();
	private TextField newCommentField = new TextField();

	private TitleService titleService;
	private Grid<Title> grid;

	public TitleView(TitleService tService) {
		setMargin(false);

		i18n = new I18n();
		this.titleService = tService;
		this.grid = new Grid<Title>();

		final List<Title> list = titleService.getTitleDAO().findAll();
		selectedTitles = new HashSet<>();
		dataProvider = DataProvider.ofCollection(list);
		dataProvider.setSortOrder(Title::getListPrio, SortDirection.ASCENDING);

		this.grid.setWidth("100%");

		grid.setSelectionMode(SelectionMode.MULTI);
		grid.addSelectionListener(event -> {
			selectedTitles = event.getAllSelectedItems();
		});

		grid.getEditor().setEnabled(true);
		grid.getEditor().addSaveListener(event -> {
			selectedTitle = event.getBean();
			updateRow(selectedTitle);
		});
		grid.getEditor().addCancelListener(event -> {
			refreshGrid();
		});

		grid.setDataProvider(dataProvider);
		grid.addColumn(Title::getListPrio).setCaption(i18n.BASIC_LIST_PRIO).setId(i18n.BASIC_LIST_PRIO);
		grid.addColumn(title -> title.getTitle()).setCaption(i18n.TITLE_VALUE).setEditorComponent(newValueField,
				Title::setTitle);
		grid.addColumn(Title::getComment).setCaption(i18n.BASIC_COMMENT).setEditorComponent(newCommentField,
				Title::setComment);

		Button edit = new Button(i18n.BASIC_EDIT);
		edit.setIcon(VaadinIcons.EDIT);

		Button add = new Button("+");
		add.addClickListener(event -> {
			getUI().addWindow(new TitleNewView(this));
		});

		Button delete = new Button("-");
		delete.addClickListener(event -> deleteRow());

		Button up = new Button();
		up.setIcon(VaadinIcons.ARROW_UP);
		up.addClickListener(event -> upRow());

		Button top = new Button();
		top.setIcon(VaadinIcons.UPLOAD_ALT);
		top.addClickListener(event -> topRow());

		Button down = new Button();
		down.setIcon(VaadinIcons.ARROW_DOWN);
		down.addClickListener(event -> downRow());

		Button bottom = new Button();
		bottom.setIcon(VaadinIcons.DOWNLOAD_ALT);
		bottom.addClickListener(event -> bottomRow());

		Button detail = new Button("", ev -> {
			if (onlyOneSelected(selectedTitles)) {
				for (Title entry : selectedTitles) {
					selectedTitle = entry;
					getUI().addWindow(new TitleDetailView(this, selectedTitle));
				}
				refreshGrid();
			}
		});
		detail.setIcon(VaadinIcons.PENCIL);
		
		Button aud = new Button("", ev -> {
			if (onlyOneSelected(selectedTitles)) {
				for (Title entry : selectedTitles) {
					selectedTitle = entry;
					getUI().addWindow(new TitleAuditView(selectedTitle, titleService));
				}
				refreshGrid();
			}
		});
		aud.setIcon(VaadinIcons.ELLIPSIS_V);

		CssLayout titleNavBar = new CssLayout(add, delete, up, top, down, bottom, detail, aud);
		titleNavBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		

		addComponent(grid);
		addComponent(titleNavBar);
	}

	private void deleteRow() {
		if (selectedTitles.size() == 0) {
			Notification.show(i18n.NOTIFICATION_NO_ITEM);
			return;
		}
		for (Title entry : selectedTitles) {
			titleService.getTitleDAO().remove(entry.getId());
		}
		refreshGrid();
	}

	private void upRow() {
		prevEntry = new Title();

		if (!onlyOneSelected(selectedTitles)) {
			return;
		}

		for (Title entry : selectedTitles) {
			selectedTitle = entry;
			if (selectedTitle.getListPrio() == 0) {
				Notification.show(i18n.NOTIFICATION_ONTOP);
				return;
			}
			prevEntry = (Title) titleService.getTitleDAO().findByPriority(selectedTitle.getListPrio()-1).get(0);

			selectedTitle.setListPrio(selectedTitle.getListPrio()-1);
			prevEntry.setListPrio(prevEntry.getListPrio() + 1);
			
			titleService.getTitleDAO().update(selectedTitle);
			titleService.getTitleDAO().update(prevEntry);
		}
		refreshGrid();
	}

	private void topRow() {
		if (!onlyOneSelected(selectedTitles)) {
			return;
		}

		for (Title entry : selectedTitles) {
			selectedTitle = entry;
		}

		if (selectedTitle.getListPrio() == 0) {
			Notification.show(i18n.NOTIFICATION_ONTOP);
			return;
		}

		List<Title> list = titleService.getTitleDAO().findAll();
		for (Title tmp : list) {
			if (tmp.getListPrio() < selectedTitle.getListPrio()) {
				tmp.setListPrio(tmp.getListPrio() + 1);
			}
			titleService.getTitleDAO().update(tmp);
		}
		selectedTitle.setListPrio(0);
		titleService.getTitleDAO().update(selectedTitle);
		refreshGrid();
	}

	private void downRow() {
		nextEntry = new Title();

		if (!onlyOneSelected(selectedTitles)) {
			return;
		}

		for (Title entry : selectedTitles) {
			selectedTitle = entry;
		}

		if (selectedTitle.getListPrio() == getMaxListPrio()) {
			Notification.show(i18n.NOTIFICATION_ONTBOTTOM);
			return;
		}
		nextEntry = (Title) titleService.getTitleDAO().findByPriority(selectedTitle.getListPrio() + 1).get(0);
		selectedTitle.setListPrio(selectedTitle.getListPrio() + 1);
		nextEntry.setListPrio(nextEntry.getListPrio() - 1);
		titleService.getTitleDAO().update(selectedTitle);
		titleService.getTitleDAO().update(nextEntry);
		refreshGrid();
	}

	private void bottomRow() {
		int maxListPrio = 0;

		if (!onlyOneSelected(selectedTitles)) {
			return;
		}

		for (Title entry : selectedTitles) {
			selectedTitle = entry;
		}

		maxListPrio = getMaxListPrio();
		if (selectedTitle.getListPrio() == maxListPrio) {
			Notification.show(i18n.NOTIFICATION_ONTBOTTOM);
			return;
		}

		List<Title> list = titleService.getTitleDAO().findAll();
		for (Title tmp : list) {
			if (tmp.getListPrio() > selectedTitle.getListPrio()) {
				tmp.setListPrio(tmp.getListPrio() - 1);
			}
			titleService.getTitleDAO().update(tmp);
		}
		selectedTitle.setListPrio(maxListPrio);
		titleService.getTitleDAO().update(selectedTitle);
		refreshGrid();
	}

	public void updateRow(Title title) {
		titleService.getTitleDAO().update(title);
		refreshGrid();
	}

	public void refreshGrid() {
		List<Title> list = titleService.getTitleDAO().findAll();
		grid.sort(i18n.BASIC_LIST_PRIO, SortDirection.ASCENDING);
		grid.setItems(list);
	}

	public int getMaxListPrio() {
		int maxListPrio;
		List<Title> list = titleService.getTitleDAO().findAll();
		maxListPrio = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getListPrio() > maxListPrio) {
				maxListPrio = list.get(i).getListPrio();
			}
		}
		return maxListPrio;
	}

	private boolean onlyOneSelected(Set<Title> selected) {
		boolean isCorrect = true;
		if (selected.size() > 1) {
			Notification.show(i18n.NOTIFICATION_ONLY_ONE_ITEM);
			isCorrect = false;
		}
		if (selected.size() < 1) {
			Notification.show(i18n.NOTIFICATION_EXACT_ONE_ITEM);
			isCorrect = false;
		}
		return isCorrect;

	}

	public TitleService getTitleService() {
		return titleService;
	}

}