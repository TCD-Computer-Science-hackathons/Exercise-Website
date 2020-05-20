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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@PWA(name = "Exercise Website",
        shortName = "Exercise Website",
        description = "",
        enableInstallPrompt = false)
public class LoginPage extends VerticalLayout {

    VerticalLayout VL = new VerticalLayout();
    VerticalLayout VLRegister = new VerticalLayout();
    HorizontalLayout HL = new HorizontalLayout();
    HorizontalLayout HLRegister = new HorizontalLayout();
    @Autowired
    MongoDBOperations database;

    public LoginPage() {
        //creating input field components
        EmailField registerEmailField = new EmailField("Email");
        registerEmailField.setClearButtonVisible(true);
        registerEmailField.setErrorMessage("Please enter a valid email address");

        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setLabel("Password");
        registerPasswordField.setPlaceholder("Enter password");

        EmailField loginEmailField = new EmailField("Email");
        loginEmailField.setClearButtonVisible(true);
        loginEmailField.setErrorMessage("Please enter a valid email address");

        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setLabel("Password");
        loginPasswordField.setPlaceholder("Enter password");

        //creating Register form Dialogue box to show when a new user is registering
        Button confirmRegistrationButton = new Button("Confirm Registration");
        confirmRegistrationButton.addClickListener(var -> {

        });
        FormLayout registerFormLayout = new FormLayout();

        //registerFormLayout.addFormItem(registerEmailField, "");
        //registerFormLayout.addFormItem(registerPasswordField, "");
        HLRegister.add(registerEmailField, registerPasswordField);
        HLRegister.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        VLRegister.add(confirmRegistrationButton);
        VLRegister.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        //Dialog registerForm = new Dialog(registerEmailField, registerPasswordField);
        //Dialog registerForm = new Dialog(registerFormLayout, confirmRegistrationButton);
        Dialog registerForm = new Dialog(HLRegister, VLRegister);
        registerForm.setCloseOnEsc(true);
        registerForm.setCloseOnOutsideClick(true);
        registerForm.setSizeFull();

        //creating and handling buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.addClickListener(var -> {
            loginButton.getUI().ifPresent(ui ->
                    ui.navigate("exercise"));
        });
        registerButton.addClickListener(var -> {
            registerForm.open();
        });


        //displaying components on web page
        VL.add(loginEmailField, loginPasswordField, loginButton, registerButton);
        VL.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        add(VL, HL);
    }

}
