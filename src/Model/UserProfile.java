package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserProfile {

    private final UserCredentials credentials;
    private ObservableList<Meetup> userMeetups;
    private final ObservableList<String> userCourses;

    public UserProfile(UserCredentials credentials) {
        this.credentials = credentials;
        this.userMeetups = FXCollections.observableArrayList();
        this.userCourses = FXCollections.observableArrayList();

    }

    public String getUsername() {
        return this.credentials.getUsername();
    }


    public void loadUserMeetupsFromDB() throws Exception {
        UserMeetUpHandler handler = new UserMeetUpHandler(this);
        this.userMeetups = handler.initialiseUserMeetUpList(getUserMeetups());
    }

    public void loadUserCoursesFromDB() throws Exception {
        UserCourseHandler handler = new UserCourseHandler(this);
        handler.getCoursesOfStudentsDB();
    }

    public ObservableList<Meetup> getUserMeetups() {
        return userMeetups;
    }

    public ObservableList<String> getUserCourses() {
        return userCourses;
    }



}
