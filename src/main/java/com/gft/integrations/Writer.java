package com.gft.integrations;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;

public class Writer {

    public static boolean write(@NotNull String name, @NotNull String text) {
        try {
            FileWriter fileWriter = new FileWriter(name);
            fileWriter.write(text);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean createFile(@NotNull String path, @NotNull String name) {
        boolean out = false;
        try {
            path = path + "/" + name;
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created");
                out = true;
            } else {
                System.out.println("File already exists.");
                if (deleteFile(path)) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
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
