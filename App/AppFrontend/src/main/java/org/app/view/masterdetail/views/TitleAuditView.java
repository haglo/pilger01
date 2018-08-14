package org.app.view.masterdetail.views;

import java.util.List;

import org.app.controler.TitleService;
import org.app.model.entity.Title;
import org.app.model.entity.Title_AUD;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TitleAuditView extends Window {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("static-access")
	public TitleAuditView(Title selectedTitle, TitleService service) {

		this.setCaption("Title History");
		VerticalLayout subContent = new VerticalLayout();
		this.setContent(subContent);
		this.center();

		Grid<Title_AUD> grid = new Grid<Title_AUD>();
		List<Title_AUD> list = service.getTitleDAO().findAudById(selectedTitle.getId());
		DataProvider<Title_AUD, ?> dataProvider = DataProvider.ofCollection(list);
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);


		grid.addColumn(aud -> {
			String result = "" + aud.getRevision().getRevisionDate();
			return result;
		}).setCaption("Datum");
		
		grid.addColumn(aud -> {
			String result = "" + aud.getReventity().getListPrio();
			return result;
		}).setCaption("Listposition");

		grid.addColumn(aud -> {
			String result = "" + aud.getReventity().getTitle();
			return result;
		}).setCaption("Titel");

		grid.addColumn(aud -> {
			String result = "" + aud.getRevision().getUserId();
			return result;
		}).setCaption("Benutzer ID");

		grid.addColumn(aud -> {
			String result = "" + aud.getRevision().getElytronUser().getUsername();
			return result;
		}).setCaption("Benutzer Name");
 
		grid.addColumn(aud -> {
			String result = "" + aud.getRevType();
			return result;
		}).setCaption("Art");

		subContent.addComponent(grid);
		this.setWidth("80%");

	}

}
