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
    ArrayList<Details> groupDetails;
    ArrayList<Group> groups;

    public MyGroupsPage()
    {
        database = BeanUtil.getBean(MongoDBOperations.class);
        // Centers the components
        /**setHeightFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);**/
        addClassName("mygroups");
        setSizeFull();
        generateGroupDetails();
    }

    private void generateGroupDetails() {
        groups = (ArrayList<Group>) database.getGroupsByUser(SecurityUtils.getUsername());
        groupDetails = new ArrayList<>();
        if(!groups.isEmpty()) {
            for (Group g : groups) {
                Details group = new Details();
                if(database.checkIfAdmin(SecurityUtils.getUsername(), g.getName())) {
                    group.setSummaryText(g.getName() + " (Owned)");
                } else {
                    group.setSummaryText(g.getName());
                }
                Button leaveButton = new Button("Leave");
                leaveButton.addClickListener(buttonClickEvent -> {
                    database.removeUserFromGroup(SecurityUtils.getUsername(), g.getName());
                    // Check if no users are left then run database.deleteGroupPassword
                    if(database.getUsersByGroup(g.getName()).size() <= 0) {
                        database.deleteGroupFinal(g.getName());
                    }
                    removeAll();
                    generateGroupDetails();
                });
                ArrayList<User> users = (ArrayList<User>) database.getUsersByGroup(g.getName());
                if(!users.isEmpty()) {
                    for (User u : users) {
                        // Only show this version if the user owns the group
                        if(database.checkIfAdmin(SecurityUtils.getUsername(), g.getName())) {
                            Details userSubDetails = new Details();
                            boolean isAdmin = database.checkIfAdmin(u.getLogin(), g.getName());
                            if(isAdmin) {
                                userSubDetails.setSummaryText(u.getLogin() + " (Admin)");
                            } else {
                                userSubDetails.setSummaryText(u.getLogin());
                            }
                            if(!isAdmin) {
                                Label padding = new Label();
                                padding.getStyle().set("width", "1em");
                                Button kickButton = new Button("Kick");
                                kickButton.setDisableOnClick(true);
                                kickButton.addClickListener(event -> {
                                    database.removeUserFromGroup(u.getLogin(), g.getName());
                                });
                                userSubDetails.addContent(new HorizontalLayout(padding, kickButton));
                            }
                            Label padding = new Label();
                            padding.getStyle().set("width", "1em");
                            Label padding2 = new Label();
                            padding2.getStyle().set("width", "1em");
                            group.addContent(new HorizontalLayout(padding, userSubDetails), new HorizontalLayout(padding2, leaveButton));
                        } else {
                            Details userSubDetails = new Details();
                            boolean isAdmin = database.checkIfAdmin(u.getLogin(), g.getName());
                            if(isAdmin) {
                                userSubDetails.setSummaryText(u.getLogin() + " (Admin)");
                            } else {
                                userSubDetails.setSummaryText(u.getLogin());
                            }
                            Label padding = new Label();
                            padding.getStyle().set("width", "1em");
                            Label padding2 = new Label();
                            padding2.getStyle().set("width", "1em");
                            group.addContent(new HorizontalLayout(padding, userSubDetails), new HorizontalLayout(padding2, leaveButton));
                        }
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
