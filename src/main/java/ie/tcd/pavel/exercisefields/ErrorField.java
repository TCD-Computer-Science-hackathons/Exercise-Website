package ie.tcd.pavel.exercisefields;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Label;

public class ErrorField extends CustomField<String> {

    private String identifier = "";

    public ErrorField(String identifier) {
        this.identifier = identifier;

        add(new Label("Error: Exercise Field not found"));
    }

    @Override
    protected String generateModelValue() {
        return "Error";
    }

    @Override
    protected void setPresentationValue(String s) {

    }

    @Override
    public String getValue() {
        return "Error Field Value";
    }
}
