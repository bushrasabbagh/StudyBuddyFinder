package View;

import Controller.MyMeetUpsController;
import Controller.Session;
import Controller.ViewController;
import Model.Meetup;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyMeetupsView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button backButton;
    @FXML
    private TableView<Meetup> table;
    @FXML
    private TableColumn<Meetup, String> meetingName;
    @FXML
    private TableColumn<Meetup, String> meetingTime;
    @FXML
    private TableColumn<Meetup, String> meetingDate;
    @FXML
    private TableColumn<Meetup, String> meetingCourse;
    @FXML
    private TableColumn<Meetup, String> meetingNote;
    @FXML
    private ListView<String> participantsList;


    private Session session;
    private MyMeetUpsController controller;
    private MyMeetupsView instance;


    public MyMeetupsView() throws IOException {


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ViewController.loadIcon(brandingImageView);
            instance = this;
            this.controller.setView(this);
            try {
                setupTable();
                initializeUserMeetUps();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }


    /**
     * Creates the tableView
     * @author Fredrik Pettersson
     * @throws IOException exception
     */
    public void setupTable() throws IOException {
        meetingName.setCellValueFactory(new PropertyValueFactory<>("meetupName"));
        meetingTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        meetingDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        meetingCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        meetingNote.setCellValueFactory(new PropertyValueFactory<>("notes"));


    }

    public void initializeUserMeetUps() throws Exception {
        table.getItems().addAll(this.session.getUserMeetups());

    }

    public String getSelectedMeeting() {
        return table.getSelectionModel().getSelectedItem().getMeetupName();
    }

    public ListView getParticipantsList() {
        return participantsList;
    }

    public void updateParticipantList(ObservableList<String> list) {
        this.participantsList.setItems(list);

    }

    public void clearParticipantsList() {
        this.participantsList.getItems().clear();
    }

    /**
     * Closes the window
     *
     * @author Fredrik Pettersson
     */
    public void backButton() {
        ViewController.backButton(this.backButton);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setInstance(MyMeetupsView currentInstance) {
        this.instance = currentInstance;
    }

    private MyMeetupsView getInstance() {
        return this.instance;
    }


    public void setController(MyMeetUpsController instance) {
        this.controller = instance;
    }

    public void showParticipants() throws Exception {
        String meeting = table.getSelectionModel().getSelectedItem().getMeetupName();
        this.controller.getParticipants(meeting);

    }
}
