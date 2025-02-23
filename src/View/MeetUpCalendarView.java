package View;

import Controller.MeetUpCalendarController;
import Controller.Session;
import Controller.ViewController;
import Model.Meetup;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MeetUpCalendarView implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button joinMeetingButton;
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
    @FXML
    private Label messageLabel;
    @FXML
    private Label infoLabel;

    private MeetUpCalendarController contr;
    private Session session;
    private MeetUpCalendarView viewInstance;


    public MeetUpCalendarView() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ViewController.loadIcon(this.brandingImageView);
            infoLabel.setText("Select a meeting to see participants\nand join the meeting");
            viewInstance = this;
            this.contr.setView(this);
            try {
                initialiseCalendar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Creates the tableView
     *
     * @throws IOException exception
     * @author Fredrik Pettersson
     */
    public void initialiseCalendar() throws Exception {

        meetingName.setCellValueFactory(new PropertyValueFactory<>("meetupName"));
        meetingTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        meetingDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        meetingCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        meetingNote.setCellValueFactory(new PropertyValueFactory<>("notes"));
        this.contr.displayAvailableMeetups();
    }

    public void updateCalendar(ObservableList<Meetup> meetups) {

        table.setItems(meetups);


    }

    /**
     * Adds a new student to a already existing meeting
     * @throws IOException exception
     * @author Fredrik Pettersson

    public void joinMeeting() throws IOException {
    this.calendar = new MeetUpCalendar();
    Meetup meetup = table.getSelectionModel().getSelectedItem();

    messageLabel.setText(calendar.joinExistingMeeting(meetup, student));
    showParticipants();
    }

    /**
     * Updates participants lists that shows all participants of a meeting
     * @author Fredrik Pettersson
     * @throws IOException exception

    public void showParticipants() throws IOException {
    participantsList.getItems().clear();
    String courseName = table.getSelectionModel().getSelectedItem().getMeetupName();
    participantsList.getItems().addAll(joinMeeting.getAllParticipants(courseName));

    }

    /**
     * Sets user

     * @author Fredrik Pettersson
     */
    public void setController(MeetUpCalendarController controller) {

        this.contr = controller;
    }

    public void setSession(Session current) {
        this.session = current;
    }

    public void updateMessageLabel(String message, String color) {
        this.messageLabel.setText(message);
        this.messageLabel.setTextFill(Color.valueOf(color));
    }

    /**
     * Closes the window
     *
     * @author Fredrik Pettersson
     */
    public void backButton() {
        ViewController.backButton(this.backButton);
    }


    public void updateParticipantList(ObservableList<String> participants) {
        this.participantsList.setItems(participants);
    }


    public ObservableList<String> getParticipantList() {
        return participantsList.getItems();
    }


    public String getSelectedMeetingTitle() {
        return this.table.getSelectionModel().getSelectedItem().getMeetupName();
    }

    public void joinMeeting() throws Exception {
        this.contr.joinMeeting(getSelectedMeetingTitle());


    }

    public void showParticipants(MouseEvent mouseEvent) throws Exception {
        this.contr.displayParticipants(getSelectedMeetingTitle());

    }
}
