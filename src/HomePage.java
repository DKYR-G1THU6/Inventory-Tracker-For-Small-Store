import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JButton viewStockButton, updateStockButton, deleteStockButton, addStockButton, userProfileButton;
    private String workerID;
    private String email;

    public HomePage(String workerID, String email) {                                     //constructor of HomePage
        this.workerID = workerID;
        this.email = email;

        setTitle("Stock Management");                                                    // set the name of the window
        setSize(800, 600);                                                               // set the size of the window/frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                  // exit the whole program
        setLayout(new BorderLayout());                                                   //in order to set approximate  location at middle
        setLocationRelativeTo(null);                                                     //generate the window at the middle of the screen

        // Create buttons
        viewStockButton = new JButton("View Stock");
        updateStockButton = new JButton("Update Stock");
        deleteStockButton = new JButton("Delete Stock");
        addStockButton = new JButton("Add Stock");
        userProfileButton = new JButton("User Profile");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));                              //set precise control of ui
        buttonPanel.add(viewStockButton);
        buttonPanel.add(updateStockButton);
        buttonPanel.add(deleteStockButton);
        buttonPanel.add(addStockButton);
        buttonPanel.add(userProfileButton);

        add(buttonPanel, BorderLayout.CENTER);                                             //set approximate  location at middle

        
        // Create ActionListener class
        class Click implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == userProfileButton) {                              //open user profile page window
                    new UserProfilePage(workerID, email).setVisible(true);
                }
                if (event.getSource() == viewStockButton) {                                //open View Stock Page window
                    new ViewItem().setVisible(true);
                }
                if (event.getSource() == addStockButton) {                                 //open Add Stock page window
                    new Add().setVisible(true);
                    
                }
                if (event.getSource() == updateStockButton) {                              //open Update Stock page window
                    new UpdateItemDetails().setVisible(true);
                }
                if (event.getSource() == deleteStockButton) {                              //open Delete Stock page window
                    new DeleteItemDetails().setVisible(true);
                }
               
            }
        }

        
        ActionListener listener = new Click();                                             // Create ActionListener instance and add to buttons
        userProfileButton.addActionListener(listener);
        addStockButton.addActionListener(listener);
        viewStockButton.addActionListener(listener);
        deleteStockButton.addActionListener(listener);
        updateStockButton.addActionListener(listener);

    }

    public static void main(String[] args) {    
        new HomePage("A123", "admin@example.com").setVisible(true);                        // Mocked for example, if value is empty
    }
}

