package com.clinic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;


public class AdminPanel  {
        private JFrame frame;
        private JPanel currentPanel;

        public void run() {
            frame = new JFrame("Admin Panel");  //Designing admin panel to choose what to do
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            currentPanel = new JPanel();
            frame.add(currentPanel);
            placeComponents(currentPanel);

            frame.setVisible(true);
        }


        private void placeComponents(JPanel panel) {  //loading the design with its components
            panel.setLayout(new GridLayout(4, 1));

            JLabel welcomeLabel = new JLabel("Welcome, Admin!");
            panel.add(welcomeLabel);

            JButton manageDatabaseButton = new JButton("Manage Database");
            panel.add(manageDatabaseButton);
            manageDatabaseButton.addActionListener(new ActionListener() { //set db button on listener , lw etdas 3leh ybd2 y3ml el action dh
                @Override
                public void actionPerformed(ActionEvent e) {
                    openManageDatabasePanel();  // load database managing page
                }
            });

      /*  JButton manageSystemComponentsButton = new JButton("Manage System Components");
        panel.add(manageSystemComponentsButton);
        manageSystemComponentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageSystemComponentsPanel();
            }
        });
*/
            JButton logoutButton = new JButton("Logout");  // set logout on click listener , and user chooses
            panel.add(logoutButton);
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logout();
                }
            });
        }

        private void openManageDatabasePanel() { //opening db managing page
            currentPanel.setVisible(false); //disable current page
            JPanel manageDatabasePanel = new JPanel();
            frame.add(manageDatabasePanel);
            placeManageDatabaseComponents(manageDatabasePanel);  //designing panel
            manageDatabasePanel.setVisible(true);
            currentPanel = manageDatabasePanel; //khlas h-disply el panel elly 3mltha
        }

        private void placeManageDatabaseComponents(JPanel panel) {
            panel.setLayout(new GridLayout(5, 1));

            JLabel titleLabel = new JLabel("Manage User Accounts");
            panel.add(titleLabel);

            JButton addAccountButton = new JButton("Add Account");
            panel.add(addAccountButton);
            addAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openAddAccountPanel();
                }
            });

        JButton editAccountButton = new JButton("Edit Account");
        panel.add(editAccountButton);
        editAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditAccountPanel();
            }
        });

        JButton deleteAccountButton = new JButton("Delete Account");
        panel.add(deleteAccountButton);
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteAccountPanel();
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
    private void openAddAccountPanel() {  //same as we explained , creating new panel for adding account
        currentPanel.setVisible(false);
        JPanel addAccountPanel = new JPanel();
        frame.add(addAccountPanel);
        placeAddAccountComponents(addAccountPanel);
        addAccountPanel.setVisible(true);
        currentPanel = addAccountPanel;
    }
    private void placeAddAccountComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Add Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel roleLabel = new JLabel("Select Role:");  //list of choices to select role to add account to
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(roleLabel, gbc);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Doctor", "Receptionist"}); //choises displayed to the user
        gbc.gridx = 1;
        panel.add(roleCombo, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();   //field to take username input from user
        usernameField.setPreferredSize(new Dimension(200, 25));  // Adjust the width and height as needed
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Make the username field wider
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();   //same for password
        passwordField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Make the password field wider
        panel.add(passwordField, gbc);

        JButton addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = roleCombo.getSelectedItem().toString();   //formatting inputs to strings to store it in file
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());


                if (username.isEmpty() || password.isEmpty()) {           // Check if both username and password are entered
                    JOptionPane.showMessageDialog(frame, "Please complete all fields.");
                    return;
                }

                // Determine the file path based on the selected role
                String filePath;
                if (role.equals("Doctor")) {
                    filePath = "C:\\Users\\menna\\Desktop\\doctor.txt";
                } else if (role.equals("Receptionist")) {
                    filePath = "C:\\Users\\menna\\Desktop\\receptionist.txt";
                } else {
                    return;
                }

                // calling addaccount method to add the new account to the file
                addAccountToFile(filePath, username, password);

                // Go back to the previous panel
                goBack();
            }
        });

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }
    public  void addAccountToFile(String filePath, String username, String password) {
        try {
            //Check if the account already exists
            if (accountExists(filePath, username, password)) {

                JOptionPane.showMessageDialog(frame, "User already exists.");

            } else {
                // If the account doesn't exist, append it to the file
                Files.write(Paths.get(filePath), (username + " ," + password + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                JOptionPane.showMessageDialog(frame, "User saved successfully.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void openEditAccountPanel() {
        currentPanel.setVisible(false);
        JPanel editAccountPanel = new JPanel();
        frame.add(editAccountPanel);
        placeEditAccountComponents(editAccountPanel);
        editAccountPanel.setVisible(true);
        currentPanel = editAccountPanel;     
    }

    private void placeEditAccountComponents(JPanel editAccountPanel) {

        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");
        editAccountPanel.setLayout(new CardLayout());  // setting layout

        // First card: Choose role and enter credentials
        JPanel card1 = new JPanel(new GridLayout(5, 1));

        JLabel titleLabel1 = new JLabel("Edit Account - Step 1");
        card1.add(titleLabel1);

        JLabel roleLabel = new JLabel("Choose the role of the account (Doctor/Receptionist):");
        card1.add(roleLabel);

        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Doctor", "Receptionist"});
        card1.add(roleComboBox);

        //requesting username and password for the account to be modified
        JLabel usernameLabel = new JLabel("Enter the username:");
        card1.add(usernameLabel);

        JTextField usernameField = new JTextField();
        card1.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter the password:");
        card1.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        card1.add(passwordField);

        JButton nextButton = new JButton("Next"); //navigates us to card2
        card1.add(nextButton);

        JButton backButtonCard1 = new JButton("Back"); //navigates us to manage db page
        card1.add(backButtonCard1);

        // Second card: Choose what to modify and enter new value
        JPanel card2 = new JPanel(new GridLayout(7, 1));

        JLabel titleLabel2 = new JLabel("Edit Account - Step 2");
        card2.add(titleLabel2);

        JLabel editLabel = new JLabel("Choose what to modify:");
        card2.add(editLabel);

        // choosing exactly what field to modify
        String[] editOptions = {"Username", "Password", "Both"};
        JComboBox<String> editComboBox = new JComboBox<>(editOptions);
        card2.add(editComboBox);

        // Fields for entering new username and/or new password
        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();



        editComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove existing components
                card2.removeAll();

                // Show appropriate fields based on user choice
                String editChoice = editComboBox.getSelectedItem().toString();

                card2.add(titleLabel2);
                card2.add(editLabel);
                card2.add(editComboBox);

                if (editChoice.equals("Username")) {
                    card2.add(new JLabel("Enter the new username:"));
                    card2.add(newUsernameField);
                } else if (editChoice.equals("Password")) {
                    card2.add(new JLabel("Enter the new password:"));
                    card2.add(newPasswordField);
                } else if (editChoice.equals("Both")) {
                    card2.add(new JLabel("Enter the new username:"));
                    card2.add(newUsernameField);
                    card2.add(new JLabel("Enter the new password:"));
                    card2.add(newPasswordField);
                }

                card2.add(saveButton);
                card2.add(backButton);

                // Repaint the panel to reflect changes
                card2.revalidate();
                card2.repaint();
            }
        });


        // CardLayout to switch between the two cards
        CardLayout cardLayout = (CardLayout) editAccountPanel.getLayout();
        editAccountPanel.add(card1, "Card 1");
        editAccountPanel.add(card2, "Card 2");

        // Initial card shown is card1 , y3ni awl ma a call el panel , el basic hwa card1 elly ytl3
        cardLayout.show(editAccountPanel, "Card 1");

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check credentials and switch to card2
                String role = roleComboBox.getSelectedItem().toString().toLowerCase();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (GUI.checkCredentials("C:\\Users\\menna\\Desktop\\" + role + ".txt", username, password)) {  //checking of credentials are right and account exists
                    cardLayout.show(editAccountPanel, "Card 2"); //navigating to card2
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials so account not found");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = roleComboBox.getSelectedItem().toString().toLowerCase();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                // Save modifications based on user choice

                String editChoice = editComboBox.getSelectedItem().toString(); //what field to modify
                String newUsername = newUsernameField.getText().trim();
                String newPassword = new String(newPasswordField.getPassword()).trim();

                String filePath = "C:\\Users\\menna\\Desktop\\" + role + ".txt"; //determining which file to be modified in after choosing role

                if (editChoice.equals("Username")) {
                    editAccount(filePath, username, password, newUsername, null);
                } else if (editChoice.equals("Password")) {
                    editAccount(filePath, username, password, null, newPassword);
                } else if (editChoice.equals("Both")) {
                    editAccount(filePath, username, password, newUsername, newPassword);
                }
                goBack();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to card1
                cardLayout.show(editAccountPanel, "Card 1");
            }
        });
        backButtonCard1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to card1
                goBack();
            }
        });
    }

    private void editAccount(String s, String username, String password, String newUsername, String newPassword) {
        try {
            Path path = Paths.get(s);
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path)); //array list to store all files lines(data)

            Iterator<String> iterator = lines.iterator(); //iterator to iterate every line , bymshy m3ah line by line
            while (iterator.hasNext()) {
                String line = iterator.next(); // current iterator
                String[] parts = line.split(","); //split line values abl el comma w b3d el comma 3shan y3rf y check
                // checking
                String storedUsername = parts[0].trim();
                String storedPassword = parts[1].trim();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    iterator.remove(); // Remove the line from the list

                    // Add the edited account information to the list
                    if (newUsername != null && newPassword != null) {
                        lines.add(newUsername + ", " + newPassword);
                    } else if (newUsername != null) {
                        lines.add(newUsername + ", " + storedPassword);
                    } else if (newPassword != null) {
                        lines.add(storedUsername + ", " + newPassword);
                    }

                    Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING); // Write the updated list back to the file
                    JOptionPane.showMessageDialog(frame, "Data has been updated successfully.");
                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Account not found. Edit failed."); //mfesh ay line by match

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void openDeleteAccountPanel() {
        currentPanel.setVisible(false);
        JPanel deleteAccountPanel = new JPanel();
        frame.add(deleteAccountPanel);
        placeDeleteAccountComponents(deleteAccountPanel);
        deleteAccountPanel.setVisible(true);
        currentPanel = deleteAccountPanel;
    }

    private void placeDeleteAccountComponents(JPanel deleteAccountPanel) {
        deleteAccountPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Delete Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        deleteAccountPanel.add(titleLabel, gbc);

        JLabel roleLabel = new JLabel("Select Role:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        deleteAccountPanel.add(roleLabel, gbc);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Doctor", "Receptionist"});
        gbc.gridx = 1;
        deleteAccountPanel.add(roleCombo, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        deleteAccountPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Make the username field wider
        deleteAccountPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        deleteAccountPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Make the password field wider
        deleteAccountPanel.add(passwordField, gbc);

        JButton deleteButton = new JButton("Delete");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        deleteAccountPanel.add(deleteButton, gbc);

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        deleteAccountPanel.add(backButton, gbc);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = roleCombo.getSelectedItem().toString();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if both username and password are entered
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please complete all fields.");
                    return;
                }

                // Determine the file path based on the selected role
                String filePath = "";
                if (role.equals("Doctor")) {
                    filePath = "C:\\Users\\menna\\Desktop\\doctor.txt";
                } else if (role.equals("Receptionist")) {
                    filePath = "C:\\Users\\menna\\Desktop\\receptionist.txt";
                } else {
                    return;
                }

                // Check if the account exists
                if (GUI.checkCredentials(filePath, username, password)) {
                    // Delete the account from the file
                    deleteAccountFromFile(filePath, username, password);


                }
                else {
                    JOptionPane.showMessageDialog(frame, "Account not found. Deletion failed.");
                }


                // Go back to the previous panel
                goBack();
            }
        });




        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void deleteAccountFromFile(String filePath, String username, String password) {
        try {
            Path path = Paths.get(filePath);
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path));
            Iterator<String> iterator = lines.iterator();
            boolean accountFound = false;
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split(",");
                String storedUsername = parts[0].trim();
                String storedPassword = parts[1].trim();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    iterator.remove();  //same iterator functionality

                    // Step 11: Display a success message
                    JOptionPane.showMessageDialog(frame, "Account is deleted successfully.");

                    // Step 12: Break out of the loop since the account has been found
                    break;
                }

            }

            // Step 13: Write the updated list back to the file, truncating existing content
            Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);

            // Step 14: Display a message if the account was not found


        } catch (IOException e) {
            // Step 15: Handle IOException by printing the stack trace
            e.printStackTrace();
        }
    }








   /* private void openManageSystemComponentsPanel() {
        currentPanel.setVisible(false);
        JPanel manageSystemComponentsPanel = new JPanel();
        frame.add(manageSystemComponentsPanel);
        placeManageSystemComponentsComponents(manageSystemComponentsPanel);
        manageSystemComponentsPanel.setVisible(true);
        currentPanel = manageSystemComponentsPanel;
    }

    private void placeManageSystemComponentsComponents(JPanel panel) {
        panel.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Manage System Components");
        panel.add(titleLabel);

        // Add components and logic for managing system components

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }
*/


    private boolean accountExists(String filePath, String username, String password) {
        try {
            // Read all lines from the file
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filePath));

            // Iterate through the lines and check if the account exists
            for (String line : lines) {
                String[] parts = line.split(",");
                String storedUsername = parts[0].trim();
                String storedPassword = parts[1].trim();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true; // Account exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Account not found
    }

        private void logout() {
            currentPanel.setVisible(false);
            GUI gui = new GUI();
            gui.run();
        }

        private void goBack() {
            currentPanel.setVisible(false);
            frame.remove(currentPanel);
            currentPanel = new JPanel(); //removing current panel and navigating to the last panel
            frame.add(currentPanel);
            placeComponents(currentPanel);
            currentPanel.setVisible(true);
        }
    }


