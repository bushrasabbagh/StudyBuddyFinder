package Model;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author Basoz Soza
 *
 * <p>UserCourseHandler is a subclass of DatabaseHandler. UserCourseHandler handles the courses of the UserProfile linked to the current session.
 * Functions are provided to retrieve the list of courses a user has added, to modify this list and to update the database accordingly.</p>
 *
 * <p>UserCourseHandler is a subclass of DatabaseHandler.
 * UserCourseHandler handles the courses of the UserProfile linked
 * to the current session. Functions are provided to retrieve the list
 * of courses a user has added, to modify this list and to update the database accordingly.</p>
 */


public class UserCourseHandler extends DatabaseHandler {

    /**
     * Index of the column <i>Username</i>
     */
    private static final int COL_USERNAME = 0;
    /**
     * Index of the column <i>Course</i>
     */
    private static final int COL_COURSE = 1;
    /**
     * Name of the sheet in the database file
     */
    private final String sheetName = "Courses";
    /**
     * The <code>UserProfile</code> associated with the current <code>Session</code>
     */
    private final UserProfile user;
    /**
     * The <code>Sheet</code> instance of the sheet in the database file
     */
    private Sheet COURSES_SHEET;


    /**
     * Default constructor
     * Sets class attribute <code>File</code> to database file <i>Database.xlsx</i>
     * Sets class attribute <code>user</code> to the current <code>UserProfile</code>
     * @param currentuser the current UserProfile associated with the active session
     * @throws Exception if no user is associated with the current session
     * @see Controller.Session
     * @see DatabaseHandler
     */
    public UserCourseHandler(UserProfile currentuser) throws Exception {
        super();
        if (currentuser == null) {
            throw new IllegalArgumentException("Can't initialise UserCourseHandler because no UserProfile is linked to the current session");
        } else {
            this.user = currentuser;
        }
    }


    /** Connects to the sheet associated with UserCourseHandler
     *
     *
     * @throws Exception
     */
    public void connectToCourseSheet() throws Exception {
        openWorkbookAtSheet(sheetName);
        this.COURSES_SHEET = getSheet();

    }


    /**
     * Adds new course to excelfile.
     *
     * @param course course that is added
     * @throws IOException if database file can not be found or read
     *
     */
    public void addCourseToDatabase(String course) throws Exception {
        connectToCourseSheet();
        if (getSheet() != null) {
            String[] data = {this.user.getUsername(), course};
            writeDataToRow(data);
        } else {
            throw new Exception("Can't access database");
        }
        closeWorkbook();
    }


    /**
     * Checks if a course is added by the student
     *
     * @param course the name of the course
     * @return true of if the course is already added
     */
    public boolean courseIsAlreadyAdded(String course) {
        ObservableList<String> userCourses = this.user.getUserCourses();
        for (String enrolled : userCourses) {
            if (enrolled.equals(course)) {
                return true;
            }
        }
        return false;

    }


    /**
     * Fetches all the courses of a user and adds them
     * to the <class>UserProfile</class> associated with
     * the current session
     *
     *
     * @throws IOException if the database connection is faulty
     */
    public void getCoursesOfStudentsDB() throws Exception {

        openWorkbookAtSheet(sheetName);
        ObservableList<String> list = this.user.getUserCourses();
        String username = this.user.getUsername();
        Sheet enrolled = getSheet();
        Iterator<Row> row = enrolled.rowIterator();
        Row currentRow;
        String currentCell;
        while (row.hasNext()) {
            currentRow = row.next();
            if (!cellIsEmpty(currentRow.getCell(COL_USERNAME))) {
                currentCell = currentRow.getCell(COL_USERNAME).getStringCellValue();
                if (currentCell.equals(username)) {
                    list.add(currentRow.getCell(COL_COURSE).getStringCellValue());
                }
            }


        }


        closeWorkbook();

    }

    /**
     * Removes the course specified by the parameter
     *
     * @param coursename name of the course to be removed
     * @throws Exception if the connection is faulty
     */
    public void removeCourseFromDB(String coursename) throws Exception {
        connectToCourseSheet();
        try {
            Row row = getRowByIdentifier(coursename);
            removeRow(row);

            writeDataAndClose();
        } catch (Exception e) {
        }
    }


    /**
     * Removes the specified course from the <code>UserProfile</code>
     * associated with the current session
     *
     * @param coursename
     */
    public void removeCourseFromUserProfile(String coursename) {
        ObservableList<String> list = user.getUserCourses();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(coursename)) {
                list.remove(i);
            }
        }
    }

    /**
     * Adds the specified course to the <code>UserProfile</code>
     * associated with the current Session
     *
     * @param coursename the name of the course to be added
     */
    public void addCourseToUserProfile(String coursename) {
        ObservableList<String> list = user.getUserCourses();
        list.add(coursename);


    }


}
