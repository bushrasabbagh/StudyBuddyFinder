package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import java.util.Iterator;

/**
 * @author Basoz Soza
 * <p>
 * This class represents the available courses that a user can add to their profile.
 * </p>
 */
public class CourseCatalog extends DatabaseHandler {

    String sheetname = "CourseCatalog";
    private static final int COL_COURSENAME = 0;
    ObservableList<String> courseCatalog;


    /** Initialises the <code>courseCatalog</code> which contains all the available courses.
     * @throws Exception
     */
    public CourseCatalog() throws Exception {
        this.courseCatalog = FXCollections.observableArrayList();
        loadCourseCatalog();
    }


    /** Gets the list of courses
     * @return <code>courseCatalog</code>
     */
    public ObservableList<String> getCourseCatalog() {
        return this.courseCatalog;
    }


    /** Retrieves the content of the sheet "CourseCatalog"
     * from the database file and stores it in the class attribute
     * <code>ObservableList<String></code>
     *
     * @throws Exception if the database connection is faulty
     */
    public void loadCourseCatalog() throws Exception {

        openWorkbookAtSheet(sheetname);
        Sheet sheet = getSheet();
        Iterator<Row> row = sheet.rowIterator();
        Row currentRow;
        while (row.hasNext()) {
            currentRow = row.next();
            if (!currentRow.getCell(COL_COURSENAME).getStringCellValue().isBlank()) {
                this.courseCatalog.add(currentRow.getCell(COL_COURSENAME).getStringCellValue());
            }
        }

        closeWorkbook();

    }


}
