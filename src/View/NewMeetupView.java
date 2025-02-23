package View;

import Controller.NewMeetUpController;
import Controller.Session;
import Controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class NewMeetupView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<String> timeListView;
    @FXML
    private ListView<String> courseList;
    @FXML
    private TextField notesTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label meetupMessage;


    private Session session;
    private NewMeetUpController controller;
    private NewMeetupView instance;

    public NewMeetupView() {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ViewController.loadIcon(this.brandingImageView);

            instance = this;
            this.controller.setViewInstance(getViewInstance());
            timeListView.getItems().addAll(this.controller.createList());
            courseList.setItems(this.session.getAvailableCourses());
        });
    }


    /**
     * Closes the window
     *
     * @author Fredrik Pettersson
     */
    public void cancelButton() {
        ViewController.cancelButton(this.cancelButton);
    }

    public String getNameTextField() {
        return nameTextField.getText();
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public String getSelectedTime() {
        return timeListView.getSelectionModel().getSelectedItem();
    }

    public NewMeetupView getViewInstance() {
        return this.instance;
    }

    public String getSelectedCourse() {
        return courseList.getSelectionModel().getSelectedItem();
    }

    public String getNotesTextField() {
        return notesTextField.getText();
    }

    public LocalDate getDatePicker() {
        return datePicker.getValue();
    }


    public void setMeetupMessage(String meetupMessage, String color) {
        this.meetupMessage.setText(meetupMessage);
        this.meetupMessage.setTextFill(Color.valueOf(color));
    }

    public void setController(NewMeetUpController newMeetUpController) {
        this.controller = newMeetUpController;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void createMeetup() throws Exception {
        controller.createMeetup();
    }
}

