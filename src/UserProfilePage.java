import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserProfilePage extends JFrame {
    private JLabel workerIDLabel, emailLabel;
    private JButton logOutButton, resetPasswordButton;

    public UserProfilePage(String workerID, String password) {
        setTitle("User Profile");
        setSize(800, 600);                                                                // set the size of frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                //prevent the close of the window  will stop whole program
        setLayout(new GridLayout(4, 1, 10, 10));                                          // in order to precise control the position of the button
        setLocationRelativeTo(null);                                                      // let the window can generate at the middle of user screen

                               
        workerIDLabel = new JLabel("Worker ID: " + workerID);                             // Labels
        emailLabel = new JLabel("Password: " + password);

        
        logOutButton = new JButton("Log Out");                                            // Buttons
        resetPasswordButton = new JButton("Reset Password");

        
        add(workerIDLabel);                                                               // Adding to the frame
        add(emailLabel);                                                                  // Adding to the frame
        add(resetPasswordButton);                                                         // Adding to the frame
        add(logOutButton);                                                                // Adding to the frame

        
        resetPasswordButton.addActionListener(new ActionListener() {                      // Action listeners of handleResetPassword
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResetPassword(password);
            }
        });

        logOutButton.addActionListener(new ActionListener() {                             // Action listeners of handleLogOut
            @Override
            public void actionPerformed(ActionEvent e) {
            	handleLogOut();
            	
            }
        });
    }

    private void handleResetPassword(String email) {
        String newPassword = JOptionPane.showInputDialog(this, "Enter your new password:");

        try {
            User user = User.findByEmail(email); 
            if (user != null) {                                                            // make sure user exist
                user.setPassword(newPassword);
                user.updateToFile();
                JOptionPane.showMessageDialog(this, "Password Reset Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();                                                          //This makes it easier to locate and resolve issues
        }
    }

    private void handleLogOut() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            this.dispose();                                                                //It closes and destroys the window, making it disappear from the screen and releasing system resources associated with the window.
            new LoginForm().setVisible(true); 
           // Return to login form
        }
    }
}
