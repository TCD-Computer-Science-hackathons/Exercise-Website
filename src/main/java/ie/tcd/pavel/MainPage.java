package ie.tcd.pavel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import ie.tcd.pavel.security.SecurityUtils;
import org.apache.catalina.webresources.FileResource;



@CssImport(value = "./styles/shared-styles.css")
@Route("")
public class MainPage extends AppLayout implements BeforeEnterObserver {
    public MainPage() {
        setPrimarySection(AppLayout.Section.DRAWER);
        Image background = new Image("https://i.imgur.com/2ikSvg1.jpg", "Background");
        this.setContent(background);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Tabs tabs;
        if(SecurityUtils.isUserLoggedIn())
        {
            createHeaderWithLogout();
            tabs = new Tabs(createHomeTab(),createTab("Add Exercise",AddExercisePage.class),
                    createTab("Create/Join Group",JoinCreateGroupPage.class),
                    createTab("Charts",ExerciseChartsPage.class));

        }
        else
        {
            createHeaderWithoutLogout();
             tabs = new Tabs(createHomeTab(),createTab("Register",RegisterPage.class),
                    createTab("Login",LoginPage.class));
        }

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }


    private static Tab createHomeTab()
    {
        Tab home = new Tab("Home");
        return  home;
    }

    private static Tab createTab( String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass),title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a,  String title) {
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
}
