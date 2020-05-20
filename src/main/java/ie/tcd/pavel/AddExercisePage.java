package ie.tcd.pavel;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("exercise")
public class AddExercisePage extends VerticalLayout {

    @Autowired
    MongoDBOperations database;

    public AddExercisePage() {
        AddExerciseButton addExerciseButton = new AddExerciseButton("Add Exercise");
        add(addExerciseButton);
    }
}
