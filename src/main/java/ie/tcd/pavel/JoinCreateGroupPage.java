package ie.tcd.pavel;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
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
		
		Dialog dialog = new Dialog();
		TextField groupNameIn = new TextField();
		TextField groupPassIn = new TextField();
		TextField newGroupNameIn = new TextField();
		TextField newGroupPassIn = new TextField();
		Div head1 = new Div();
		Div head2 = new Div();
		
		VerticalLayout verLay = new VerticalLayout (head1,groupNameIn,head2,groupPassIn,confirm);
		dialog.add(verLay);
		
		dialog.setWidth("400px");
		dialog.setHeight("200px");

		createGroup.addClickListener(event -> {
			head1.setText("Enter new group Name");
			head2.setText("Enter password for new group");
		    dialog.open();
		    groupNameIn.getElement().callJsFunction("focus");
		});
		
		joinGroup.addClickListener(event -> {
			head1.setText("Enter name of group you would like to join");
			head2.setText("Enter password of group you would like to join");
		    dialog.open();
		    groupNameIn.getElement().callJsFunction("focus");
		});
		
		
		

	}
}
