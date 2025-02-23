package Controller;

import Model.*;
import javafx.collections.ObservableList;

public class Session {

  private final UserProfile userProfile;
  private final CourseCatalog availableCourses;


  public Session(UserCredentials userCredentials) throws Exception {
      this.userProfile = new UserProfile(userCredentials);
      this.userProfile.loadUserCoursesFromDB();
      this.availableCourses = new CourseCatalog();


  }

    public UserProfile getUserProfile() {
        return userProfile;
    }


    public ObservableList<String> getUserCourses() {
        return this.userProfile.getUserCourses();

    }

    public ObservableList<String> getAvailableCourses() {
        return this.availableCourses.getCourseCatalog();
    }

    public ObservableList<Meetup> getUserMeetups() throws Exception {
        this.userProfile.loadUserMeetupsFromDB();
        return this.userProfile.getUserMeetups();
    }


}

