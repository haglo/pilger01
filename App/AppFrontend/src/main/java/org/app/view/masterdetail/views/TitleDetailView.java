package org.app.view.masterdetail.views;

import org.app.controler.TitleService;
import org.app.helper.I18n;
import org.app.model.entity.Title;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TitleDetailView extends Window {

	private static final long serialVersionUID = 1L;

	private TextField txfListPrio;
	private TextField txfValue;
	private TextArea txaComment;
	private CheckBox ckbEdit;
	private Button saveButton;

	@SuppressWarnings("static-access")
	public TitleDetailView(TitleView titleView, Title selectedTitle) {
		I18n i18n = new I18n();
		TitleService titleService = titleView.getTitleService();
		saveButton = new Button(i18n.BASIC_SAVE);

		this.setCaption(i18n.TITLE_WINDOW_DETAIL_CAPTION);
		VerticalLayout subContent = new VerticalLayout();
		this.setContent(subContent);
		this.center();

		try {
			titleService.setEditing(false);

			TextField txfID = new TextField("ID", "" + selectedTitle.getId());
			subContent.addComponent(txfID);

			TextField txfUUID = new TextField("UUID", "" + selectedTitle.getUuid());
			subContent.addComponent(txfUUID);

			txfListPrio = new TextField(i18n.BASIC_LIST_PRIO, "" + selectedTitle.getListPrio());
			subContent.addComponent(txfListPrio);

			txfValue = new TextField(i18n.TITLE_VALUE, "" + selectedTitle.getTitle());
			subContent.addComponent(txfValue);

			txaComment = new TextArea(i18n.BASIC_COMMENT, "" + selectedTitle.getComment());
			subContent.addComponent(txaComment);

			ckbEdit = new CheckBox(i18n.BASIC_EDIT);
			ckbEdit.addValueChangeListener(event -> {
				titleService.toggleEditing();
				if (event.getValue()) {
					saveButton.setEnabled(true);
				} else {
					saveButton.setEnabled(false);
				}
			});

			subContent.addComponent(ckbEdit);

			saveButton.setEnabled(titleService.getEditing());
			subContent.addComponent(saveButton);

			saveButton.addClickListener(event -> {
				selectedTitle.setListPrio(Integer.valueOf(txfListPrio.getValue()));
				selectedTitle.setTitle(txfValue.getValue());
				selectedTitle.setComment(txaComment.getValue());
				titleView.updateRow(selectedTitle);
				titleView.refreshGrid();
				getUI().getCurrent().removeWindow(this);
			});

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
