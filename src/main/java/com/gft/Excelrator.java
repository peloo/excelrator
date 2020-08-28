package com.gft;

import com.gft.integrations.Reader;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Excelrator extends Exception{
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("|================[START]================|");
        System.out.println("Hi, this script converts an excel file given in input in txt files.");
        System.out.print("Enter the path with name where locate the excel file: ");
        String path = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\/" + ".*" + "\\.");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            /*
             * The path contain the file name and the extension
             * Go to method tha convert excel to txt
             */
            Reader.read(path);
        } else {
            /*
             * The path not contain the file name and the extension
             * Require the name from input
             */
            System.out.print("Enter the name and extension of file: ");
            String fileName = scanner.nextLine();

            String[] stringSplit = fileName.split("\\.");

            if (stringSplit.length < 2) {
                do {
                    System.out.print("Pease enter the CORRECT name and extension of file: ");
                    fileName = scanner.nextLine();
                    stringSplit = fileName.split("\\.");
                } while (stringSplit.length < 2);
            }
            Reader.read(path + "/" + fileName);
        }

        System.out.println("|=================[END]=================|");
    }
}
