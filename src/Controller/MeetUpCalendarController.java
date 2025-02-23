package Controller;

import Model.UserMeetUpHandler;
import View.MeetUpCalendarView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class MeetUpCalendarController {

    private Session currentSession;
    private MeetUpCalendarView view;
    private MeetUpCalendarController instance;

    public MeetUpCalendarController(Session session) {
        this.currentSession = session;

    }

    public void loadMeetUpCalendarView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MeetUpCalendarView.fxml"));
            Parent root = fxmlLoader.load();
            MeetUpCalendarView view = fxmlLoader.getController();
            view.setSession(getSession());
            view.setController(getInstance());
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 1200, 600));
            registerStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private Session getSession() {
        return this.currentSession;
    }

    public void setSession(Session session) {
        this.currentSession = session;
    }

    private MeetUpCalendarController getInstance() {
        return this.instance;
    }

    public void setInstance(MeetUpCalendarController instance) {
        this.instance = instance;
    }

    public void displayAvailableMeetups() throws Exception {
        UserMeetUpHandler handler = new UserMeetUpHandler(this.currentSession.getUserProfile());
        this.view.updateCalendar(handler.getAvailableMeetups());
    }

    public void displayParticipants(String meetupName) throws Exception {
        UserMeetUpHandler handler = new UserMeetUpHandler(this.currentSession.getUserProfile());
        this.view.updateParticipantList(handler.getParticipantsOfMeetingByName(meetupName));


    }


    public void setView(MeetUpCalendarView meetUpCalendarView) {
        this.view = meetUpCalendarView;
    }

    public void joinMeeting(String selectedMeetingTitle) throws Exception {
        String username = this.currentSession.getUserProfile().getUsername();
        ObservableList<String> participants = this.view.getParticipantList();
        for (String string : participants) {
            if (string.equals(username)) {
                this.view.updateMessageLabel("You have already joined this meeting!", "RED");
                return;
            }

        }

        UserMeetUpHandler handler = new UserMeetUpHandler(this.currentSession.getUserProfile());
        handler.addParticipantToDB(selectedMeetingTitle);
        displayParticipants(selectedMeetingTitle);
        this.view.updateMessageLabel("Added to meeting", "GREEN");


    }
}
