package ie.tcd.pavel;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.awt.*;


@CssImport(value = "./styles/shared-styles.css")
@Route("")
public class MainPage extends AppLayout {
    public MainPage() {
        setPrimarySection(AppLayout.Section.DRAWER);
        createHeader();
        Tabs tabs = new Tabs(new Tab("Home"),new Tab("Add Exercise"), new Tab("Create/Join group"),
                new Tab("Charts"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);

    }

    private void createHeader() {
        H1 logo = new H1("Fit Together");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }
}
