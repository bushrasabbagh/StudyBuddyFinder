package View;

import Controller.LoginController;
import Controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the application's login controller. All login related calls to the model pass through this class.
 *
 * @author Maya
 */

public class StartView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Label registerLabel;


    private LoginController contr;
    private RegisterView regIns;
    private StartView instance;

    public StartView() throws IOException {

    }

    /**
     * Called to initialize this controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        try {
            this.contr = new LoginController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewController.loadIcon(this.brandingImageView);
        registerLabel.setText("Not part of the Studybuddyfinder\ncommunity yet? Register now");
    }


    public void setController(LoginController controller) {
        this.contr = controller;

    }

    public Label getLoginMessageLabel() {
        return loginMessageLabel;
    }

    public void updateLoginMessageLabel(String labeltext, String textcolor) {
        this.loginMessageLabel.setText(labeltext);
        Color color = Color.valueOf(textcolor);
        this.loginMessageLabel.setTextFill(color);
    }


    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Label getRegisterLabel() {
        return registerLabel;
    }

    public void setRegisterLabel(String labeltext) {

        this.registerLabel.setText(labeltext);
    }


    /**
     * Logs the user in on click.
     *
     * @throws IOException exception thrown if the program fails to log the user in.
     */
    public void loginButtonOnAction() throws Exception {
        contr.loginButtonOnAction();
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

    /**
     * Registers the users on click.
     */
    public void registerButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/RegisterView.fxml"));
            fxmlLoader.load();
            Parent root = fxmlLoader.getRoot();
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 600, 600));
            registerStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        }



}