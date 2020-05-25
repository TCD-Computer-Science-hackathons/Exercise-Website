package ie.tcd.pavel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import ie.tcd.pavel.exercisefields.*;
import ie.tcd.pavel.security.SecurityUtils;
import ie.tcd.pavel.utility.ExerciseAdaptor;
import ie.tcd.pavel.utility.ExerciseTypes;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;


@CssImport(value = "./styles/shared-styles.css")
@Route("")
public class MainPage extends AppLayout implements BeforeEnterObserver {

    private HashMap<Tab, Component> tabMap = new HashMap<>();
    private Hashtable<String, CustomField<String>> exerciseFields = new Hashtable<>();
    MongoDBOperations database;
    ExerciseTypes exerciseTypes;
    private String currentExercise = "Running";
    private final ErrorField errorField = new ErrorField("Error");
    String[] exercises;


    public MainPage() {

        database = BeanUtil.getBean(MongoDBOperations.class);
        exerciseTypes = BeanUtil.getBean(ExerciseTypes.class);
        exercises = exerciseTypes.getExerciseTypes();
        // Create a HashTable filled with exercise fields
        int i = 0;
        DistanceField runningField = new DistanceField(exercises[i]);
        exerciseFields.put(exercises[i++], runningField);
        DistanceField swimmingField = new DistanceField(exercises[i]);
        exerciseFields.put(exercises[i++], swimmingField);
        TimeField plankField = new TimeField(exercises[i]);
        exerciseFields.put(exercises[i++], plankField);
        WeightField benchField = new WeightField(exercises[i]);
        exerciseFields.put(exercises[i++], benchField);
        RepField pushUpField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], pushUpField);
        RepField lungeField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], lungeField);
        WeightField weightedSquatField = new WeightField(exercises[i]);
        exerciseFields.put(exercises[i++], weightedSquatField);
        RepField squatField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], squatField);
        WeightField overheadDumbbellField = new WeightField(exercises[i]);
        exerciseFields.put(exercises[i++], overheadDumbbellField);
        WeightField dumbbellRowField = new WeightField(exercises[i]);
        exerciseFields.put(exercises[i++], dumbbellRowField);
        WeightField deadliftField = new WeightField(exercises[i]);
        exerciseFields.put(exercises[i++], deadliftField);
        RepField burpeesField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], burpeesField);
        RepField situpField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], situpField);
        RepField skippingField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], skippingField);
        DistanceField cyclingField = new DistanceField(exercises[i]);
        exerciseFields.put(exercises[i++], cyclingField);
        RepField pullupField = new RepField(exercises[i]);
        exerciseFields.put(exercises[i++], pullupField);
        setPrimarySection(AppLayout.Section.DRAWER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Tabs tabs;
        setContent(new TextPage());
        if(SecurityUtils.isUserLoggedIn())
        {
            createHeaderWithLogout();
            tabs = new Tabs(
                    createHomeTab(),
                    createAddExerciseTab(),
                    createJoinGroupTab(),
                    createCreateGroupTab(),
                    createChartsTab(),
                    createMyGroupsTab()
            );
            tabs.addSelectedChangeListener(event -> {
                final Tab selectedTab = event.getSelectedTab();
                if(tabMap.containsKey(selectedTab)) {
                    if(!selectedTab.getLabel().equals("Join Group")&&!selectedTab.getLabel().equals("Create Group")&&
                    !selectedTab.getLabel().equals("Add Exercise")) {
                        setContent(tabMap.get(selectedTab));
                    }
                    else
                    {
                        ((Dialog) tabMap.get(selectedTab)).open();
                        tabs.setSelectedIndex(0);
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

    private Tab createAddExerciseTab()
    {
        Tab addExerciseTab = new Tab("Add Exercise");
        addExerciseTab .addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabMap.put(addExerciseTab,createAddExerciseDialog());
        return  addExerciseTab;
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
        tabMap.put(home, new TextPage());
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
        PasswordField joinGroupPassField = new PasswordField();
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
                    UI.getCurrent().getPage().reload();
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
        PasswordField newGroupPassField = new PasswordField();
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
                    database.makeAdmin(SecurityUtils.getUsername(), newGroupNameField.getValue());
                    dialog.close();
                    UI.getCurrent().getPage().reload();
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

    public Dialog createAddExerciseDialog()
    {
        Dialog dialog = new Dialog();

        // Vertical layout for within dialog
        VerticalLayout layout = new VerticalLayout();

        // Buttons to finalise adding of exercise
        Button confirm = new Button("Confirm");
        Button cancel = new Button("Cancel");
        HorizontalLayout cancelOrConfirm = new HorizontalLayout();
        cancelOrConfirm.add(cancel);
        cancelOrConfirm.add(confirm);

        // Dropdown menu of exercises
        Select<String> exercise = new Select<>();
        exercise.setItems(exercises);
        exercise.getStyle().set("width", "15em");
        exercise.setValue("Running");
        exercise.addValueChangeListener(e -> {
            layout.remove(getCustomField(currentExercise));
            layout.remove(cancelOrConfirm);
            currentExercise = exercise.getValue();
            layout.add(getCustomField(exercise.getValue()));
            layout.add(cancelOrConfirm);
        });

        // Click listeners for cancel and confirm
        confirm.addClickListener(e -> {
            String data = exerciseFields.get(exercise.getValue()).getValue();
            if(data != null) {
                if(exerciseFields.get(exercise.getValue()) instanceof DistanceField) {
                    DistanceField distanceField = (DistanceField) getCustomField(exercise.getValue());
                    database.insertExercise(SecurityUtils.getUsername(),
                            exercise.getValue(),
                            ExerciseAdaptor.getDistanceFieldInfo(distanceField.getDistance(), distanceField.getUnit()),
                            new Date().getTime());
                    UI.getCurrent().getPage().reload();
                    System.out.printf("%s: Added %s%s at %s to %s%n", exercise.getValue(), distanceField.getDistance(),
                            distanceField.getUnit(), new Date().getTime(), SecurityUtils.getUsername());

                } else if(exerciseFields.get(exercise.getValue()) instanceof RepField) {
                    RepField repField = (RepField) getCustomField(exercise.getValue());
                    database.insertExercise(SecurityUtils.getUsername(),
                            exercise.getValue(),
                            ExerciseAdaptor.getRepFieldInfo(repField.getReps()),
                            new Date().getTime());
                    UI.getCurrent().getPage().reload();
                    System.out.printf("%s: Added %s reps at %s to %s%n", exercise.getValue(), repField.getReps(),
                            new Date().getTime(), SecurityUtils.getUsername());

                } else if(exerciseFields.get(exercise.getValue()) instanceof TimeField) {
                    TimeField timeField = (TimeField) getCustomField(exercise.getValue());
                    database.insertExercise(SecurityUtils.getUsername(),
                            exercise.getValue(),
                            ExerciseAdaptor.getTimeFieldInfo(timeField.getTime(), timeField.getUnit()),
                            new Date().getTime());
                    UI.getCurrent().getPage().reload();
                    System.out.printf("%s: Added %s%s at %s to %s%n", exercise.getValue(), timeField.getTime(),
                            timeField.getUnit(), new Date().getTime(), SecurityUtils.getUsername());

                } else if(exerciseFields.get(exercise.getValue()) instanceof WeightField) {
                    WeightField weightField = (WeightField) getCustomField(exercise.getValue());
                    database.insertExercise(SecurityUtils.getUsername(),
                            exercise.getValue(),
                            ExerciseAdaptor.getWeightFieldInfo(weightField.getWeight(), weightField.getUnit(),
                                    weightField.getReps()),
                            new Date().getTime());
                    UI.getCurrent().getPage().reload();
                    System.out.printf("%s: Added %s%s for %s reps at %s to %s%n", exercise.getValue(),
                            weightField.getWeight(), weightField.getUnit(), weightField.getReps(),
                            new Date().getTime(), SecurityUtils.getUsername());
                }
                dialog.close();
            }
        });
        cancel.addClickListener(e -> {
            dialog.close();
        });

        // Add everything to the dialog
        layout.add(exercise);
        layout.add(getCustomField(exercise.getValue()));
        layout.add(cancelOrConfirm);
        dialog.add(layout);
        return  dialog;
    }

    private CustomField<String> getCustomField(String exerciseField) {
        if(exerciseFields.containsKey(exerciseField)) {
            return exerciseFields.get(exerciseField);
        }
        return errorField;
    }
}