package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;

public class UserMeetUpHandler extends DatabaseHandler {


    private final String SHEET_NAME_PARTICIPANTS = "MeetUpParticipants";
    private final String SHEET_NAME_MEETUPCALENDAR = "MeetUpCalendar";
    UserProfile user;


    /**
     * Default constructor
     * Sets class attribute <code>File</code> to database file <i>Database.xlsx</i>
     *
     * @throws IOException if database file can not be found or read
     */
    public UserMeetUpHandler(UserProfile user) throws IOException {
        super();
        this.user = user;


    }


    /**
     * Returns a list of all meetups with time less then today and with the same
     * courses as the student
     *
     * @return a list of meetups
     * @throws IOException exception
     * @
     * @author Bushra Al-Sabbagh
     */
    public ObservableList<Meetup> getAvailableMeetups() throws Exception {

        ObservableList<Meetup> list = FXCollections.observableArrayList();
        openWorkbookAtSheet(SHEET_NAME_MEETUPCALENDAR);
        Sheet sheet = getSheet();
        int lastRow = sheet.getLastRowNum();
        ObservableList<String> courseList = this.user.getUserCourses();

        String courseName;
        for (int i = 1; i <= lastRow; i++) {
            courseName = sheet.getRow(i).getCell(3).getStringCellValue();
            if (courseList.contains(courseName)) {
                list.add(new Meetup(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue(),
                        sheet.getRow(i).getCell(2).getStringCellValue(), sheet.getRow(i).getCell(3).getStringCellValue(),
                        sheet.getRow(i).getCell(4).getStringCellValue()));
            }
        }
        closeWorkbook();
        return list;
    }

    /**
     * @return a list of meetups
     * @throws IOException exception
     * @author Bushra Al-Sabbagh
     */
    public ObservableList<Meetup> initialiseUserMeetUpList(ObservableList<Meetup> list) throws Exception {


        ArrayList listOfMeetupNames = getAllMeetingNamesOfAStudent();
        openWorkbookAtSheet(SHEET_NAME_MEETUPCALENDAR);
        Sheet sheet = getSheet();
        int lastRow = getNextFreeRow();

        String meetingName;
        for (int i = 0; i <= lastRow; i++) {
            meetingName = sheet.getRow(i).getCell(0).getStringCellValue();
            if (listOfMeetupNames.contains(meetingName)) {
                list.add(new Meetup(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue(),
                        sheet.getRow(i).getCell(2).getStringCellValue(), sheet.getRow(i).getCell(3).getStringCellValue(),
                        sheet.getRow(i).getCell(4).getStringCellValue()));
            }
        }
        closeWorkbook();
        return list;


    }

    /**
     * Returns a list of all meeting names for a student
     *
     * @return a list of meeting names
     * @throws IOException exception
     * @author Bushra Al-Sabbagh
     */
    public ArrayList<String> getAllMeetingNamesOfAStudent() throws Exception {

        ArrayList<String> list = new ArrayList();
        openWorkbookAtSheet(SHEET_NAME_PARTICIPANTS);


        Sheet sheet = getSheet();
        int lastRow = sheet.getLastRowNum();
        String currentMeeting = "";
        String username = this.user.getUsername();
        String currentname = "";
        for (int i = 1; i <= lastRow; i++) {
            currentname = sheet.getRow(i).getCell(1).getStringCellValue();
            currentMeeting = sheet.getRow(i).getCell(0).getStringCellValue();
            if (currentname.equals(username)) {
                list.add(currentMeeting);
            }
        }

        closeWorkbook();
        return list;
    }

    /**
     * Checks if meeting with the same same already exists
     *
     * @param meetup meeting
     * @return true if meeting already exists else false
     * @throws IOException Exception
     * @author Bushra Al-Sabbagh
     */
    public boolean meetingExists(Meetup meetup) throws Exception {
        openWorkbookAtSheet(SHEET_NAME_MEETUPCALENDAR);

        Sheet sheet = getSheet();
        int lastRow = sheet.getLastRowNum();

        String MeetingName = "";
        for (int i = 0; i <= lastRow; i++) {
            MeetingName = sheet.getRow(i).getCell(0).getStringCellValue();
            if (MeetingName.equals(meetup.getMeetupName())) {
                closeWorkbook();
                return true;
            }

        }

        closeWorkbook();
        return false;
    }


    public void createNewMeetup(Meetup meetup) throws Exception {

        openWorkbookAtSheet(SHEET_NAME_MEETUPCALENDAR);
        Sheet sheet = getSheet();
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(0).setCellValue(meetup.getMeetupName());
        row.createCell(1).setCellValue(meetup.getTime());
        row.createCell(2).setCellValue(meetup.getDate());
        row.createCell(3).setCellValue(meetup.getCourse());
        row.createCell(4).setCellValue(meetup.getNotes());

        writeDataAndClose();

    }


    public void addParticipantToDB(String meetingName) throws Exception {

        openWorkbookAtSheet(SHEET_NAME_PARTICIPANTS);
        String[] data = {meetingName, this.user.getUsername()};
        writeDataToRow(data);
        writeDataAndClose();

    }


    public ObservableList<String> getParticipantsOfMeetingByName(String meetingname) throws Exception {
        openWorkbookAtSheet(SHEET_NAME_PARTICIPANTS);
        ObservableList<String> participants = FXCollections.observableArrayList();
        Sheet sheet = getSheet();
        Row currentRow = null;
        for (Row cells : sheet) {
            currentRow = cells;
            if (currentRow.getCell(0).getStringCellValue().equals(meetingname)) {
                participants.add(currentRow.getCell(1).getStringCellValue());
            }

        }
        closeWorkbook();
        return participants;


    }

}

