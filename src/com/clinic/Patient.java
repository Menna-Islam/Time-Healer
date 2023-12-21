package com.clinic;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Patient {
    private static final String FILE_PATH = "C:\\Users\\menna\\Desktop\\patient.txt";

    private String name;
    private int age;
    private String gender;
    private String address;

    public Patient(String name, int age, String gender, String address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public static void addPatient(Patient patient) {
        try (FileWriter writer = new FileWriter(FILE_PATH + "patients.txt", true)) {
            writer.write(patient.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removePatient(String patientName) {
        try {
            List<String> patients = Files.readAllLines(Paths.get(FILE_PATH + "patients.txt"));
            if (patients.isEmpty()) {
                System.out.println("No patients found in the database.");
            } else {
                FileWriter writer = new FileWriter(FILE_PATH + "patients.txt");

                for (String patient : patients) {
                    String[] parts = patient.split(",");
                    String name = parts[0].trim();
                    if (!name.equalsIgnoreCase(patientName)) {
                        writer.write(patient + "\n");
                    }
                }

                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPatients() {
        try {
            List<String> patients = Files.readAllLines(Paths.get(FILE_PATH + "patients.txt"));
            if (patients.isEmpty()) {
                System.out.println("No patients found in the database.");
            } else {
                for (String patient : patients) {
                    System.out.println(patient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name + "," + age + "," + gender + "," + address;
}
}