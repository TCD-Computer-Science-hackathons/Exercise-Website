package ie.tcd.pavel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import ie.tcd.pavel.security.SecurityUtils;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;


@CssImport(value = "./styles/shared-styles.css")
@Route("")
public class MainPage extends AppLayout implements BeforeEnterObserver {

    private HashMap<Tab, Component> tabMap = new HashMap<>();
    private Image background = new Image("https://i.imgur.com/4AhkxVz.jpg", "Background");
    MongoDBOperations database;
    public MainPage() {
        database = BeanUtil.getBean(MongoDBOperations.class);
        this.setContent(background);
        setPrimarySection(AppLayout.Section.DRAWER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Tabs tabs;
        if(SecurityUtils.isUserLoggedIn())
        {
            createHeaderWithLogout();
            tabs = new Tabs(
                    createHomeTab(),
                    createTab("Add Exercise", AddExercisePage.class),
                    createJoinGroupTab(),
                    createCreateGroupTab(),
                    createChartsTab(),
                    createMyGroupsTab()
            );
            tabs.addSelectedChangeListener(event -> {
                final Tab selectedTab = event.getSelectedTab();
                if(tabMap.containsKey(selectedTab)) {
                    if(!selectedTab.getLabel().equals("Join Group")&&!selectedTab.getLabel().equals("Create Group"))
                        setContent(tabMap.get(selectedTab));
                    else
                    {
                        ((Dialog) tabMap.get(selectedTab)).open();
                    }
                }
            });

        }
        else
        {
            createHeaderWithoutLogout();
            tabs = new Tabs(
                    createHomeTab(),
                    createTab("Register",RegisterPage.class),
                    createTab("Login",LoginPage.class)
            );
            tabs.addSelectedChangeListener(event -> {
                final Tab selectedTab = event.getSelectedTab();
                if(tabMap.containsKey(selectedTab)) {
                    setContent(tabMap.get(selectedTab));
                }
            });
        }

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }

    private Tab createJoinGroupTab()
    {
        Tab joinGroup = new Tab("Join Group");
        joinGroup.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabMap.put(joinGroup,createJoinGroupDialog());
        return  joinGroup;
    }

    private Tab createCreateGroupTab()
    {
        Tab createGroup = new Tab("Create Group");
        createGroup.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabMap.put(createGroup,createCreateGroupDialog());
        return  createGroup;
    }


    private Tab createHomeTab()
    {
        Tab home = new Tab("Home");
        tabMap.put(home, background);
        return  home;
    }

    private Tab createMyGroupsTab()
    {
        Tab myGroups = new Tab("My Groups");
        myGroups.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabMap.put(myGroups, new MyGroupsPage());
        return myGroups;
    }

    private Tab createChartsTab()
    {
        Tab myCharts= new Tab("My Charts");
        myCharts.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabMap.put(myCharts, new ExerciseChartsPage());
        return myCharts;
    }

    private Tab createTab( String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass),title));
    }

    private Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private <T extends HasComponents> T populateLink(T a,  String title) {
        a.add(title);
        return a;
    }

    private void createHeaderWithLogout() {
        H1 logo = new H1("Fit Together");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("95%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createHeaderWithoutLogout() {
        H1 logo = new H1("Fit Together");
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private Dialog createJoinGroupDialog()
    {
        Dialog dialog = new Dialog();
        TextField joinGroupNameField = new TextField();
        TextField joinGroupPassField = new TextField();
        joinGroupNameField.getStyle().set("width", "15em");
        joinGroupNameField.setMaxLength(13);
        joinGroupNameField.setLabel("Group Name: ");
        joinGroupPassField.getStyle().set("width", "15em");
        joinGroupPassField.setMaxLength(13);
        joinGroupPassField.setLabel("Group Password: ");
        Label joinErrorLabel = new Label();
        Button confirmJoinGroup = new Button("Confirm");
        VerticalLayout vL = new VerticalLayout (joinGroupNameField, joinGroupPassField, confirmJoinGroup, joinErrorLabel);

        confirmJoinGroup.addClickListener( event -> {
            // User is attempting to join a group
            if(!joinGroupNameField.getValue().equals("") && !joinGroupPassField.getValue().equals("")) {
                if (database.groupExists(joinGroupNameField.getValue(), joinGroupPassField.getValue())) {
                    database.insertGroup(joinGroupNameField.getValue(), joinGroupPassField.getValue(),
                            SecurityUtils.getUsername());
                    dialog.close();
                    System.out.printf("[DEBUG] Group Joined: Name - %s | Password - %s%n", joinGroupNameField.getValue(), joinGroupPassField.getValue());
                } else {
                    System.out.printf("[DEBUG] Someone tried to join a group that does not exist. Password Incorrect.%n");
                }
            }
            dialog.close();
        });

        dialog.add(vL);

        return dialog;
    }

    private Dialog createCreateGroupDialog()
    {
        Dialog dialog = new Dialog();
        TextField newGroupNameField = new TextField();
        TextField newGroupPassField = new TextField();
        newGroupNameField.getStyle().set("width", "15em");
        newGroupNameField.setMaxLength(13);
        newGroupNameField.setLabel("New Group Name: ");
        newGroupPassField.getStyle().set("width", "15em");
        newGroupPassField.setMaxLength(13);
        newGroupPassField.setLabel("New Group Password: ");
        Label createErrorLabel = new Label();
        Button confirmCreateGroup = new Button ("Confirm");
        VerticalLayout vL = new VerticalLayout (newGroupNameField, newGroupPassField, confirmCreateGroup, createErrorLabel);

        confirmCreateGroup.addClickListener( event -> {
            // User is creating a new group
            if(!newGroupNameField.getValue().equals("") && !newGroupPassField.getValue().equals("")) {
                if(!database.groupExists(newGroupNameField.getValue())) {
                    database.insertGroup(newGroupNameField.getValue(), newGroupPassField.getValue(),
                            SecurityUtils.getUsername());
                    dialog.close();
                    System.out.printf("[DEBUG] Group Created: Name - %s | Password - %s%n", newGroupNameField.getValue(), newGroupPassField.getValue());
                } else {
                    vL.remove(createErrorLabel);
                    createErrorLabel.setText("Group Name already exists");
                    vL.add(createErrorLabel);
                    System.out.printf("[DEBUG] Someone tried to create a group when the name already exists%n");
                }
            }
            dialog.close();
        });

        dialog.add(vL);

        return dialog;
    }
}