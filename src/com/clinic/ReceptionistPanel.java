
package com.clinic;

        import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
        import javax.swing.table.TableRowSorter;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.*;
        import java.util.ArrayList;
        import java.util.List;
        import javax.swing.RowFilter;

public class ReceptionistPanel {
    private JFrame frame;
    private JPanel currentPanel;
    private JTable patientsTable;  // Declare the patientsTable as a class-level variable
    private String patientsFilePath = "C:\\Users\\menna\\Desktop\\patients.txt";
    private String appointmentsFilePath = "C:\\Users\\menna\\Desktop\\appointments.txt";
    private JTextField searchField;
    private JPanel cards;
    private CardLayout cardLayout;
    private JTextField patientNameField;

    public void run() {
        frame = new JFrame("Receptionist Panel");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentPanel = new JPanel();
        frame.add(currentPanel);
        placeComponents(currentPanel);

        frame.setVisible(true);
    }
    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Welcome, Receptionist!");
        panel.add(welcomeLabel);

        JButton managePatientsButton = new JButton("Manage Patients");
        panel.add(managePatientsButton);
        managePatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManagePatientsPanel();
            }
        });

        JButton scheduleAppointmentsButton = new JButton("Schedule Appointments");
        panel.add(scheduleAppointmentsButton);
        scheduleAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openScheduleAppointmentsPanel();
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

    private void openManagePatientsPanel() {
        currentPanel.setVisible(false);
        JPanel managePatientsPanel = new JPanel();
        frame.add(managePatientsPanel);
        placeManagePatientsComponents(managePatientsPanel);

        // Initialize the class-level patientsTable
        patientsTable = createPatientsTable();

        JScrollPane scrollPane = new JScrollPane(patientsTable);
        managePatientsPanel.add(scrollPane, BorderLayout.CENTER);

        loadPatientsData((DefaultTableModel) patientsTable.getModel());

        managePatientsPanel.setVisible(true);
        currentPanel = managePatientsPanel;
    }




    private void placeManagePatientsComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Patients");
        panel.add(titleLabel, BorderLayout.NORTH);

        // Search bar
        searchField = new JTextField();
        panel.add(searchField, BorderLayout.SOUTH);

        // Table for displaying patients
        JTable patientsTable = createPatientsTable();
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        JButton createMedicalRecordButton = new JButton("Create Medical Record");
        buttonPanel.add(createMedicalRecordButton);
        createMedicalRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMedicalRecord();
            }
        });
        JButton addPatientButton = new JButton("Add Patient");
        buttonPanel.add(addPatientButton);
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
                refreshTable(patientsTable);
            }
        });

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JButton deletePatientButton = new JButton("Delete Patient");
        buttonPanel.add(deletePatientButton);
        deletePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient(patientsTable);
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Search functionality
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                filterTable(patientsTable, searchTerm);
            }
        });
    }

    private void createMedicalRecord() {
        int selectedRow = patientsTable.getSelectedRow();
        if (selectedRow != -1) {
            String firstName = (String) patientsTable.getValueAt(selectedRow, 0);
            String lastName = (String) patientsTable.getValueAt(selectedRow, 1);
            String patientName = firstName + " " + lastName;

            // Show confirmation dialog
            int confirmDialogResult = JOptionPane.showConfirmDialog(frame, "Create medical record for " + patientName + "?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                // Retrieve patient information
                String age = (String) patientsTable.getValueAt(selectedRow, 2);
                String address = (String) patientsTable.getValueAt(selectedRow, 3);
                String phone = (String) patientsTable.getValueAt(selectedRow, 4);
                String gender = (String) patientsTable.getValueAt(selectedRow, 5);
                String bloodType = (String) patientsTable.getValueAt(selectedRow, 6);

                // Create or update medical record
                String medicalRecordPath = "C:\\Users\\menna\\Desktop\\medical records.txt";
                String medicalRecordContent = String.format("Patient: %s\nAge: %s\nAddress: %s\nPhone: %s\nGender: %s\nBlood Type: %s",
                        patientName, age, address, phone, gender, bloodType);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(medicalRecordPath, true))) {
                    writer.write("\n\n" + medicalRecordContent); // Append a newline and the new record
                    writer.flush();
                    JOptionPane.showMessageDialog(frame, "Medical record created successfully for " + patientName, "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error creating medical record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private JTable createPatientsTable() {
        String[] columnNames = {"First Name", "Last Name", "Age", "Address", "Phone", "Gender", "Blood Type"}; // Add "Blood Type"
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        return table;
    }


    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data
        loadPatientsData(model);
    }

    private void loadPatientsData(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data

        try (BufferedReader reader = new BufferedReader(new FileReader(patientsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading patients data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void showMedicalRecord(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String firstName = (String) table.getValueAt(selectedRow, 0);
            String lastName = (String) table.getValueAt(selectedRow, 1);
            String patientName = firstName + " " + lastName;
            // Implement logic to show medical record for the selected patient
            // You may use JOptionPane to display the medical record or create a new panel for this purpose
            // ...
            JOptionPane.showMessageDialog(frame, "Medical record for " + patientName, "Medical Record", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPatient() {
        // Use a form with text fields for better user input
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();

        String[] genderOptions = {"Male", "Female"};
        JComboBox<String> genderComboBox = new JComboBox<>(genderOptions);

        // Add blood type field
        String[] bloodTypeOptions = {"A", "B", "AB", "O"};
        JComboBox<String> bloodTypeComboBox = new JComboBox<>(bloodTypeOptions);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderComboBox);
        panel.add(new JLabel("Blood Type:"));
        panel.add(bloodTypeComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Retrieve user input and add the patient to the file
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String age = ageField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String bloodType = (String) bloodTypeComboBox.getSelectedItem();

            // Append the new patient to the file
            String patientInfo = String.format("%s, %s, %s, %s, %s, %s, %s", firstName, lastName, age, address, phone, gender, bloodType);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientsFilePath, true))) {
                writer.write(patientInfo + "\n");
                writer.flush();
                JOptionPane.showMessageDialog(frame, "Patient added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error adding patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // If the user clicks "Cancel," do nothing.
    }

    private void editPatient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String firstName = (String) table.getValueAt(selectedRow, 0);
            String lastName = (String) table.getValueAt(selectedRow, 1);
            String patientName = firstName + " " + lastName;
            // Implement logic to edit patient information
            // You may use JOptionPane to gather patient information or create a new panel for this purpose
            // ...
            // Retrieve and edit the patient information based on the patientName
            // ...
            JOptionPane.showMessageDialog(frame, "Editing patient: " + patientName, "Edit Patient", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirmDialogResult = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this patient?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                String firstName = (String) model.getValueAt(selectedRow, 0);
                String lastName = (String) model.getValueAt(selectedRow, 1);
                String patientName = firstName + " " + lastName;

                // Implement logic to delete a patient from the file
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(patientsFilePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.contains(firstName + ", " + lastName)) {
                            lines.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error reading patients data.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientsFilePath))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                    writer.flush();
                    JOptionPane.showMessageDialog(frame, "Patient deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error deleting patient.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                refreshTable(table);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void filterTable(JTable table, String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            filters.add(RowFilter.regexFilter("(?i)" + searchTerm, i));
        }
        sorter.setRowFilter(RowFilter.orFilter(filters));
    }

    private void openScheduleAppointmentsPanel() {
        currentPanel.setVisible(false);
        JPanel scheduleAppointmentsPanel = new JPanel();
        frame.add(scheduleAppointmentsPanel);
        placeScheduleAppointmentsComponents(scheduleAppointmentsPanel);
        scheduleAppointmentsPanel.setVisible(true);
        currentPanel = scheduleAppointmentsPanel;
    }

    private void placeScheduleAppointmentsComponents(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Schedule Appointments");
        panel.add(titleLabel);

        JButton showAppointmentsButton = new JButton("Show Appointments");
        panel.add(showAppointmentsButton);
        showAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointments();
            }
        });

        JButton addAppointmentButton = new JButton("Add Appointment");
        panel.add(addAppointmentButton);
        addAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
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

    private void showAppointments() {
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

        JButton deleteAppointmentButton = new JButton("Delete Appointment");
        buttonPanel.add(deleteAppointmentButton);
        deleteAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointment(appointmentsTable);
            }
        });

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



    // Update deleteAppointment method
    private void deleteAppointment(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirmDialogResult = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this appointment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                String date = (String) model.getValueAt(selectedRow, 0);
                String time = (String) model.getValueAt(selectedRow, 1);
                String patientName = (String) model.getValueAt(selectedRow, 2);

                // Remove the appointment from the file
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(appointmentsFilePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(", ");
                        if (!(data[0].equals(date) && data[1].equals(time) && data[2].equals(patientName))) {
                            lines.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error reading appointments data.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentsFilePath))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                    writer.flush();
                    JOptionPane.showMessageDialog(frame, "Appointment deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error deleting appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                model.removeRow(selectedRow); // Remove from the table
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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


    private void addAppointment() {
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();

        JComboBox<String> patientDropdown = new JComboBox<>(getPatientsList().toArray(new String[0]));
        patientDropdown.insertItemAt("Select Patient", 0); // Add a default option
        patientDropdown.setSelectedIndex(0);
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Time:"));
        panel.add(timeField);
        panel.add(new JLabel("Patient:"));
        panel.add(patientDropdown);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String time = timeField.getText();
            String selectedPatient = (String) patientDropdown.getSelectedItem();

            // Check if "Select Patient" is selected
            if ("Select Patient".equals(selectedPatient)) {
                JOptionPane.showMessageDialog(frame, "Please select a patient or add a new patient.", "Error", JOptionPane.ERROR_MESSAGE);
                // Optionally, navigate to the Manage Patients panel
                openManagePatientsPanel();
                return;
            }

            String appointmentInfo = String.format("%s, %s, %s", date, time, selectedPatient);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentsFilePath, true))) {
                writer.write(appointmentInfo + "\n");
                writer.flush();

                // Call operateBillAfterAppointment immediately after adding the appointment
                operateBillAfterAppointment(selectedPatient);

                JOptionPane.showMessageDialog(frame, "Appointment added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error adding appointment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void operateBillAfterAppointment(String patientName) {
        generateBill(patientName);
        cardLayout.show(cards, "operateBillPanel");
        patientNameField.setText(patientName);
    }
    private void generateBill(String patientName) {
        // Placeholder logic for bill generation
        double billAmount = calculateBill(patientName);
        JOptionPane.showMessageDialog(frame, "Bill for " + patientName + ": $" + billAmount);
    }

    private double calculateBill(String patientName) {
        // Placeholder logic for calculating the bill amount
        // You can replace this with your actual logic
        return 100.0;
    }

    private List<String> getPatientsList() {
        List<String> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(patientsFilePath))) {
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

    public static void main(String[] args) {
        ReceptionistPanel receptionistPanel = new ReceptionistPanel();
        receptionistPanel.run();
}
}