package ie.tcd.pavel;

import com.vaadin.flow.server.VaadinSession;

public class TemporarySessionHandler {

    public static void bindUserToSession(String login) {
        VaadinSession.getCurrent().setAttribute("user", login);
    }

    public static String getCurrentUser() {
        return (String) VaadinSession.getCurrent().getAttribute("user");
    }

}
