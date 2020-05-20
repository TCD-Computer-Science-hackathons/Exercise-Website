package ie.tcd.pavel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("group")
public class JoinCreateGroupPage extends VerticalLayout {

    public JoinCreateGroupPage() {
        Button createGroup = new Button ("Create a Group");
        Button joinGroup = new Button ("Join a Group");
        HorizontalLayout horLay = new HorizontalLayout (createGroup,joinGroup);
        add(horLay);

        Dialog dialog = new Dialog();
        Input input = new Input();
        Div content = new Div();
        content.addClassName("my-style");

        content.setText("Group Name");

        dialog.add(content);
        dialog.add(input);

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        createGroup.addClickListener(event -> {
            dialog.open();
            input.getElement().callJsFunction("focus");
        });

        joinGroup.addClickListener(event -> {
            dialog.open();
            input.getElement().callJsFunction("focus");
        });
    }
}
