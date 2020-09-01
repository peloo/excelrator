package com.gft;

import com.gft.integrations.Reader;
import com.gft.integrations.Writer;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Excelrator extends Exception{
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("|================================[START]================================|");
        System.out.println("| Hi, this script converts an excel file given in input in txt files.   |");
        System.out.println("| The script ignore the firs sheet of excel file, ignore the first row  |");
        System.out.println("| of every sheet and get the name of txt file output from the first row |");
        System.out.println("| of the first column.                                                  |");
        System.out.println("|================================[START]================================|");

        System.out.print("Enter the path with name where locate the excel file: ");
        String readFromPath = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\/" + ".*" + "\\.");
        Matcher matcher = pattern.matcher(readFromPath);
        if (!matcher.find()) {
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
            readFromPath = readFromPath + "/" + fileName;
        }

        Map<String, String> textRead = Reader.read(readFromPath);
        if (textRead != null && !textRead.isEmpty()) {
            System.out.print("Pease enter the path where save the txt files: ");
            final String writeInPath = scanner.nextLine();

            textRead.forEach((key, value) -> {
                key = key + ".txt";
                Writer.checkPath(writeInPath, key);
                if (Writer.write(writeInPath + "/" + key, value)) {
                    System.out.println("File " + key + " write in path " + writeInPath);
                } else {
                    System.out.println("Impossible to write file " + key + " write in path " + writeInPath);
                }
            });
        }

        System.out.println("|=================================[END]=================================|");
    }
}
