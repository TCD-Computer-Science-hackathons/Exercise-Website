package ie.tcd.pavel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("group")
public class JoinCreateGroupPage extends VerticalLayout {

	MongoDBOperations database;
	private boolean isNewGroup = false;
	
	public JoinCreateGroupPage()
	{
		database = BeanUtil.getBean(MongoDBOperations.class);

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

		confirm.addClickListener(buttonClickEvent -> {
			if(isNewGroup) {
				// User is creating a new group
				// Check if the group already exists: if(database)
				if(groupNameIn.getValue() != null && groupPassIn.getValue() != null) {
					if(!database.groupExists(groupNameIn.getValue())) {
						database.insertGroup(groupNameIn.getValue(), groupPassIn.getValue(), TemporarySessionHandler.getCurrentUser());
					}
				}
			} else {
				// User is attempting to join a group
				if(groupNameIn.getValue() != null && groupPassIn.getValue() != null) {
					if (database.groupExists(groupNameIn.getValue(), groupPassIn.getValue())) {
						database.insertGroup(groupNameIn.getValue(), groupPassIn.getValue(), TemporarySessionHandler.getCurrentUser());
					}
				}
			}
		});
		
		VerticalLayout verLay = new VerticalLayout (head1, groupNameIn, head2, groupPassIn, confirm);
		dialog.add(verLay);
		
		dialog.setWidth("400px");
		dialog.setHeight("200px");

		createGroup.addClickListener(event -> {
			head1.setText("New Group Name:");
			head2.setText("New Group Password:");
			isNewGroup = true;
		    dialog.open();
		    groupNameIn.getElement().callJsFunction("focus");
		});
		
		joinGroup.addClickListener(event -> {
			head1.setText("Group Name:");
			head2.setText("Group Password:");
			isNewGroup = false;
		    dialog.open();
		    groupNameIn.getElement().callJsFunction("focus");
		});
		
		
		

	}
}
