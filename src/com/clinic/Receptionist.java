package com.clinic;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

 public class Receptionist {
     private static final String FILE_PATH = "C:\\Users\\menna\\Desktop\\receptionist.txt";

     public static void registerPatient(String patientInfo) {
         try (FileWriter writer = new FileWriter(FILE_PATH + "patients.txt", true)) {
             writer.write(patientInfo + "\n");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public static void scheduleAppointment(String appointmentDetails) {
         try {
             List<String> appointments = Files.readAllLines(Paths.get(FILE_PATH + "appointments.txt"));
             if (appointments.isEmpty()) {
                 try (FileWriter writer = new FileWriter(FILE_PATH + "appointments.txt", true)) {
                     writer.write(appointmentDetails + "\n");
                     JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             } else {
                 for (String appointment : appointments) {
                     if (appointment.equals(appointmentDetails)) {
                         JOptionPane.showMessageDialog(null, "Appointment already booked!");
                         return;
                     }
                 }
                 try (FileWriter writer = new FileWriter(FILE_PATH + "appointments.txt", true)) {
                     writer.write(appointmentDetails + "\n");
                     JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }