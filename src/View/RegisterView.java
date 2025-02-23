package View;

import Controller.RegisterController;
import Controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label registerMessageLabel;

    private RegisterController contr;
    private RegisterView instance;


    /**
     * RegisterController constructor
     *
     * @throws IOException Exception
     * @author Fredrik Pettersson
     */
    public RegisterView() throws IOException {

    }


    /**
     * Initializes the picture on the View
     * @param url url of the picture
     * @param resourceBundle resourceBundle
     * @author Fredrik Pettersson
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        this.contr = new RegisterController(this);

        ViewController.loadIcon(this.brandingImageView);

    }


    /**
     * Closes the window
     *
     * @param event event
     * @author Fredrik Pettersson
     */
    public void cancelButton(ActionEvent event) {
        ViewController.cancelButton(cancelButton);
    }


    public String getUsernameTextField() {
        return usernameTextField.getText();
    }


    public String getPasswordTextField() {
        return passwordTextField.getText();
    }


    public String getEmailTextField() {
        return emailTextField.getText();
    }


    public void setRegisterMessageLabel(String registerMessageLabel, String color) {
        this.registerMessageLabel.setText(registerMessageLabel);
        this.registerMessageLabel.setTextFill(Color.valueOf(color));
    }

    public void setContr(RegisterController contr) {
        this.contr = contr;
    }


    public void setInstance(RegisterView instance) {
        this.instance = instance;
    }

    public void registerNewUser() throws Exception {
        this.contr.register();
    }
}