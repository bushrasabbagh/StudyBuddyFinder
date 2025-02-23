package Controller;

import Model.Meetup;
import Model.UserMeetUpHandler;
import View.NewMeetupView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewMeetUpController {

    Session session;
    private NewMeetUpController instance;
    private NewMeetupView view;


    public NewMeetUpController(Session session) {
        this.session = session;
    }

    public void loadNewMeetUpView() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/NewMeetUpView.fxml"));
            Parent root = fxmlLoader.load();
            NewMeetupView view = fxmlLoader.getController();
            view.setController(getInstance());
            view.setSession(getSession());
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 1100, 800));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private Session getSession() {
        return this.session;
    }

    /**
     * Creates a list of times from 07:00 to 22:00 with interval of 30 min.
     *
     * @return list of times
     * @author Fredrik Pettersson
     */
    public ArrayList<String> createList() {
        ArrayList<String> list = new ArrayList<>();

        String time;
        int hh = 7;
        int mm;
        for (int i = 0; i < 31; i++) {
            if (i % 2 == 0) {
                mm = 0;
                if (hh < 10)
                    time = "0" + hh + ":" + mm + "0";
                else
                    time = hh + ":" + mm + "0";
            } else {
                mm = 30;
                if (hh < 10)
                    time = "0" + hh + ":" + mm;
                else
                    time = hh + ":" + mm;
                hh++;
            }
            list.add(time);
        }
        return list;
    }


    public void setInstance(NewMeetUpController controller) {
        this.instance = controller;
    }

    public NewMeetUpController getInstance() {
        return instance;
    }

    public void setViewInstance(NewMeetupView view) {
        this.view = view;
    }

    private boolean checkIfDataIsNull() {
        return (this.view.getNotesTextField().isBlank() || this.view.getNameTextField().isBlank() || this.view.getNotesTextField().isBlank()
                || this.view.getSelectedTime().isBlank() || this.view.getSelectedCourse().isBlank());
    }

    /**
     * Creates a new meeting
     *
     * @throws IOException exception
     * @author Fredrik Pettersson
     */
    public void createMeetup() throws Exception {

        if (checkIfDataIsNull()) {
            view.setMeetupMessage("Fill in the blank fields", "RED");
            return;
        }

        String date = view.getDatePicker().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String meetupName = view.getNameTextField();
        String time = view.getSelectedTime();
        String notes = view.getNotesTextField();
        String course = view.getSelectedCourse();

        Meetup meetup = new Meetup(meetupName, time, date, course, notes);
        UserMeetUpHandler handler = new UserMeetUpHandler(this.session.getUserProfile());
        handler.createNewMeetup(meetup);
        handler.addParticipantToDB(meetupName);
        view.setMeetupMessage("Meeting created", "GREEN");

    }


}

