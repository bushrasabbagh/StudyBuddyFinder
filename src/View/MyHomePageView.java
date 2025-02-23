package View;

import Controller.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MyHomePageView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button yourCoursesButton;
    @FXML
    private Label userLabel;

    private Session currentSession;
    private MyHomePageController contr;
    private MyHomePageView instance;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ViewController.loadIcon(this.brandingImageView);
            instance = this;


        });
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

    public void setSession(Session session) {
        this.currentSession = session;
    }

    public void setController(MyHomePageController controller) {
        this.contr = controller;
    }

    public void setViewInstance(MyHomePageView current) {
        this.instance = current;
    }


    public void loadMeetUpCalendarView() {
        MeetUpCalendarController controller = new MeetUpCalendarController(getSession());
        controller.setInstance(controller);
        controller.loadMeetUpCalendarView();

    }


    public void loadMyCoursesView() {
        MyCoursesController controller = new MyCoursesController(getSession());
        controller.setInstance(controller);
        controller.loadMyCoursesView();

    }


    public void loadNewMeetUpView() {
        NewMeetUpController controller = new NewMeetUpController(getSession());
        controller.setInstance(controller);
        controller.loadNewMeetUpView();

    }

    public void loadMyMeetUpsView() {
        MyMeetUpsController newContr = new MyMeetUpsController(getSession());
        newContr.setInstance(newContr);
        newContr.loadMyMeetUpsView();
    }

    public Session getSession() {
        return this.currentSession;
    }
}