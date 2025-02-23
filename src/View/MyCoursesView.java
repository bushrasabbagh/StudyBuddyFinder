package View;

import Controller.MyCoursesController;
import Controller.Session;
import Controller.ViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyCoursesView implements Initializable {

    @FXML
    private Button deleteCourse;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ListView<String> courseAvailableList;
    @FXML
    private ListView<String> enrolledCourses;
    @FXML
    private Button addCoursesButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label message;

    private Session session;
    private MyCoursesController controller;


    public MyCoursesView() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ViewController.loadIcon(this.brandingImageView);
            this.controller.setViewInstance(this);
            initialiseAvailableCourseList();
            initializeEnrolledCoursesList();


        });
    }


    public void setSession(Session session) {
        this.session = session;
    }

    public void updateMessage(String text, String color) {
        this.message.setText(text);
        this.message.setTextFill(Color.valueOf(color));
    }

    public void initializeEnrolledCoursesList() {
        ObservableList<String> courses = this.session.getUserCourses();
        this.enrolledCourses.setItems(courses);

    }

    public void updateEnrolledCourses(ObservableList<String> courses) {
        this.enrolledCourses.setItems(courses);
        this.enrolledCourses.refresh();
    }

    public void displayAvailableCourses(ObservableList<String> list) {
        this.courseAvailableList.setItems(list);

    }

    public void initialiseAvailableCourseList() {
        this.courseAvailableList.setItems(this.session.getAvailableCourses());
    }

    public String getSelectedCourse() {
        return courseAvailableList.getSelectionModel().getSelectedItem();
    }

    public String getSelectedEnrolledCourse() {
        return this.enrolledCourses.getSelectionModel().getSelectedItem();
    }


    public void addSelectedCourse() throws Exception {
        this.controller.addSelectedCourse(getSelectedCourse());


    }

    public void removeCourse() throws Exception {
        this.controller.removeCourse(getSelectedEnrolledCourse());

    }

    public void cancelButton() {
        ViewController.backButton(this.cancelButton);
    }

    public void setController(MyCoursesController controller) {
        this.controller = controller;
    }
}