package ie.tcd.pavel;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("blank")
public class BlankPage extends VerticalLayout {

    public BlankPage() {
        Image background = new Image("https://i.imgur.com/2ikSvg1.jpg", "Background");
        add(background);
    }
}
