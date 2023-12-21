package com.clinic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JComboBox<String> accountTypeCombo;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public void run() {
        frame = new JFrame("Clinic Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(4, 2));

        JLabel accountTypeLabel = new JLabel("Account Type:");
        panel.add(accountTypeLabel);

        accountTypeCombo = new JComboBox<>(new String[]{"Admin", "Doctor", "Receptionist"});
        panel.add(accountTypeCombo);

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String accountType = accountTypeCombo.getSelectedItem().toString();

                if (accountType.equals("Admin")) {
                    if (checkCredentials("C:\\Users\\menna\\Desktop\\admin.txt", username, password)) {
                        openAdminPanel();
                    }
                    else if (username.isEmpty() || password.isEmpty()) {           // Check if both username and password are entered
                        JOptionPane.showMessageDialog(frame, "Please complete all fields.");
                        return;
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials!");
                    }
                } else if (accountType.equals("Doctor")) {
                    if (checkCredentials("C:\\Users\\menna\\Desktop\\doctor.txt", username, password)) {
                        openDoctorPanel();
                    }
                    else if (username.isEmpty() || password.isEmpty()) {           // Check if both username and password are entered
                        JOptionPane.showMessageDialog(frame, "Please complete all fields.");
                        return;
                    }else{
                        JOptionPane.showMessageDialog(frame, "Invalid credentials!");
                    }
                } else if (accountType.equals("Receptionist")) {
                    if (checkCredentials("C:\\Users\\menna\\Desktop\\receptionists.txt", username, password)) {
                        openReceptionistPanel();
                    }
                    else if (username.isEmpty() || password.isEmpty()) {           // Check if both username and password are entered
                        JOptionPane.showMessageDialog(frame, "Please complete all fields.");
                        return;
                    }else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials!");
                    }
                }
            }
        });
    }

    public static boolean checkCredentials(String filePath, String username, String password) {
        try {

            List<String> credentials = Files.readAllLines(Paths.get(filePath));

            for (String credential : credentials) {
                String[] parts = credential.split(",");

                // Check if the array has at least two parts
                if (parts.length >= 2) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[1].trim();

                    // Use equals method to compare passwords
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
                // Handle cases where a line doesn't have the expected format
            }
        } catch (IOException e) {
            // Handle the exception, e.g., log it or show an appropriate message to the user
            e.printStackTrace();
        }
        return false;
    }
    private void openAdminPanel() {
        frame.dispose();
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.run();
    }

    private void openDoctorPanel() {
        frame.dispose();
        DoctorPanel doctorPanel = new DoctorPanel();
        doctorPanel.run();
    }

    private void openReceptionistPanel() {
        frame.dispose();
        ReceptionistPanel receptionistPanel = new ReceptionistPanel();
        receptionistPanel.run();
}
}