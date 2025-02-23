package Model;

import java.io.IOException;
import java.time.LocalDate;

public class Meetup {


    private final String meetupName;
    private final String time;
    private final String date;
    private final String course;
    private final String notes;

    public Meetup(String meetupName, String time, String date, String course, String notes) {
        this.meetupName = meetupName;
        this.time = time;
        this.date = date;
        this.course = course;
        this.notes = notes;

    }


    public String getMeetupName() {
        return meetupName;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getCourse() {
        return course;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetupName='" + meetupName + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", course='" + course + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }

    public String[] toArray() {
        return new String[]{this.meetupName, this.time, this.date, this.course, this.notes};


    }


    /**
     * Creates a new meetup that is saved in the Database.
     * Also checks for errors and returns text associates with that error.
     *
     * @param meetup meeting
     * @return text to display in view.
     * @throws IOException exception
     *  @author Bushra Al-Sabbagh
     */
    public String verifyMeetup(Meetup meetup) throws Exception {

        if (meetingExists())
            return "Meeting already exists";

        if (!checkTimeFormat(meetup))
            return "Time format is wrong, use hh:mm";

        if (!checkOldDate(meetup))
            return "You can't add a meeting with a date older then today";
        return "Meeting created";

    }





    /**
     * Checks if time format is right
     *
     * @param meetup meeting
     * @return true if timeformat is right else false
     * @author Bushra Al-Sabbagh
     */
    public boolean checkTimeFormat(Meetup meetup) {
        String time = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        return meetup.getTime().matches(time);
    }


    /**
     * Checks if user tries to add a new meeting with a old date
     *
     * @param meetup meetup
     * @return returns true if date is OK else false
     *  @author Bushra Al-Sabbagh
     */
    public boolean checkOldDate(Meetup meetup) {

        return LocalDate.now().isBefore(LocalDate.parse(meetup.getDate()));
    }


    /**
     * Checks if meeting with the same same already exists
     *
     * @return true if meeting already exists else false
     * @throws IOException Exception
     * @author Bushra Al-Sabbagh
     */
    public boolean meetingExists() throws Exception {
        DatabaseHandler db = new DatabaseHandler();
        db.openWorkbookAtSheet("Meetup");
        return db.entryExists(this.meetupName);
    }


}
