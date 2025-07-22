

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class DeleteItemDetails extends JFrame {
    private JTextField itemIdField;
    private JButton deleteButton, loadButton;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    
    private List<Item> itemList = new ArrayList<>();
    // this allow other teammates to change the file path.
    private String filePath = "items.txt";
    
    public DeleteItemDetails() {
        this.setTitle("Delete Item Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //allow the user to close the window
        this.setSize(800, 600);
        this.setLayout(new BorderLayout()); //use borderlayout to arrange the structure of the GUI.
        setLocationRelativeTo(null);
        JPanel formPanel = new JPanel(new GridLayout(2, 2)); //in this case i just have to enter the itemId.

        formPanel.add(new JLabel("Enter Item ID:"));//enter itemId to locate the selected item.
        itemIdField = new JTextField();
        formPanel.add(itemIdField);
        
        this.add(formPanel, BorderLayout.NORTH);
        
        //table created to identify and adding header to allow the user to read
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "ItemType", "SellCost (RM)", "BuyCost(RM)", "Quantity", "Location", "Brand"}, 0);
        itemTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemTable);
        this.add(scrollPane, BorderLayout.CENTER);
        
        //button created to allow the user to navigate the function.
        JPanel buttonPanel = new JPanel(new FlowLayout());
        deleteButton = new JButton("Delete Item");
        loadButton = new JButton("Display Item");
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        //assign the function to each of the button, this button function as delete item.
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
            }
        });
        // this button use to display the list
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadItemsFromFile();
            }
        });
        //to show the GUI window
        this.setVisible(true);
    }
    // A parameter that function as delete selected item. 
    private void deleteSelectedItem() {
        String itemId = itemIdField.getText().trim();
        //if user didnt not enter anything this message will show.
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Item ID.");
            return;
        }
        //set the itemFound to false to perform while-loop function.
        boolean itemFound = false;
        Iterator<Item> iterator = itemList.iterator();
        //use while-loop to find the item user key in
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemId().equals(itemId)) {
                iterator.remove(); 
                itemFound = true;
                break;
            }
        }
        //if the item found and successfully delete
        if (itemFound) {
        	updateTableModel();//parameter that show the updated list
            saveItemsToFile(); //parameter that saved the updated list
            JOptionPane.showMessageDialog(this, "Item deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Item ID not found!");
        }
    }
    
    private void saveItemsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Item item : itemList) {
               
                if (item.getName() == null && item.getItemType() == null && item.getSellCost() < 0 
                        && item.getBuyCost() < 0 && item.getQuantity() < 0 && item.getLocation() == null 
                        && item.getBrand() == null) {
                    continue;
                }
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
    //display item list
    private void loadItemsFromFile() {
        tableModel.setRowCount(0);
        itemList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8) {
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

                Item item = new Item(id, name, itemType, sellCost, buyCost, quantity, location, brand);
                itemList.add(item);
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
    
    private void updateTableModel() {
        tableModel.setRowCount(0);
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

//main function 
    public static void main(String[] args) {
        new DeleteItemDetails();
    }
}