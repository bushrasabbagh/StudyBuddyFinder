package Model;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Iterator;

/**
 * @author Eira Birkhammar
 * <p>
 * DatabaseHandler manages the connection to the database file specified
 * in the field filepath. DatabaseHandler establishes a connection by
 * creating a Workbook instance from the specified file path. Functions are provided
 * to open a connection to a specific Sheet as well a the entire Workbook.
 * <p>
 * DatabaseHandler provides functions to both retrieve data and add new data.
 * <p>
 * The connection always has to be closed once it's no longer needed. Functions are
 * provided to close the current connection.
 */
class DatabaseHandler {

    /**
     * Reads the <code>filepath</code>
     */
    private FileInputStream in;
    /**
     * Enables writing the <code>Workbook</code> content
     */
    private FileOutputStream out;
    /**
     * Represents the spreadsheet file as a <code>Workbook</code>
     */
    private Workbook workbook;
    /**
     * Represents a sheet in the spreadsheet file
     */
    private Sheet sheet;
    /**
     * The filepath of the .xlsx file
     */
    final String filepath = "data-DB.xlsx";
    /**
     * Represents the spreadsheet file
     */
    private File workbookFile;

    /**
     * Default constructor
     * Sets class attribute <code>File</code> to database file <i>Database.xlsx</i>
     *
     * @throws IOException if database file can not be found or read
     */
    public DatabaseHandler() throws IOException {
        this.workbookFile = new File(filepath);

    }

    /** Returns the <code>Sheet</code> associated with the active connection
     *
     * @return the Sheet associated with the current instance
     */
    public Sheet getSheet() throws Exception {

        return sheet;
    }


    /** Identifies the next available row by iterating over
     * the current sheet and finding the next blank row.
     *
     *
     * @return the index of the next available row as an int
     * @throws IOException if no sheet with the specified name exists in the file linked to the database connection
     */
    public int getNextFreeRow() throws Exception {
        int rowNum = 0;

        Sheet sheet = getSheet();
        Iterator<Row> row = sheet.rowIterator();
        Row currentRow;
        while (row.hasNext()) {
            currentRow = row.next();
            rowNum = currentRow.getRowNum();
            if (currentRow.getCell(0) == null) {
                break;
            }
        }

        return rowNum;
    }


    /**
     * Opens a connection to the database workbook file at a specific sheet
     *
     *
     * @param sheetname the name of the sheet to be opened
     * @throws IOException if no sheet with the specified name exists in the file linked to the database connection
     */
    public void openWorkbookAtSheet(String sheetname) throws IOException {
        try {
            FileInputStream input = new FileInputStream(this.workbookFile);
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheet(sheetname);
            this.in = input;
            this.workbook = workbook;
            this.sheet = sheet;



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes the content of the current Workbook instance to the database file
     * and closes the connection
     */
    public void writeDataAndClose() {
        try {


            this.out = new FileOutputStream(this.workbookFile);
            this.workbook.write(this.out);
            this.out.close();
            this.in.close();
            this.workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes the data passed to it to the next available
     * row in the current Sheet associated with the database connection
     *
     * @param data the data to be written
     * @throws IOException
     */
    public void writeDataToRow(String[] data) throws Exception {
        Row row = null;
        try {
            row = addRow();
        } catch (IOException e) {
            System.out.println("The database connection hasn't been initialised properly");
            e.printStackTrace();
        }
        int i = 0;
        for (String text : data) {
            row.createCell(i).setCellValue(text);
            i++;
        }


    }


    /**
     * Closes the Workbook instance without writing any data
     *
     *
     *@throws IOException
     *
     */
    public void closeWorkbook() {
        try {
            this.in.close();
            this.workbook.close();
        } catch (IOException e) {
            System.out.println("Can't close database connection because there's no open connection");
            e.printStackTrace();
        }
    }

    /**
     * Finds the next available row in the current Sheet linked
     * to the database connection and returns the row as a Row object
     *
     * @return the next available Row in the open Sheet
     * @throws Exception if the database connection hasn't been initialised properly
     */
    public Row addRow() throws Exception {

        Row row = null;
        if (this.sheet != null) {
            int lastrow = this.sheet.getLastRowNum();
            row = this.sheet.createRow(lastrow);
        } else {
            throw new Exception("Can't add row because database connection hasn't been initialised");
        }
        return row;
    }

    /**
     * Removes the <code>Row</code> in the <code>Sheet</code>
     * associated with the current connection
     *
     * @param row the Row to be removed
     */
    public void removeRow(Row row) {
        int rowIndex = row.getRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {

            if (row != null) {
                sheet.removeRow(row);

            }
        }
    }

    /**
     * Returns the Row in the specified sheet which contains the
     * String identifier passed to it. Assumes the identifier is unique
     * and will ignore any other Rows containing the same String value
     *
     * @param identifier the String value which identifies the Row
     * @return the Row containing the String identifier
     * @throws IOException if no sheet with the specified name exists in the file linked to the database connection
     */
    public Row getRowByIdentifier(String identifier) throws IOException {
        Row row = null;

        for (Row cells : this.sheet) {
            row = cells;
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getStringCellValue().equals(identifier)) {

                    return row;

                }
            }

        }

        return row;
    }


    /**
     * Checks if cell is empty
     *
     * @param cell the cell to check
     * @return true if the cell is empty
     */
    public boolean cellIsEmpty(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return false;
    }


    /**
     * Checks if a String value exists anywhere in a cell in the specified sheet
     * and returns TRUE if that's the case.
     *
     * @param text the String value to match with specified sheet
     * @return true if the entry exists
     * @throws IOException if no sheet with the specified name exists in the file linked to
     *                     the database connection
     */
    public boolean entryExists(String text) throws Exception {

        try {

            Sheet users = getSheet();
            Iterator<Row> row = users.rowIterator();
            Row currentRow;
            while (row.hasNext()) {
                currentRow = row.next();

                if (currentRow.getCell(0) == null) {
                    break;
                } else if (currentRow.getCell(0).getStringCellValue().equals(text)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Can't find sheet " + sheet + " in " + filepath);
            e.printStackTrace();
        }

        return false;
    }


}


