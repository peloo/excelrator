package com.gft.integrations;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;

public class Writer {

    public static boolean write(@NotNull String name, @NotNull String text) {
        boolean out = false;
        try {
            FileWriter fileWriter = new FileWriter(name);
            fileWriter.write(text);
            fileWriter.close();
            out = true;
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return out;
    }

    public static boolean checkPath(@NotNull String path, @NotNull String fileName) {
        boolean out = false;
        try {
            File pathToCheck = new File(path);
            File fileToCheck = new File(path + "/" + fileName);
            if (pathToCheck.exists()) {
                System.out.println("Path exists");
                if (fileToCheck.exists()) {
                    System.out.println("File already exists.");
                    if (deleteFile(path + "/" + fileName)) {
                        System.out.println("File deleted successfully");
                    } else {
                        System.out.println("Failed to delete the file");
                    }
                }
                out = true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return out;
    }

    private static boolean deleteFile(@NotNull String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}
