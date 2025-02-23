package Controller;

import Model.UserMeetUpHandler;
import View.MyMeetupsView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MyMeetUpsController {

    MyMeetupsView view;
    Session session;
    private MyMeetUpsController instance;

    public MyMeetUpsController(Session currentSession) {
        this.session = currentSession;


    }

    public MyMeetupsView getView() {
        return view;
    }

    public void setView(MyMeetupsView view) {
        this.view = view;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setAvailableMeetUps() {

    }

    public void getParticipants(String meetingname) throws Exception {

        UserMeetUpHandler handler = new UserMeetUpHandler(this.session.getUserProfile());
        this.view.updateParticipantList(handler.getParticipantsOfMeetingByName(meetingname));

    }

    public void loadMyMeetUpsView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MyMeetUpsView.fxml"));
            Parent root = fxmlLoader.load();
            MyMeetupsView view = fxmlLoader.getController();
            view.setController(getInstance());
            view.setSession(getSession());
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 1000, 800));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void setInstance(MyMeetUpsController newContr) {
        this.instance = newContr;
    }

    public MyMeetUpsController getInstance() {
        return this.instance;
    }
}
