package ie.tcd.pavel;

import ie.tcd.pavel.exercisefields.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

import java.util.Hashtable;

public class AddExerciseButton extends Button {

    private Hashtable<String, CustomField<String>> exerciseFields = new Hashtable<>();
    private final VerticalLayout fakeDatabase = new VerticalLayout();
    private final ErrorField errorField = new ErrorField("Error");
    private String currentExercise = "Running";

    public AddExerciseButton(String text) {
        super(text);

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
            // TODO: Add the exercise to database
            String data = getCustomField(exercise.getValue()).getValue();
            if(data != null) {
                fakeDatabase.add(new Label(exercise.getValue() + ": " + data));
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

    public VerticalLayout getFakeDatabase() {
        return fakeDatabase;
    }
}
