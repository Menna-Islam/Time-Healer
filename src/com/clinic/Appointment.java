package com.clinic;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

 public class Appointment {
     private static final String FILE_PATH = "C:\\Users\\menna\\Desktop\\appointment.txt";

     private String doctorName;
     private String patientName;
     private String dateTime;

     public Appointment(String doctorName, String patientName, String dateTime) {
         this.doctorName = doctorName;
         this.patientName = patientName;
         this.dateTime = dateTime;
     }

     public static void scheduleAppointment(Appointment appointment) {
         try {
             List<String> appointments = Files.readAllLines(Paths.get(FILE_PATH + "appointments.txt"));
             if (appointments.isEmpty()) {
                 try (FileWriter writer = new FileWriter(FILE_PATH + "appointments.txt", true)) {
                     writer.write(appointment.toString() + "\n");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             } else {
                 for (String existingAppointment : appointments) {
                     if (existingAppointment.equals(appointment.toString())) {
                         System.out.println("Appointment already booked!");
                         return;
                     }
                 }
                 try (FileWriter writer = new FileWriter(FILE_PATH + "appointments.txt", true)) {
                     writer.write(appointment.toString() + "\n");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public static void showAppointments() {
         try {
             List<String> appointments = Files.readAllLines(Paths.get(FILE_PATH + "appointments.txt"));
             if (appointments.isEmpty()) {
                 System.out.println("No appointments found in the database.");
             } else {
                 for (String appointment : appointments) {
                     System.out.println(appointment);
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     @Override
     public String toString() {
         return doctorName + "," + patientName + "," + dateTime;
     }


 }