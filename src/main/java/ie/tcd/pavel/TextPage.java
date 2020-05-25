package ie.tcd.pavel;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TextPage extends VerticalLayout{
    public TextPage()
    {

        this.setAlignItems(Alignment.CENTER);
        addClassName("text-main");
        H1 title = new H1("Stopping exercise can increase risk of depression");
        H2 subtitle = new H2("According to a study conducted by researchers at the " +
                "University of Adelaide, ceasing all forms of vigorous " +
                "physical activity can cause an individual to develop " +
                "depressive symptoms.\n");
        H3 text = new H3("Studies show that exercise can treat mild to moderate depression " +
                "as effectively as antidepressant medication. As one example, " +
                "a recent study done by the Harvard T.H. Chan School of Public " +
                "Health found that running for 15 minutes a day or walking for an " +
                "hour reduces the risk of major depression by 26%. In addition to " +
                "relieving depression symptoms, research also shows that " +
                "maintaining an exercise schedule can prevent you from relapsing.");
        VerticalLayout vL = new VerticalLayout();
        vL.setWidthFull();
        vL.setAlignItems(Alignment.CENTER);
        vL.add(title,subtitle,text);
        add(vL);
        VerticalLayout vLImage = new VerticalLayout();
        vLImage.setWidthFull();
        vLImage.setAlignItems(Alignment.CENTER);
        Image image = new Image("https://i.imgur.com/R8mRFvh.jpg", "WHO Recommendations");
        image.setWidthFull();
        vLImage.add(image);
        add(vLImage);


    }
}
