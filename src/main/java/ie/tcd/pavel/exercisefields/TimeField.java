package ie.tcd.pavel.exercisefields;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

public class TimeField extends CustomField<String> {

    private final TextField time = new TextField();
    private final Select<String> unit = new Select<>();

    private String identifier = "";

    public TimeField(String identifier) {
        this.identifier = identifier;
        time.setPattern("[0-9]*");
        time.setPreventInvalidInput(true);
        time.setMaxLength(8);
        time.setPlaceholder("Time");
        time.getStyle().set("width", "9em");
        unit.setItems("hr", "mins");
        unit.getStyle().set("width", "6em");
        unit.setValue("mins");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(time, unit);
        add(horizontalLayout);
    }

    public String getTime() {
        return time.getValue();
    }

    public String getUnit() {
        return unit.getValue();
    }

    @Override
    protected String generateModelValue() {
        return time.getValue().replaceFirst("^0+(?!$)", "") + unit.getValue();
    }

    @Override
    protected void setPresentationValue(String s) {
        if (s == null) {
            time.setValue(null);
            unit.setValue(null);
        }
    }
}
