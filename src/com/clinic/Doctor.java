package com.clinic;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

  public class Doctor {
    private static final String FILE_PATH = "C:\\Users\\menna\\Desktop\\doctor.txt";

    public static void searchPatient(String patientName) {
        try {
            List<String> patients = Files.readAllLines(Paths.get(FILE_PATH + "patients.txt"));
            if (patients.isEmpty()) {
                System.out.println("No patients found in the database.");
            } else {
                for (String patient : patients) {
                    String[] parts = patient.split(",");
                    String name = parts[0].trim();
                    if (name.equalsIgnoreCase(patientName)) {
                        System.out.println("Patient found: " + patient);
                        return;
                    }
                }
                System.out.println("Patient not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyMedicalRecord(String patientName, String newRecord) {
        try {
            List<String> patients = Files.readAllLines(Paths.get(FILE_PATH + "patients.txt"));
            if (patients.isEmpty()) {
                System.out.println("No patients found in the database.");
            } else {
                for (int i = 0; i < patients.size(); i++) {
                    String patient = patients.get(i);
                    String[] parts = patient.split(",");
                    String name = parts[0].trim();
                    if (name.equalsIgnoreCase(patientName)) {
                        patients.set(i, name + "," + newRecord);
                        updatePatientDatabase(patients);
                        System.out.println("Medical record updated for: " + name);
                        return;
                    }
                }
                System.out.println("Patient not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updatePatientDatabase(List<String> patients) {
        try (FileWriter writer = new FileWriter(FILE_PATH + "patients.txt")) {
            for (String patient : patients) {
                writer.write(patient + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
 }
}
}
