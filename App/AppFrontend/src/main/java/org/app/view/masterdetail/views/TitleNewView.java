package org.app.view.masterdetail.views;

import org.app.controler.TitleService;
import org.app.helper.I18n;
import org.app.model.entity.Title;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TitleNewView extends Window {

	private static final long serialVersionUID = 1L;

	private TextField txfValue;
	private TextArea txaComment;
	private Button saveButton;
	private TitleService titleService;
	private Title newTitle;

	@SuppressWarnings("static-access")
	public TitleNewView(TitleView titleView) {
		I18n i18n = new I18n();
		this.titleService = titleView.getTitleService();
		this.newTitle = new Title();

		this.setCaption(i18n.TITLE_WINDOW_NEW_CAPTION);
		VerticalLayout subContent = new VerticalLayout();
		this.setContent(subContent);
		this.center();

		try {
			txfValue = new TextField(i18n.TITLE_VALUE);
			subContent.addComponent(txfValue);

			txaComment = new TextArea(i18n.BASIC_COMMENT);
			subContent.addComponent(txaComment);

			saveButton = new Button(i18n.BASIC_SAVE);
			saveButton.setEnabled(true);
			subContent.addComponent(saveButton);

			saveButton.addClickListener(event -> {
				newTitle.setListPrio(titleView.getMaxListPrio() + 1);
				newTitle.setTitle(txfValue.getValue());
				newTitle.setComment(txaComment.getValue());
				titleService.getTitleDAO().create(newTitle);
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
