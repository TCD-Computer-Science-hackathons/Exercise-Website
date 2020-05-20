package ie.tcd.pavel.exercisefields;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

public class WeightField extends CustomField<String> {

    private final TextField weight = new TextField();
    private final Select<String> unit = new Select<>();
    private final TextField reps = new TextField();

    private String identifier = "";

    public WeightField(String identifier) {
        this.identifier = identifier;

        weight.setPattern("[0-9]*");
        weight.setPreventInvalidInput(true);
        weight.setMaxLength(8);
        weight.setPlaceholder("Amount");
        weight.getStyle().set("width", "9em");

        unit.setItems("kg", "lb");
        unit.getStyle().set("width", "5em");
        unit.setValue("kg");

        reps.setPattern("[0-9]*");
        reps.setPreventInvalidInput(true);
        reps.setMaxLength(4);
        reps.setPlaceholder("Reps");
        reps.getStyle().set("width", "5em");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(weight, unit, reps);
        add(horizontalLayout);
    }

    public String getWeight() {
        return weight.getValue();
    }

    public String getUnit() {
        return unit.getValue();
    }

    public String getReps() {
        return reps.getValue();
    }

    @Override
    protected String generateModelValue() {
        return weight.getValue().replaceFirst("^0+(?!$)", "") + unit.getValue() + " x" + reps.getValue().replaceFirst("^0+(?!$)", "");
    }

    @Override
    protected void setPresentationValue(String s) {
        if (s == null) {
            weight.setValue(null);
            unit.setValue(null);
            reps.setValue(null);
        }
    }
}
