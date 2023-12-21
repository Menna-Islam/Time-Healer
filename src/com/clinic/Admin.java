package com.clinic;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Admin {

    private static final String FILE_PATH = "C:\\Users\\menna\\Desktop\\admin.txt";

    public static void addDoctor(String username, String password) {
        addAccountToFile("doctors.txt", username, password);
    }

    public static void addReceptionist(String username, String password) {
        addAccountToFile("receptionists.txt", username, password);
    }

    public static void modifyDatabase(String filename, String data) {
        modifyAccount(filename, data);
    }

    public static void accessDatabase(String filename) {
        accessAccount(filename);
    }

    private static void addAccountToFile(String filename, String username, String password) {
        String filePath = FILE_PATH + filename;

    }

    private static void modifyAccount(String filename, String data) {
        String filePath = FILE_PATH + filename;

    }

    private static void accessAccount(String filename) {
        String filePath = FILE_PATH + filename;

    }
}
