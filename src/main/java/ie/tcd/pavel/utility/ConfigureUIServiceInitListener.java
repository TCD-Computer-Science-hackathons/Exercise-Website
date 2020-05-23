package ie.tcd.pavel.utility;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import ie.tcd.pavel.LoginPage;
import ie.tcd.pavel.TemporarySessionHandler;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginPage.class.equals(event.getNavigationTarget())
                && TemporarySessionHandler.getCurrentUser() == null) {
            event.rerouteTo(LoginPage.class);
        }
    }
}
