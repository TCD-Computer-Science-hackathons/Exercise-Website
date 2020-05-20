package ie.tcd.pavel.exercisefields;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

public class DistanceField extends CustomField<String> {

    private final TextField distance = new TextField();
    private final Select<String> unit = new Select<>();

    private String identifier = "";

    public DistanceField(String identifier) {
        this.identifier = identifier;
        distance.setPattern("[0-9]*");
        distance.setPreventInvalidInput(true);
        distance.setMaxLength(8);
        distance.setPlaceholder("Distance");
        distance.getStyle().set("width", "9em");
        unit.setItems("km", "mi", "m", "ft");
        unit.getStyle().set("width", "5em");
        unit.setValue("km");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(distance, unit);
        add(horizontalLayout);
    }

    public String getDistance() {
        return distance.getValue();
    }

    public String getUnit() {
        return unit.getValue();
    }

    @Override
    protected String generateModelValue() {
        return distance.getValue().replaceFirst("^0+(?!$)", "") + unit.getValue();
    }

    @Override
    protected void setPresentationValue(String s) {
        if (s == null) {
            distance.setValue(null);
            unit.setValue(null);
        }
    }
}
