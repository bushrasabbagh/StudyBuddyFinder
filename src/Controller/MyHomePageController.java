package Controller;

import View.MyHomePageView;

import View.MyMeetupsView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyHomePageController {


    private final Session currentSession;
    private MyHomePageView view;
    private MyHomePageController instance;
    private MyMeetupsView instanceMeetup;
    private MyMeetUpsController meetContrInstance;


    public MyHomePageController(Session session) {
        this.currentSession = session;

    }

    /**
     * Renders the view of the users profile and main page.
     *
     * @author Maya
     */
    public void setView(MyHomePageView view) {
        this.view = view;
    }

    public void setInstance(MyHomePageController instance) {
        this.instance = instance;
    }

    public MyHomePageController getInstance() {
        return this.instance;
    }

    public Session getSession() {
        return this.currentSession;
    }


    public void loadUserHomePage() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MyHomepageView.fxml"));
            Parent root = fxmlLoader.load();
            MyHomePageView view = fxmlLoader.getController();
            view.setSession(getSession());
            view.setController(getInstance());
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











