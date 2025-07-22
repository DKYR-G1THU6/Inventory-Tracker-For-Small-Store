	import java.awt.*;
	import java.io.*;
	import java.util.*;
	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;

	public class Add extends JFrame {
		   private ArrayList<Item> itemList; //store item details
		    private JTextArea displayArea;// key in details
		    private JTable itemTable;// show details in table
		    private DefaultTableModel tableModel;//set table structure


		    public Add() {
		        itemList = new ArrayList<>();// create item array list
		        setTitle("Item Management");// set title name by "Item Management"
		        setSize(800, 600); // set the size of GUI window
		        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// allow user close the window
		        setLocationRelativeTo(null);
		        

		        // Create UI components
		        displayArea = new JTextArea();//create display area of details
		        displayArea.setEditable(false);//the area unable to edit
		        JScrollPane scrollPane = new JScrollPane(displayArea);

		        JButton addItemButton = new JButton("Add Item");//button for adding new item
		        JButton checkItemButton = new JButton("Check Item by ID");// button for checking new added items' details
		        JButton saveItemsButton = new JButton("Save Items to File");//button to save new added items

		        // Set up layout
		        JPanel panel = new JPanel();
		        panel.setLayout(new FlowLayout());
		        panel.add(addItemButton);
		        panel.add(checkItemButton);
		        panel.add(saveItemsButton);

		        add(panel, BorderLayout.NORTH);//arrange the panel at north

		        // Set up table for displaying items
		        String[] columnNames = {"ID", "Name", "Type", "Price", "Cost", "Quantity", "Location", "Brand"};
		        tableModel = new DefaultTableModel(columnNames, 0);
		        itemTable = new JTable(tableModel);
		        JScrollPane tableScrollPane = new JScrollPane(itemTable);
		        add(tableScrollPane, BorderLayout.CENTER);//arrange the table display in center


		        // Add event listeners
		        addItemButton.addActionListener(e -> addItemDialog());
		        checkItemButton.addActionListener(e -> checkItemByIdDialog());
		        saveItemsButton.addActionListener(e -> saveItemsToFile());
		        
		        setVisible(true);
		        
		    }

		    private void addItemDialog() {//add item function
		    	//set text field size
		        JTextField idField = new JTextField(5);
		        JTextField nameField = new JTextField(10);
		        JTextField typeField = new JTextField(10);
		        JTextField priceField = new JTextField(5);
		        JTextField costField = new JTextField(5);
		        JTextField quantityField = new JTextField(5);
		        JTextField locationField = new JTextField(10);
		        JTextField brandField = new JTextField(10);

		        JPanel dialogPanel = new JPanel();//create dialog panel
		        dialogPanel.setLayout(new GridLayout(8, 2));
		        dialogPanel.add(new JLabel("ID:"));
		        dialogPanel.add(idField);
		        dialogPanel.add(new JLabel("Name:"));
		        dialogPanel.add(nameField);
		        dialogPanel.add(new JLabel("Type:"));
		        dialogPanel.add(typeField);
		        dialogPanel.add(new JLabel("Price (RM):"));
		        dialogPanel.add(priceField);
		        dialogPanel.add(new JLabel("Cost (RM):"));
		        dialogPanel.add(costField);
		        dialogPanel.add(new JLabel("Quantity:"));
		        dialogPanel.add(quantityField);
		        dialogPanel.add(new JLabel("Location:"));
		        dialogPanel.add(locationField);
		        dialogPanel.add(new JLabel("Brand:"));
		        dialogPanel.add(brandField);
		        
		        
		        //create ok and cancel option
		        int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Add Item", JOptionPane.OK_CANCEL_OPTION);

		        if (result == JOptionPane.OK_OPTION) {
		            try {
		                String id = idField.getText();
		                String name = nameField.getText();
		                String type = typeField.getText();
		                double price = Double.parseDouble(priceField.getText());
		                double cost = Double.parseDouble(costField.getText());
		                int quantity = Integer.parseInt(quantityField.getText());
		                String location = locationField.getText();
		                String brand = brandField.getText();

		                Item newItem = new Item(id, name, type, price, cost, quantity, location, brand);
		                itemList.add(newItem);

		                // Add item to table
		                tableModel.addRow(new Object[]{id, name, type, price, cost, quantity, location, brand});
		             // display item in table
		                displayArea.append("Item added: " + newItem + "\n");
		            } catch (NumberFormatException ex) {
		                JOptionPane.showMessageDialog(this, "Invalid input. Please enter correct values.");
		            }
		        }
		    }
		    //check new added item by Id function
		    private void checkItemByIdDialog() {
		        String idInput = JOptionPane.showInputDialog(this, "Enter Item ID:");
		        try {
		            String itemId = idInput;
		            Item foundItem = null;
		            for (Item item : itemList) {
		                if (item.getItemId() == itemId) {
		                    foundItem = item;
		                    break;
		                }
		            }

		            if (foundItem != null) {
		                JOptionPane.showMessageDialog(this, "Item found:\n" + foundItem);// display items found details
		            } else {
		                JOptionPane.showMessageDialog(this, "Item with ID " + itemId + " not found.");// display itemId not found
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a numeric value.");
		            
		        }
		    }
		  //save new added items to "items.txt"
		    private void saveItemsToFile() {
		        try {
		            File file = new File("items.txt");
		            if (!file.exists()) {
		                file.createNewFile();
		            }

		            try (FileWriter writer = new FileWriter(file, true)) {
		                for (Item item : itemList) {
		                    writer.write(item.toString() + "\n");
		                }
		                JOptionPane.showMessageDialog(this, "Items saved to 'items.txt'.");// display the message after save successful
		            }
		        } catch (IOException e) {
		            JOptionPane.showMessageDialog(this, "An error occurred while saving the file.");
		            e.printStackTrace();
		        }
		        
		    }
		  // main function
		    public static void main(String[] args) {
		        SwingUtilities.invokeLater(Add::new);
		        
		    }
	}

