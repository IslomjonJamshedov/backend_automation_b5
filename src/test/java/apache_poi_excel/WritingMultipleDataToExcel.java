package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WritingMultipleDataToExcel {

    public static void main(String[] args) throws IOException {

        String filepath = "test_data/WriteData.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Sheet2");

        Object[][] employeeData = {
                {"EmpID", "Name", "Title"},
                {101, "Tech Global", "DevOps"},
                {102, "Ulan", "Developer"},
                {103, "Abe", "Instructor"},
        };

        // getting the length of the multidimensional array
        // it will be our reference that for number rows we
        // will put into the Excel file
        int rowLength = employeeData.length;
        System.out.println("Length of the multidimensional array is " + rowLength);

        // getting the cell length on the row
        int cellLength = employeeData[0].length;
        System.out.println("Length of the single array in multidimensional array is " + cellLength);

        // Creating the rows on the Excel file
        for (int r = 0; r < rowLength; r++) {
            //Getting the corresponding row
            XSSFRow row = sheet.createRow(r);
            //creating the cells for each row
            for (int c = 0; c < cellLength; c++) {
                //creating the cell for that row
                XSSFCell cell = row.createCell(c);

                // Getting the corresponding data from the
                // employeeData based on the indexes of nest loop
                Object cellValue = employeeData[r][c];
                // check each data from the multidimensional array and write it into Excel file
                if (cellValue instanceof String)
                    cell.setCellValue((String) cellValue);

                if (cellValue instanceof Integer)
                    cell.setCellValue((Integer) cellValue);

                if (cellValue instanceof Boolean)
                    cell.setCellValue((Boolean) cellValue);
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        workbook.write(fileOutputStream);
        workbook.close();

    }
}
