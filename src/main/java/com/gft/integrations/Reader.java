package com.gft.integrations;

import com.gft.utility.typeDetector;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Reader extends typeDetector {

    public static Map<String, String> read(@NotNull String path) {
        Map<String, String> textRead = null;

        if (checkPath(path)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                String fileType = detect(path);

                switch (fileType) {
                    case "xls":
                        System.out.println("Reading xls file");
                        textRead = readXlx(fileInputStream, true);
                        break;

                    case "xlsx":
                        System.out.println("Reading xlsx file");
                        textRead = readXlsx(fileInputStream, true);
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

        return textRead;
    }

    private static boolean checkPath(@NotNull String path) {
        return new File(path).exists();
    }

    private static Map<String, String> readXlx (@NotNull FileInputStream fileInputStream, boolean ignoreFirstSheet) {
        Map<String, String> textRead = new HashMap<>();

        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);      // workbook is excel file
            textRead = readWorkbook(hssfWorkbook, ignoreFirstSheet);
            hssfWorkbook.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return textRead;
    }

    private static Map<String, String> readXlsx (@NotNull FileInputStream fileInputStream, boolean ignoreFirstSheet) {
        Map<String, String> textRead = new HashMap<>();

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);      // workbook is excel file
            textRead = readWorkbook(xssfWorkbook, ignoreFirstSheet);
            xssfWorkbook.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return textRead;
    }

    private static Map<String, String> readWorkbook(@NotNull Workbook workbook, boolean ignoreFirstSheet) {
        Map<String, String> textRead = new HashMap<>();
        try {
            int firstSheet = 0;
            if (ignoreFirstSheet) {
                firstSheet = 1;
            }

            for (int i = firstSheet; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);

                StringBuilder text = new StringBuilder();
                String txtNameFile = sheet.getRow(1).getCell(0).getStringCellValue();

                for (int r = 1; r <= sheet.getLastRowNum(); ++r) {
                    Row row = sheet.getRow(r);

                    for (int c = 1; c < 3; ++c) {
                        Cell cell = row.getCell(c);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    // System.out.print(cell.getStringCellValue());
                                    text.append(cell.getStringCellValue()).append("\t");
                                    break;
                                case BOOLEAN:
                                    // System.out.print(cell.getBooleanCellValue());
                                    text.append(cell.getBooleanCellValue()).append("\t");
                                    break;
                                case NUMERIC:
                                    // System.out.print(cell.getNumericCellValue());
                                    text.append(cell.getNumericCellValue()).append("\t");
                                    break;
                            }
                        }
                    }
                    text.append("\n");
                }

                textRead.put(txtNameFile, text.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return textRead;
    }

//    private static Map<String, String> readXlsx (@NotNull FileInputStream fileInputStream, boolean ignoreFirstSheet) {
//        Map<String, String> textRead = new HashMap<>();
//
//        try {
//            int firstSheet = 0;
//            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);      // workbook is excel file
//
//            if (ignoreFirstSheet) {
//                firstSheet = 1;
//            }
//
//            for (int i = firstSheet; i < xssfWorkbook.getNumberOfSheets(); ++i) {
//                Sheet sheet = xssfWorkbook.getSheetAt(i);
//
//                StringBuilder text = new StringBuilder();
//                String txtNameFile = sheet.getRow(1).getCell(0).getStringCellValue();
//
//                for (int r = 1; r <= sheet.getLastRowNum(); ++r) {
//                    Row row = sheet.getRow(r);
//
//                    for (int c = 1; c < 3; ++c) {
//                        Cell cell = row.getCell(c);
//                        if (cell != null) {
//                            switch (cell.getCellType()) {
//                                case STRING:
//                                    // System.out.print(cell.getStringCellValue());
//                                    text.append(cell.getStringCellValue()).append("\t");
//                                    break;
//                                case BOOLEAN:
//                                    // System.out.print(cell.getBooleanCellValue());
//                                    text.append(cell.getBooleanCellValue()).append("\t");
//                                    break;
//                                case NUMERIC:
//                                    // System.out.print(cell.getNumericCellValue());
//                                    text.append(cell.getNumericCellValue()).append("\t");
//                                    break;
//                            }
//                        }
//                    }
//                    text.append("\n");
//                }
//
//                textRead.put(txtNameFile, text.toString());
//                xssfWorkbook.close();
//            }
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            e.printStackTrace();
//        }
//
//        return textRead;
//    }
}
