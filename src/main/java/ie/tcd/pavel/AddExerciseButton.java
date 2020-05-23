package ie.tcd.pavel;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.server.VaadinSession;
import ie.tcd.pavel.exercisefields.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import ie.tcd.pavel.utility.ExerciseAdaptor;
import ie.tcd.pavel.utility.ExerciseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;

import java.sql.Time;
import java.util.Date;
import java.util.Hashtable;

public class AddExerciseButton extends Button {

    private Hashtable<String, CustomField<String>> exerciseFields = new Hashtable<>();
    private final ErrorField errorField = new ErrorField("Error");
    private String currentExercise = "Running";
    @Autowired ExerciseTypes exerciseTypes;
    @Autowired MongoDBOperations database;

    public AddExerciseButton(String text) {
        super(text);
        // TODO: Caused a null pointer exception
        //String[] exercises = exerciseTypes.getExerciseTypes();

        String[] exercises = new String[] {"Running", "Swimming", "Plank", "Bench Press", "Push Ups", "Lunges", "Weighted Squats", "Squats", "Overhead Dumbbell Press",
                "Dumbbell Rows", "Deadlift", "Burpees", "Sit Ups", "Skipping", "Cycling", "Pull Ups"};

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

        // Simple button to load add exercise dialog
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
                    System.out.printf("Param 1: %s Param 2: %s Param 3: %s Param 4: %s%n", TemporarySessionHandler.checkCurrentUser(), exercise.getValue(), ExerciseAdaptor.getDistanceFieldInfo(distanceField.getDistance(), distanceField.getUnit()), new Date().getTime());
                    database.insertExercise(TemporarySessionHandler.checkCurrentUser(),
                        exercise.getValue(),
                        ExerciseAdaptor.getDistanceFieldInfo(distanceField.getDistance(), distanceField.getUnit()),
                        new Date().getTime());
                    System.out.printf("%s: Added %s%s at %s to %s%n", exercise.getValue(), distanceField.getDistance(), distanceField.getUnit(), new Date().getTime(), TemporarySessionHandler.checkCurrentUser());

                } else if(exerciseFields.get(exercise.getValue()) instanceof RepField) {
                    RepField repField = (RepField) getCustomField(exercise.getValue());
                    database.insertExercise(TemporarySessionHandler.checkCurrentUser(),
                        exercise.getValue(),
                        ExerciseAdaptor.getRepFieldInfo(repField.getReps()),
                        new Date().getTime());
                    System.out.printf("%s: Added %s reps at %s to %s%n", exercise.getValue(), repField.getReps(), new Date().getTime(), TemporarySessionHandler.checkCurrentUser());

                } else if(exerciseFields.get(exercise.getValue()) instanceof TimeField) {
                    TimeField timeField = (TimeField) getCustomField(exercise.getValue());
                    database.insertExercise(TemporarySessionHandler.checkCurrentUser(),
                            exercise.getValue(),
                            ExerciseAdaptor.getTimeFieldInfo(timeField.getTime(), timeField.getUnit()),
                            new Date().getTime());
                    System.out.printf("%s: Added %s%s at %s to %s%n", exercise.getValue(), timeField.getTime(), timeField.getUnit(), new Date().getTime(), TemporarySessionHandler.checkCurrentUser());

                } else if(exerciseFields.get(exercise.getValue()) instanceof WeightField) {
                    WeightField weightField = (WeightField) getCustomField(exercise.getValue());
                    database.insertExercise(TemporarySessionHandler.checkCurrentUser(),
                            exercise.getValue(),
                            ExerciseAdaptor.getWeightFieldInfo(weightField.getWeight(), weightField.getUnit(), weightField.getReps()),
                            new Date().getTime());
                    System.out.printf("%s: Added %s%s for %s reps at %s to %s%n", exercise.getValue(), weightField.getWeight(), weightField.getUnit(), weightField.getReps(), new Date().getTime(), TemporarySessionHandler.checkCurrentUser());
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

        this.addClickListener(e -> {
            dialog.open();
        });
    }

    private CustomField<String> getCustomField(String exerciseField) {
        if(exerciseFields.containsKey(exerciseField)) {
            return exerciseFields.get(exerciseField);
        }
        return errorField;
    }
}
