import javax.swing.*;                                                            //Imported all Swing classes, such as JFrame, JPanel, JButton, JLabel, JTextField, etc.
import java.awt.*;                                                               //All AWT classes are imported, such as Button, Label, Panel, Font, Graphics, etc.
import java.awt.event.*;
import java.io.*;                                                                //Allows you to perform various I/O operations in your Java program
import java.util.Random;                                                         // to random generate worker id prevent duplication

public class LoginForm extends JFrame {
    private JTextField workerIDField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, forgotPasswordButton;
    private JPanel mainPanel;

    public LoginForm() {
        setTitle("Inventory Management System - Login");
        setLayout(new BorderLayout());                                            // to  control the Approximate location of button,etc

                                                                                  // Main panel for login
        mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));                     // to precise control the button,etc
        workerIDField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        forgotPasswordButton = new JButton("Forgot Password");

        mainPanel.add(new JLabel(" Worker ID:"));
        mainPanel.add(workerIDField);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(forgotPasswordButton);

        add(mainPanel, BorderLayout.CENTER);                                       //set pproximate location at the middle of window

        loginButton.addActionListener(new ActionListener() {                       // run login function
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {                      // run register function
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterForm();
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {               // run reset password function
            @Override
            public void actionPerformed(ActionEvent e) {
                handleForgotPassword();
            }
        });

        setSize(800, 600);                                                          //set the size of whole window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                             //close the whole program
        setLocationRelativeTo(null);                                                //set window at the middle of the screen
        setVisible(true);                                                           //display window
        
    }

                                                                                      
    private void handleLogin() {                                                    //  Handle login functionality
        String workerID = workerIDField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user = User.findByWorkerID(workerID);                              // find the worker id from text file
            if (user != null && user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");

                                                                                    // Proceed based on user role
                if (workerID.startsWith("W")) {                                     //if  viewer login the system ,  only can view
                    // Viewer Role
                    SwingUtilities.invokeLater(() -> {
                        ViewItem viewerApp = new ViewItem();        
                        viewerApp.setVisible(true);
                    }); 
                } else if (workerID.startsWith("A")) {                              //if admin login the system, admin can modify the inventory
                    // Admin Role
                	SwingUtilities.invokeLater(() -> {
                        HomePage adminApp = new HomePage(workerID,password);
                       adminApp.setVisible(true);
                    });
                    
                }
                dispose();                                                           // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Worker ID or Password. Please try again."); //error  handling
                workerIDField.setText("");
                passwordField.setText("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   
    private void showRegisterForm() {                                                // Show the register form                                                                                  
        mainPanel.removeAll();                                                       // the register form will cover the login page, Clear the main panel 
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));                           // precise control the design of window

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "viewer"});
        JLabel generatedIDLabel = new JLabel("");
        JButton confirmButton = new JButton("Confirm");
        JButton returnToLoginButton = new JButton("Back");

        mainPanel.add(new JLabel("Email:"));
        mainPanel.add(emailField);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel("Role:"));
        mainPanel.add(roleComboBox);
        mainPanel.add(new JLabel("Generated Worker ID:"));
        mainPanel.add(generatedIDLabel);
        mainPanel.add(confirmButton);
        mainPanel.add(returnToLoginButton);

        revalidate();                                                                  //Mainly used for layout updates and adjustments
        repaint();                                                                     //For refreshing and updating of visual content.

       
        confirmButton.addActionListener(new ActionListener() {                         // Confirm button to generate worker ID and save user
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String role = roleComboBox.getSelectedItem().toString();

                try {
                    if (!email.isEmpty() && !password.isEmpty()) {
                        if (User.findByEmail(email) == null) {                         // Ensure email is unique and wont duplicate
                            String workerID = generateWorkerID(role);
                            generatedIDLabel.setText(workerID);

                            User newUser = new User(workerID, email, password);           
                            newUser.saveToFile();
                            JOptionPane.showMessageDialog(mainPanel, "Registration Successful! Your Worker ID is: " + workerID);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "This email is already registered. Please use a different email.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Please enter all fields.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

       
        returnToLoginButton.addActionListener(new ActionListener() {                     // Return to login form
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();                                                   // the loginform page will cover the register page
                resetToLoginForm();
                revalidate();                                                            //Mainly used for layout updates and adjustments
                repaint();                                                               //For refreshing and updating of visual content.
            }
        });
    }

   
    private void resetToLoginForm() {                                                    // Reset to the original login form
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.add(new JLabel("Worker ID:"));
        mainPanel.add(workerIDField);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(forgotPasswordButton);
    }

    
    private String generateWorkerID(String role) {                                         // Generate worker ID based on role
        Random random = new Random();
        int idNumber = random.nextInt(900) + 100;                                          // Generates a random 3-digit number,prevent duplication
        if (role.equalsIgnoreCase("admin")) {                                              // Use to differentiate Admin and viewer
            return "A" + idNumber;
        } else if (role.equalsIgnoreCase("viewer")) {                                      // Use to differentiate Admin and viewer
            return "W" + idNumber;
        }
        return null;
    }

   
    private void handleForgotPassword() {                                                   // Handle forgot password functionality
        String email = JOptionPane.showInputDialog("Enter your registered email:");
        String newPassword = JOptionPane.showInputDialog("Enter your new password:");

        try {
            User user = User.findByEmail(email);
            if (user != null) {
                user.setPassword(newPassword);
                user.updateToFile();
                JOptionPane.showMessageDialog(this, "Password Reset Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Email not found. Please register first.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {                                                   // initiate program
        new LoginForm();
    }
}
