package com.gft.integrations;

import com.gft.utility.typeDetector;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;

public class Reader extends typeDetector {

    public static void read(@NotNull String path) {
        if (checkDir(path)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                String fileType = detect(path);

                switch (fileType) {
                    case "xls":
                        System.out.println("Reading xls file");
                        readXlx(fileInputStream, false);
                        break;

                    case "xlsx":
                        System.out.println("Reading xlsx file");
                        readXlsx(fileInputStream, true);
                        break;

                    case "null":
                        System.out.println("Impossible determinate type of file");
                        break;
                }

                fileInputStream.close();
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }

        } else {
            System.out.println("Sorry, path o file not exist");
        }
    }

    private static boolean checkDir(@NotNull String path) {
        return new File(path).exists();
    }

    private static void readXlx (@NotNull FileInputStream fileInputStream, boolean ignoreFirstSheet) {
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);      // workbook is excel file
            Sheet firstSheet = hssfWorkbook.getSheetAt(0);                // firstSheet is the first sheet of excel file

            hssfWorkbook.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    private static void readXlsx (@NotNull FileInputStream fileInputStream, boolean ignoreFirstSheet) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);      // workbook is excel file
            Sheet firstSheet;                                                   // firstSheet is the first sheet of excel file
            if (ignoreFirstSheet) {
                firstSheet = xssfWorkbook.getSheetAt(1);
            } else {
                firstSheet = xssfWorkbook.getSheetAt(0);
            }

            String txtNameFile = firstSheet.getRow(1).getCell(0).getStringCellValue();
            for (int r = 1; r < firstSheet.getLastRowNum(); ++r) {
                Row row = firstSheet.getRow(r);

                for (int c = 1; c < row.getLastCellNum(); ++c) {
                    Cell cell = row.getCell(c);

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue());
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue());
                                break;
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue());
                                break;
                        }
                    }
                    System.out.print(" - ");
                }
                System.out.println();
            }
            xssfWorkbook.close();

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
