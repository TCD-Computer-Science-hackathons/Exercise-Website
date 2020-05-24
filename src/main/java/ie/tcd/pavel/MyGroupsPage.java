package ie.tcd.pavel;

import com.mongodb.Mongo;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import ie.tcd.pavel.documents.Group;
import ie.tcd.pavel.documents.User;
import ie.tcd.pavel.security.SecurityUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyGroupsPage extends VerticalLayout {

    MongoDBOperations database;

    public MyGroupsPage()
    {
        database = BeanUtil.getBean(MongoDBOperations.class);
        // Centers the components
        /**setHeightFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);**/

        ArrayList<Group> groups = (ArrayList<Group>) database.getGroupsByUser(SecurityUtils.getUsername());
        ArrayList<Details> groupDetails = new ArrayList<>();

        if(!groups.isEmpty()) {
            for (Group g : groups) {
                Details group = new Details();
                group.setSummaryText(g.getName());
                Button leaveButton = new Button("Leave");
                ArrayList<User> users = (ArrayList<User>) database.getUsersByGroup(g.getName());
                if(!users.isEmpty()) {
                    for (User u : users) {
                        // Only show this version if the user owns the group
                        Details userSubDetails = new Details();
                        Button button = new Button("Kick");
                        userSubDetails.setSummaryText(u.getLogin());
                        userSubDetails.addContent(button);
                        Label padding = new Label();
                        padding.getStyle().set("width", "1em");
                        group.addContent(new HorizontalLayout(padding, userSubDetails), leaveButton);
                    }
                }
                groupDetails.add(group);
            }
            for (Details detail : groupDetails) {
                add(detail);
            }
        } else {
            add(new H3("You are not a member of any groups!"));
        }

    }
}
