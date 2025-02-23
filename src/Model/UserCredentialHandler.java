package Model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCredentialHandler extends DatabaseHandler {

    private static final int COL_USERNAME = 0;
    private static final int COL_PASSWORD = 1;
    private static final int COL_EMAIL = 2;
    private static final String sheetName = "Users";
    private Sheet sheet;

    /**
     * Default constructor
     * Sets class attribute <code>File</code> to database file <i>Database.xlsx</i>
     *
     * @throws IOException if database file can not be found or read
     */
    public UserCredentialHandler() throws IOException {
        super();

    }

    public void connectToUsersSheet() throws Exception {
        openWorkbookAtSheet(sheetName);
        this.sheet = getSheet();

    }

    public boolean usernameMatchesPassword(String username, String password) throws IOException {
        String correctPassword = "";
        try {
            connectToUsersSheet();
            Row row = getRowByIdentifier(username);
            correctPassword = row.getCell(COL_PASSWORD).getStringCellValue();

            if (correctPassword.equals(password)) {
                closeWorkbook();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeWorkbook();
        return false;
    }

    public static boolean validateEmailFormat(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePasswordFormat(String password) {
        return (password != null && Pattern.matches(VALID_PASSWORD_REGEX, password));
    }

    public boolean usernameExists(String username) throws Exception {
        connectToUsersSheet();
        if (entryExists(username)) {
            closeWorkbook();
            return true;
        }
        closeWorkbook();
        return false;
    }

    public UserCredentials getUserCredentialsFromDB(String username) throws Exception {
        connectToUsersSheet();
        UserCredentials user;
        String password;
        String email;

        if (!usernameExists(username)) {

            closeWorkbook();
            return user = null;


        } else {
            Row row = getRowByIdentifier(username);
            username = row.getCell(COL_USERNAME).getStringCellValue();
            password = row.getCell(COL_PASSWORD).getStringCellValue();
            email = row.getCell(COL_EMAIL).getStringCellValue();
            closeWorkbook();
            return new UserCredentials(username, password, email);
        }

    }

    public void addUserCredentialsToDB(String username, String password, String email) throws Exception {
        openWorkbookAtSheet(sheetName);
        String[] data = {username, password, email};
        writeDataToRow(data);
        closeWorkbook();

    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USER_NAME_REGEX =
            Pattern.compile("^(?=^[^_\\n]+_?[^_\\n]+$)\\w{3,20}$", Pattern.CASE_INSENSITIVE);

    private static final String VALID_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$";


}
