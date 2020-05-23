package ie.tcd.pavel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
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

	MongoDBOperations database;
	private boolean isNewGroup = false;

	public JoinCreateGroupPage()
	{

		database = BeanUtil.getBean(MongoDBOperations.class);
		Button createGroup = new Button ("Create a Group");
		Button joinGroup = new Button ("Join a Group");
		Button confirmCreateGroup = new Button ("Confirm");
		Button confirmJoinGroup = new Button("Confirm");

		HorizontalLayout horLay = new HorizontalLayout (createGroup,joinGroup);
		add(horLay);

		Dialog dialog1 = new Dialog();
		Dialog dialog2 = new Dialog();
		TextField newGroupNameField = new TextField();
		TextField newGroupPassField = new TextField();
		TextField joinGroupNameField = new TextField();
		TextField joinGroupPassField = new TextField();
		newGroupNameField.getStyle().set("width", "15em");
		newGroupNameField.setMaxLength(13);
		newGroupNameField.setLabel("New Group Name: ");
		newGroupPassField.getStyle().set("width", "15em");
		newGroupPassField.setMaxLength(13);
		newGroupPassField.setLabel("New Group Password: ");
		joinGroupNameField.getStyle().set("width", "15em");
		joinGroupNameField.setMaxLength(13);
		joinGroupNameField.setLabel("Group Name: ");
		joinGroupPassField.getStyle().set("width", "15em");
		joinGroupPassField.setMaxLength(13);
		joinGroupPassField.setLabel("Group Password: ");

		Label errorLabel = new Label();
		VerticalLayout verLay1 = new VerticalLayout (newGroupNameField, newGroupPassField, confirmCreateGroup, errorLabel);
		VerticalLayout verLay2 = new VerticalLayout (joinGroupNameField, joinGroupPassField, confirmJoinGroup, errorLabel);

		dialog1.add(verLay1);
		dialog2.add(verLay2);

		confirmCreateGroup.addClickListener( event1 -> {
			// User is creating a new group
			if(newGroupNameField.getValue() != null && newGroupPassField.getValue() != null) {
				if(!database.groupExists(newGroupNameField.getValue())) {
					database.insertGroup(newGroupNameField.getValue(), newGroupPassField.getValue(), TemporarySessionHandler.getCurrentUser());
					dialog1.close();
				} else {
					errorLabel.setText("Group Name already exists");
				}
			} else {
				errorLabel.setText("Group Name/Password is blank");
			}
		});

		confirmJoinGroup.addClickListener( event1 -> {
			// User is attempting to join a group
			if(joinGroupNameField.getValue() != null && joinGroupPassField.getValue() != null) {
				if (database.groupExists(joinGroupNameField.getValue(), joinGroupPassField.getValue())) {
					database.insertGroup(joinGroupNameField.getValue(), joinGroupPassField.getValue(), TemporarySessionHandler.getCurrentUser());
					dialog2.close();
				} else {
					errorLabel.setText("Invalid Group Name/Password");
				}
			} else {
				errorLabel.setText("Group Name/Password is blank");
			}
		});

		createGroup.addClickListener(event -> {
			dialog1.open();
			errorLabel.setText("");
		});

		joinGroup.addClickListener(event -> {
			dialog2.open();
			errorLabel.setText("");
		});
	}
}