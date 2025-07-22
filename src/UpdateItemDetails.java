

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class UpdateItemDetails extends JFrame {
    private JTextField itemIdField, updateValueField; //display the text 
    private JComboBox<String> updateOptionsComboBox; // used as selection to let user to choose 
    private JButton updateButton, loadButton; // button to allow user to navigate
    private JTable itemTable; // create table to make it readable
    private DefaultTableModel tableModel; // set the table structure 

    private List<Item> itemList = new ArrayList<>(); // create an array list and store the items
    //this filePath allow my teammates to change the file path so it can make it easy to summarize 
    private String filePath = "items.txt";
    //parameter that used to set up the GUI window.
    public UpdateItemDetails() {
    	
        this.setTitle("Update Item Details");//the title set the "Update Item Details"
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//allow the user to close the window
        this.setSize(800, 600);// set the size of the GUI window 
        this.setLayout(new BorderLayout());// border layout make the structure arrange more nicely 
        setLocationRelativeTo(null);
        // create the form panel, set it as 4 columns and 2 rows 
        JPanel formPanel = new JPanel(new GridLayout(4, 2));

        formPanel.add(new JLabel("Enter Item ID:"));//The user have to enter itemId the search for the item.
        itemIdField = new JTextField();
        formPanel.add(itemIdField);

        // ComboBox for selecting the update option the user want to choose 
        formPanel.add(new JLabel("Select Update Option:"));
        String[] updateOptions = {"Name", "ItemType", "SellCost", "BuyCost", "Quantity", "Location", "Brand"};
        updateOptionsComboBox = new JComboBox<>(updateOptions);
        formPanel.add(updateOptionsComboBox);

        // enter the new value to apply the update function
        formPanel.add(new JLabel("Enter New Value:"));
        updateValueField = new JTextField();
        formPanel.add(updateValueField);

        // add the form panel to the frame
        this.add(formPanel, BorderLayout.NORTH);
        //create the structure of the table. 
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "ItemType", "SellCost(RM)", "BuyCost(RM)", "Quantity", "Location", "Brand"}, 0);
        itemTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // button panel for updating and loading items
        JPanel buttonPanel = new JPanel(new FlowLayout());

        updateButton = new JButton("Update Item details");
        loadButton = new JButton("Display Item");
        
        buttonPanel.add(updateButton);
        buttonPanel.add(loadButton);

        // add the button panel to the frame
        this.add(buttonPanel, BorderLayout.SOUTH);

        // apple update function in the updateButton
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemId = itemIdField.getText();
                String selectedOption = (String) updateOptionsComboBox.getSelectedItem();
                String newValue = updateValueField.getText();
                updateItemDetails(itemId, selectedOption, newValue);
                
            }
        });
        //apply display list in the loadButton
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadItem();//throw the parameter
                
            }
        });

        this.setVisible(true);
    }
 
    private void updateTableModel() {
        tableModel.setRowCount(0);
        
        //reload items from the itemList into the table model
        for (Item item : itemList) {
            tableModel.addRow(new Object[]{
                item.getItemId(),
                item.getName(),
                item.getItemType(),
                item.getSellCost(),
                item.getBuyCost(),
                item.getQuantity(),
                item.getLocation(),
                item.getBrand()
            });
        }
    }
    //parameter that function as update item details
    private void updateItemDetails(String itemId, String fieldToUpdate, String newValue) {
    	//set itemFond to false
    	boolean itemFound = false;
    	try {
    		
	        for (Item item : itemList){ //read the text file
	            if (item.getItemId().equals(itemId)) {
	                switch (fieldToUpdate) { // use switch function to loop
	                    case "Name":  
	                        item.setName(newValue); //update name
	                        break;
	                    case "ItemType":
	                        item.setItemType(newValue);//update itemType
	                        break;
	                    case "SellCost":
	                        item.setSellCost(Double.parseDouble(newValue)); //update sellCost
	                        break;
	                    case "BuyCost":
	                        item.setBuyCost(Double.parseDouble(newValue)); //update buyCost
	                        break;
	                    case "Quantity":
	                        item.setQuantity(Integer.parseInt(newValue)); //update qty
	                        break;
	                    case "Location":
	                        item.setLocation(newValue); // update location
	                        break;
	                    case "Brand":
	                        item.setBrand(newValue); // update brand
	                        break;
	                }
	                itemFound = true;
	                updateTableModel(); //parameter that display the updated list
	                saveItem(); // parameter that save the updated list back to the txt file
	                JOptionPane.showMessageDialog(this, "Item updated successfully!");
	                break;
	            }
	        }
	        if (!itemFound) {
	            JOptionPane.showMessageDialog(this, "Item ID not found!");
	        }
    	}
    	catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for field: " + fieldToUpdate);
            e.printStackTrace();
    	}
    	catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
    	} 
    	catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error!!");
        e.printStackTrace();
    	}
    }
    //saved the updated item details back to the file 
    private void saveItem() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Item item : itemList) {
                String line = item.getItemId() + "," +
                              item.getName() + "," +
                              item.getItemType() + "," +
                              item.getSellCost() + "," +
                              item.getBuyCost() + "," +
                              item.getQuantity() + "," +
                              item.getLocation() + "," +
                              item.getBrand();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving items to file.");
            e.printStackTrace();
        }
    }


    private void loadItem() {
        // clear the table model before loading new items
        tableModel.setRowCount(0);
        itemList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); //comma to separate
                if (data.length < 8) { // Skip lines that don't have enough data
                    continue;
                }

                String id = data[0];
                String name = data[1];
                String itemType = data[2];
                double sellCost = Double.parseDouble(data[3]);
                double buyCost = Double.parseDouble(data[4]);
                int quantity = Integer.parseInt(data[5]);
                String location = data[6];
                String brand = data[7];

                // create a new Item using the constructor
                Item item = new Item(id, name, itemType, sellCost, buyCost, quantity, location, brand);

                // add item to the itemList
                itemList.add(item);

                // add item to the table model for display
                tableModel.addRow(new Object[]{id, name, itemType, sellCost, buyCost, quantity, location, brand});
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error parsing number from file.");
            e.printStackTrace();
        }
    }

//the main function
    public static void main(String[] args) {
        new UpdateItemDetails();
    }
}