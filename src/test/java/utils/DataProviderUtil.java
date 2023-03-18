package utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtil {
    @DataProvider(name = "DataFromExcel")
    public static Object[][] getDataFromExcelFileWithDataProvider(){

        //Opening the Excel file
        ExcelUtil.openExcelFile("PetDataToRead", "PetData");

        //Storing converting data to multidimensional array
        Object[][] dataArray = ExcelUtil.convertListOfListToMultidimensionalArray(ExcelUtil.getValues());

        //closing the Excel file
        ExcelUtil.closingExcelFile();

        return dataArray;
    }
}
