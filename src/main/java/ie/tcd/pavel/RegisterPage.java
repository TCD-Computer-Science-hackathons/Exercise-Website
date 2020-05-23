package ie.tcd.pavel;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterPage extends VerticalLayout {

    VerticalLayout VL = new VerticalLayout();
    HorizontalLayout HL = new HorizontalLayout();
    Label errorLabel = new Label();
    MongoDBOperations database;

    public RegisterPage() {

        database = BeanUtil.getBean(MongoDBOperations.class);


        EmailField registerEmailField = new EmailField("Email");
        registerEmailField.setClearButtonVisible(true);
        registerEmailField.setErrorMessage("Please enter a valid email address");

        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setLabel("Password");
        registerPasswordField.setPlaceholder("Enter password");


        Button registerButton = new Button("Login");;
        registerButton.addClickListener(var -> {
            if(!database.userEmailExists(registerEmailField.getValue())) {
                database.insertUser(registerEmailField.getValue(),registerPasswordField.getValue());
                registerButton.getUI().ifPresent(ui ->
                        ui.navigate("group"));
            }
            else {
                errorLabel.setText("Error: LOGIN NOT AVAILABLE");
            }
        });

        //displaying components on web page
        VL.add(registerEmailField, registerPasswordField, registerButton, registerButton, errorLabel);
        VL.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(VL, HL);
    }

}
