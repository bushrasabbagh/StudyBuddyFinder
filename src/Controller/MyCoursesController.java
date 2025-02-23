package Controller;

import Model.UserCourseHandler;
import View.MyCoursesView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyCoursesController {

    private final Session session;
    private MyCoursesView view;
    private MyCoursesController instance;


    public MyCoursesController(Session currentSession) {
        this.session = currentSession;
    }


    public void setViewInstance(MyCoursesView view) {
        this.view = view;

    }

    public void loadMyCoursesView() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MyCoursesView.fxml"));
            Parent root = fxmlLoader.load();
            MyCoursesView view = fxmlLoader.getController();
            view.setSession(getSession());
            view.setController(this);
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 700, 800));
            registerStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    private Session getSession() {
        return this.session;
    }


    public void addSelectedCourse(String selectedCourse) throws Exception {
        UserCourseHandler handler = new UserCourseHandler(this.session.getUserProfile());
        if (handler.courseIsAlreadyAdded(selectedCourse)) {
            this.view.updateMessage("You have already added this course", "RED");
        } else {
            handler.addCourseToDatabase(selectedCourse);
            handler.addCourseToUserProfile(selectedCourse);
            this.view.updateEnrolledCourses(this.session.getUserProfile().getUserCourses());
        }
    }

    public void loadCourseCatalog() {
        ObservableList<String> courses = this.session.getAvailableCourses();
        this.view.displayAvailableCourses(courses);
    }

    public void setInstance(MyCoursesController controller) {
        this.instance = controller;
    }

    public void removeCourse(String selectedEnrolledCourse) throws Exception {
        UserCourseHandler handler = new UserCourseHandler(this.session.getUserProfile());
        handler.removeCourseFromDB(view.getSelectedEnrolledCourse());
        handler.removeCourseFromUserProfile(selectedEnrolledCourse);
        view.updateEnrolledCourses(session.getUserCourses());

    }
}

