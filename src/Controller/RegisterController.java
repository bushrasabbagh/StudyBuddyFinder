package Controller;

import Model.UserCredentialHandler;
import View.RegisterView;

import java.io.IOException;

public class RegisterController {


    private final RegisterView view;


    public RegisterController(RegisterView view) {
        this.view = view;


    }


    public void register() throws Exception {

        UserCredentialHandler handler = new UserCredentialHandler();
        String username = this.view.getUsernameTextField();
        String password = this.view.getPasswordTextField();
        String email = this.view.getEmailTextField();


        if (handler.usernameExists(username)) {
            this.view.setRegisterMessageLabel("Username is already in use", "RED");
            return;
        }


        if (!UserCredentialHandler.validatePasswordFormat(password)) {
            this.view.setRegisterMessageLabel("The password has to be at least 3 characters", "RED");
            return;
        }
        if (!UserCredentialHandler.validateEmailFormat(email)) {
            this.view.setRegisterMessageLabel("Invalid email format", "RED");
            return;
        }

        handler.addUserCredentialsToDB(username, password, email);
        this.view.setRegisterMessageLabel("User created, you can now log in!", "GREEN");

    }
}

