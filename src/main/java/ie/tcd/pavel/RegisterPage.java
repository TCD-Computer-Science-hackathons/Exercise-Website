package ie.tcd.pavel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterPage extends VerticalLayout {

    VerticalLayout VL = new VerticalLayout();
    HorizontalLayout HL = new HorizontalLayout();
    Label errorLabel = new Label();
    MongoDBOperations database;

    public RegisterPage() {

        database = BeanUtil.getBean(MongoDBOperations.class);


        TextField registerNameField = new TextField("Username");
        registerNameField.setClearButtonVisible(true);
        registerNameField.setErrorMessage("Please enter a valid email address");

        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setLabel("Password");
        registerPasswordField.setPlaceholder("Enter password");


        Button registerButton = new Button("Register");;
        registerButton.addClickListener(var -> {
            if(!database.userNameExists(registerNameField.getValue())) {
                database.insertUser(registerNameField.getValue(),registerPasswordField.getValue());
                registerButton.getUI().ifPresent(ui ->
                        ui.navigate("login"));
            }
            else {
                errorLabel.setText("Error: Email already taken");
            }
        });
        H1 logo = new H1("Fit Together");
        VL.add(logo);
        VL.add(registerNameField, registerPasswordField, registerButton, registerButton, errorLabel);
        VL.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(VL, HL);
    }

}
