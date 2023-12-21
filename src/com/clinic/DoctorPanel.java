package com.clinic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DoctorPanel {
    private JFrame frame;
    private JPanel currentPanel;
    private String appointmentsFilePath = "C:\\Users\\menna\\Desktop\\appointments.txt";
    private JComboBox<String> patientsDropdown;
    private JTable patientsTable;

    public void run() {
        frame = new JFrame("Doctor Panel");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentPanel = new JPanel();
        frame.add(currentPanel);
        placeComponents(currentPanel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Welcome, Doctor!");
        panel.add(welcomeLabel);

        JButton viewAppointmentsButton = new JButton("View Appointments");
        panel.add(viewAppointmentsButton);
        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAppointments();
            }
        });

        JButton viewMedicalRecordsButton = new JButton("View Medical Records");
        panel.add(viewMedicalRecordsButton);
        viewMedicalRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewMedicalRecordsPanel();
            }
        });

        JButton manageMedicalRecordsButton = new JButton("Manage Medical Records");
        panel.add(manageMedicalRecordsButton);
        manageMedicalRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageMedicalRecordsPanel();
            }
        });

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void openViewMedicalRecordsPanel() {
        currentPanel.setVisible(false);
        JPanel viewMedicalRecordsPanel = new JPanel();
        frame.add(viewMedicalRecordsPanel);
        placeViewMedicalRecordsComponents(viewMedicalRecordsPanel);
        viewMedicalRecordsPanel.setVisible(true);
        currentPanel = viewMedicalRecordsPanel;
    }

    private void placeViewMedicalRecordsComponents(JPanel panel) {
        panel.setLayout(new GridLayout(3, 1));

        // Dropdown to select a patient
        patientsDropdown = new JComboBox<>(getPatientsList().toArray(new String[0]));
        panel.add(patientsDropdown, BorderLayout.NORTH);

        JButton viewRecordButton = new JButton("View Medical Record");
        panel.add(viewRecordButton);
        viewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMedicalRecord();
            }
        });

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }


    private void viewMedicalRecord() {
        String selectedPatient = (String) patientsDropdown.getSelectedItem();

        if (selectedPatient != null) {
            String medicalRecordPath = "C:\\Users\\menna\\Desktop\\medical records.txt";

            try {
                List<String> lines = Files.readAllLines(Paths.get(medicalRecordPath));

                StringBuilder medicalRecord = new StringBuilder();
                boolean patientFound = false;

                for (String line : lines) {
                    if (line.startsWith("Patient: " + selectedPatient)) {
                        patientFound = true;
                        medicalRecord.append(line).append("\n");
                    } else if (patientFound && line.startsWith("Patient: ")) {
                        // Stop reading when the word "Patient:" is encountered for the next patient
                        break;
                    } else if (patientFound) {
                        // Append lines for the selected patient
                        medicalRecord.append(line).append("\n");
                    }
                }

                if (medicalRecord.length() > 0) {
                    JTextArea textArea = new JTextArea(medicalRecord.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JOptionPane.showMessageDialog(frame, scrollPane, "Medical Record for " + selectedPatient, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Medical record not found for " + selectedPatient, "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error reading medical record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }





    private List<String> getPatientsList() {
        List<String> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\menna\\Desktop\\Patients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                patients.add(data[0] + " " + data[1]); // Assuming the patient's name is in the first two columns
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading patients data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return patients;
    }

    private void openManageAppointmentsPanel() {
        currentPanel.setVisible(false);
        JPanel manageAppointmentsPanel = new JPanel();
        frame.add(manageAppointmentsPanel);
        placeManageAppointmentsComponents(manageAppointmentsPanel);
        manageAppointmentsPanel.setVisible(true);
        currentPanel = manageAppointmentsPanel;
    }

    private void placeManageAppointmentsComponents(JPanel panel) {
        JComboBox<String> patientsDropdown = new JComboBox<>(getPatientsList().toArray(new String[0]));
        panel.add(patientsDropdown, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(3, 1));
        JButton viewAppointmentsButton = new JButton("View Appointments");
        panel.add(viewAppointmentsButton);
        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAppointments();
            }
        });

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void openManageMedicalRecordsPanel() {
        currentPanel.setVisible(false);
        JPanel manageMedicalRecordsPanel = new JPanel();
        frame.add(manageMedicalRecordsPanel);
        placeManageMedicalRecordsComponents(manageMedicalRecordsPanel);
        manageMedicalRecordsPanel.setVisible(true);
        currentPanel = manageMedicalRecordsPanel;
    }

    private void placeManageMedicalRecordsComponents(JPanel panel) {
        panel.setLayout(new GridLayout(3, 1));

        JComboBox<String> patientsDropdown = new JComboBox<>(getPatientsList().toArray(new String[0]));
        panel.add(patientsDropdown, BorderLayout.NORTH);

        JButton editMedicalRecordButton = new JButton("Edit Medical Record");
        panel.add(editMedicalRecordButton);
        editMedicalRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMedicalRecord(patientsDropdown);
            }
        });

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }


    private JTable createPatientsTable() {
        String[] columnNames = {"First Name", "Last Name", "Age", "Address", "Phone", "Gender", "Blood Type"}; // Add "Blood Type"
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        return table;
    }

    private void loadAppointmentsData(DefaultTableModel model) {
        model.setColumnIdentifiers(new Object[]{"Date", "Time", "Patient Name"}); // Set column names
        model.setRowCount(0); // Clear existing data

        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading appointments data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAppointments() {
        currentPanel.setVisible(false);
        JPanel appointmentsPanel = new JPanel(new BorderLayout());
        frame.add(appointmentsPanel);

        JLabel titleLabel = new JLabel("Appointments");
        appointmentsPanel.add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel appointmentsTableModel = new DefaultTableModel();
        JTable appointmentsTable = new JTable(appointmentsTableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        appointmentsPanel.add(scrollPane, BorderLayout.CENTER);

        loadAppointmentsData(appointmentsTableModel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        appointmentsPanel.add(buttonPanel, BorderLayout.SOUTH);

        appointmentsPanel.setVisible(true);
        currentPanel = appointmentsPanel;
    }

    private void editMedicalRecord(JComboBox<String> dropdown) {
        String selectedPatient = (String) dropdown.getSelectedItem();

        if (selectedPatient != null) {
            // Retrieve patient information
            String medicalRecordPath = "C:\\Users\\menna\\Desktop\\medical records.txt";

            try {
                List<String> lines = Files.readAllLines(Paths.get(medicalRecordPath));

                StringBuilder medicalRecord = new StringBuilder();
                boolean patientFound = false;

                for (String line : lines) {
                    if (line.startsWith("Patient: " + selectedPatient)) {
                        patientFound = true;
                        medicalRecord.append(line).append("\n");
                    } else if (patientFound && line.startsWith("Patient: ")) {
                        // Stop reading when the word "Patient:" is encountered for the next patient
                        break;
                    } else if (patientFound) {
                        // Append lines for the selected patient
                        medicalRecord.append(line).append("\n");
                    }
                }

                if (medicalRecord.length() > 0) {
                    JTextArea textArea = new JTextArea(medicalRecord.toString());
                    textArea.setEditable(true);  // Set to editable

                    // Use JScrollPane to make the text area scrollable
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    int result = JOptionPane.showConfirmDialog(frame, scrollPane, "Edit Medical Record", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        // Perform the edit/update logic here if needed

                        // Update the medical record in the file if it is editable
                        if (textArea.isEditable()) {
                            List<String> updatedLines = new ArrayList<>(lines);
                            int index = -1;

                            // Find the starting index of the selected patient's medical record
                            for (int i = 0; i < updatedLines.size(); i++) {
                                if (updatedLines.get(i).startsWith("Patient: " + selectedPatient)) {
                                    index = i;
                                    break;
                                }
                            }

                            if (index != -1) {
                                // Remove the old medical record for the selected patient
                                while (index < updatedLines.size() && !updatedLines.get(index).startsWith("Patient: ")) {
                                    updatedLines.remove(index);
                                }

                                // Add the new medical record for the selected patient
                                updatedLines.add(index, textArea.getText());

                                // Write the updated content back to the file
                                Files.write(Paths.get(medicalRecordPath), updatedLines);

                                JOptionPane.showMessageDialog(frame, "Medical record edited successfully for " + selectedPatient, "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Medical record not found for " + selectedPatient, "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error reading medical record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }











    private void removeDuplicateLines(String filePath) {
        try {
            // Read the existing content of the file
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Create a new list to hold the unique content
            List<String> uniqueLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.trim().isEmpty() && !uniqueLines.contains(line)) {
                    uniqueLines.add(line);
                }
            }

            // Write the unique lines back to the file
            Files.write(Paths.get(filePath), String.join(System.lineSeparator(), uniqueLines).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error removing duplicate lines.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultTableModel loadMedicalRecordsData(DefaultTableModel model) {
        model.setColumnIdentifiers(new Object[]{"Patient", "Age", "Address", "Phone", "Gender", "Blood Type"}); // Set column names
        model.setRowCount(0); // Clear existing data

        String medicalRecordPath = "C:\\Users\\menna\\Desktop\\medical records.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(medicalRecordPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Patient: ")) {
                    // Extract patient name from the "Patient: " line
                    String patientName = line.substring(9).trim();

                    // Read additional lines to get Age, Address, Phone, Gender, Blood Type
                    String age = readValue(reader.readLine());
                    String address = readValue(reader.readLine());
                    String phone = readValue(reader.readLine());
                    String gender = readValue(reader.readLine());
                    String bloodType = readValue(reader.readLine());

                    model.addRow(new Object[]{patientName, age, address, phone, gender, bloodType});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading medical records data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return model;
    }

    // Helper method to extract values (e.g., Age, Address, Phone) from lines
    private String readValue(String line) {
        return line.substring(line.indexOf(":") + 1).trim();
    }



    private void logout() {
        currentPanel.setVisible(false);
        GUI gui = new GUI();
        gui.run();
    }

    private void goBack() {
        currentPanel.setVisible(false);
        frame.remove(currentPanel);
        currentPanel = new JPanel();
        frame.add(currentPanel);
        placeComponents(currentPanel);
        currentPanel.setVisible(true);
    }
}