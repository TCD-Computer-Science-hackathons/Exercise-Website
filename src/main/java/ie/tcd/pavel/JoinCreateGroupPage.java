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

		Label debugLabel = new Label();

		HorizontalLayout horLay = new HorizontalLayout (createGroup,joinGroup);
		add(horLay, debugLabel);

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

		Label joinErrorLabel = new Label();
		Label createErrorLabel = new Label();
		VerticalLayout verLay1 = new VerticalLayout (newGroupNameField, newGroupPassField, confirmCreateGroup, createErrorLabel);
		VerticalLayout verLay2 = new VerticalLayout (joinGroupNameField, joinGroupPassField, confirmJoinGroup, joinErrorLabel);

		dialog1.add(verLay1);
		dialog2.add(verLay2);

		confirmCreateGroup.addClickListener( event1 -> {
			// User is creating a new group
			if(!newGroupNameField.getValue().equals("") && !newGroupPassField.getValue().equals("")) {
				if(!database.groupExists(newGroupNameField.getValue())) {
					database.insertGroup(newGroupNameField.getValue(), newGroupPassField.getValue(), TemporarySessionHandler.getCurrentUser());
					dialog1.close();
					debugLabel.setText("Group Created Successfully");
					System.out.printf("[DEBUG] Group Created: Name - %s | Password - %s%n", newGroupNameField.getValue(), newGroupPassField.getValue());
				} else {
					verLay1.remove(createErrorLabel);
					createErrorLabel.setText("Group Name already exists");
					verLay1.add(createErrorLabel);
					System.out.printf("[DEBUG] Someone tried to create a group when the name already exists%n");
				}
			} else {
				verLay1.remove(createErrorLabel);
				createErrorLabel.setText("Group Name/Password is blank");
				verLay1.add(createErrorLabel);
			}
		});

		confirmJoinGroup.addClickListener( event1 -> {
			// User is attempting to join a group
			if(!joinGroupNameField.getValue().equals("") && !joinGroupPassField.getValue().equals("")) {
				if (database.groupExists(joinGroupNameField.getValue(), joinGroupPassField.getValue())) {
					database.insertGroup(joinGroupNameField.getValue(), joinGroupPassField.getValue(), TemporarySessionHandler.getCurrentUser());
					dialog2.close();
					debugLabel.setText("Group Joined Successfully");
					System.out.printf("[DEBUG] Group Joined: Name - %s | Password - %s%n", joinGroupNameField.getValue(), joinGroupPassField.getValue());
				} else {
					verLay2.remove(joinErrorLabel);
					joinErrorLabel.setText("Invalid Group Name/Password");
					verLay2.add(joinErrorLabel);
					System.out.printf("[DEBUG] Someone tried to join a group that does not exist. Password Incorrect.%n");
				}
			} else {
				verLay2.remove(joinErrorLabel);
				joinErrorLabel.setText("Group Name/Password is blank");
				verLay2.add(joinErrorLabel);
			}
		});

		createGroup.addClickListener(event -> {
			//createErrorLabel.setText("");
			dialog1.open();
		});

		joinGroup.addClickListener(event -> {
			//joinErrorLabel.setText("");
			dialog2.open();
		});
	}
}