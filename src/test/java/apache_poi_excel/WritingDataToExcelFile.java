package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WritingDataToExcelFile {

    public static void main(String[] args) throws IOException {

        // getting file path
        String filePath = "test_data/WriteData.xlsx";

        // created a workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // created sheet on the workbook
        XSSFSheet sheet = workbook.createSheet("Tech");

        // go to the specific row on that sheet
        XSSFRow row = sheet.createRow(1);

        // go to specific cell on that row
        XSSFCell cell = row.createCell(1);

        // write the value on the that cell
        cell.setCellValue("Tech Global");

        // Sore the file path into the system
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        // complete writing process on the file
        workbook.write(fileOutputStream);

        // closing the file after writing completion
        fileOutputStream.close();
    }
}
