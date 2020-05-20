package ie.tcd.pavel.exercisefields;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class RepField extends CustomField<String> {

    private final TextField reps = new TextField();

    private String identifier = "";

    public RepField(String identifier) {
        this.identifier = identifier;

        reps.setPattern("[0-9]*");
        reps.setPreventInvalidInput(true);
        reps.setMaxLength(4);
        reps.setPlaceholder("Reps");
        reps.getStyle().set("width", "5em");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(reps);
        add(horizontalLayout);
    }

    public String getReps() {
        return reps.getValue();
    }

    @Override
    protected String generateModelValue() {
        return " x" + reps.getValue().replaceFirst("^0+(?!$)", "");
    }

    @Override
    protected void setPresentationValue(String s) {
        if (s == null) {
            reps.setValue(null);
        }
    }
}
