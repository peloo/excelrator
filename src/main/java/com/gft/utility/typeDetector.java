package com.gft.utility;

public class typeDetector {
    public static String detect(String path) {
        if (isXlxFyle(path)) {
            return "xls";
        }

        if (isXlxsFyle(path)) {
            return "xlsx";
        }

        return "null";
    }

    private static boolean isXlxFyle(String path) {
        String[] stringSplit = path.split("\\.");
        if (stringSplit.length >= 2) {
            return stringSplit[1].equals("xls");
        }
        return false;
    }

    private static boolean isXlxsFyle(String path) {
        String[] stringSplit = path.split("\\.");
        if (stringSplit.length >= 2) {
            return stringSplit[1].equals("xlsx");
        }
        return false;
    }
}
