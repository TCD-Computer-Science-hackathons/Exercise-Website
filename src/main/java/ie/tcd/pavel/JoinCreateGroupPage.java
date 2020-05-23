package ie.tcd.pavel;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;


@Route("group")
public class JoinCreateGroupPage extends VerticalLayout {

	public JoinCreateGroupPage()
	{
		Button createGroup = new Button ("Create a Group");
		Button joinGroup = new Button ("Join a Group");
		Button confirm = new Button ("Confirm");
		HorizontalLayout horLay = new HorizontalLayout (createGroup,joinGroup);
		add(horLay);

		Dialog dialog1 = new Dialog();
		Dialog dialog2 = new Dialog();
		TextField entry1 = new TextField();
		TextField entry2 = new TextField();
		TextField entry3 = new TextField();
		TextField entry4 = new TextField();
		Div newGroupName = new Div();
		Div newGroupPass = new Div();
		Div joinGroupName = new Div();
		Div joinGroupPass = new Div();
		newGroupName.setText("Enter new Group's name: ");
		newGroupPass.setText("Enter new Group's password: ");
		joinGroupName.setText("Enter name of group you would like to join: ");
		joinGroupPass.setText("Enter password: ");

		VerticalLayout verLay1 = new VerticalLayout (newGroupName,entry1,newGroupPass,entry2);
		VerticalLayout verLay2 = new VerticalLayout (joinGroupName,entry3,joinGroupPass,entry4);

		dialog1.setWidth("400px");
		dialog1.setHeight("300px");
		dialog2.setWidth("400px");
		dialog2.setHeight("300px");
//
		createGroup.addClickListener(event -> {
			dialog1.open();
			dialog1.add(verLay1);
			dialog1.add(confirm);
			confirm.addClickListener( event1 -> {
				dialog1.close();

			});

		});

		joinGroup.addClickListener(event -> {
			dialog2.open();
			dialog2.add(verLay2);
			dialog2.add(confirm);
			confirm.addClickListener( event1 -> {
				dialog2.close();

			});
		});




	}
}