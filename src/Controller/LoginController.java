package Controller;

import Model.UserCredentialHandler;
import Model.UserCredentials;
import View.StartView;

import java.io.IOException;


public class LoginController {


    private final UserCredentialHandler accountHandler;
    private final StartView view;
    private Session session;


    public LoginController(StartView contr) throws IOException {
        this.view = contr;
        this.accountHandler = new UserCredentialHandler();


    }

    public String getEnteredUsername() throws Exception {
        String text = "";
        if (this.view != null) {
            text = this.view.getUsernameTextField().getText().toLowerCase();
            return text;
        } else {
            throw new Exception("StartUpView hasn't been instantiated properly");
        }
    }

    public String getEnteredPassword() throws Exception {
        String text = "";
        if (this.view != null) {
            text = this.view.getPasswordField().getText().toLowerCase();
            return text;
        } else {
            throw new Exception("Couldn't retrieve text fields. StartUpView hasn't been instantiated properly.");
        }
    }


    public void loginButtonOnAction() throws Exception {

        String username = getEnteredUsername();
        String password = getEnteredPassword();

        if (!this.accountHandler.usernameExists(username)) {
            this.view.updateLoginMessageLabel("The username doesn't exist", "RED");
            return;
        }


        if (!this.accountHandler.usernameMatchesPassword(username, password)) {
            this.view.updateLoginMessageLabel("Incorrect password, try again!", "RED");
            return;
        } else {
            this.view.updateLoginMessageLabel("Signing in...", "GREEN");
            UserCredentials student = this.accountHandler.getUserCredentialsFromDB(username);
            Session newSession = new Session(student);
            MyHomePageController con = new MyHomePageController(newSession);
            con.setInstance(con);
            con.loadUserHomePage();
        }


    }


}