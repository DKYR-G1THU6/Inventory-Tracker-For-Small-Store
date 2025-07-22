import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViewItem extends JFrame {
    private JTextField filterField; // display filter text field
    private JButton filterButton, loadButton;
    private JComboBox<String> filterOptionsComboBox; // New ComboBox for selecting filter field
    private JTable productTable; // show details in table
    private DefaultTableModel tableModel; // set table structure
    private List<Item> itemList = new ArrayList<>(); // read item list
    private String filePath = "items.txt";

    public ViewItem() {
        // Set up the frame
        this.setTitle("View Item"); // set title name by "View Item"
        this.setSize(800, 600); // set size of GUI window
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout()); //arrange structure by border layout
        setLocationRelativeTo(null); // window pop up at center

        // Create the filter input, ComboBox, and buttons
        JPanel filterPanel = new JPanel(new FlowLayout()); 

        // ComboBox for selecting filter field
        String[] filterOptions = {"ID", "Name", "ItemType", "SellCost", "BuyCost", "Quantity", "Location", "Brand"};
        filterOptionsComboBox = new JComboBox<>(filterOptions);

        filterField = new JTextField(20); // set text field size
        filterButton = new JButton("Filter Products"); // name filter button as "Filter Products"
        loadButton = new JButton("Load Products"); // name load button as "Load Products"
        
        //add content to panel
        filterPanel.add(new JLabel("Select filter field:"));
        filterPanel.add(filterOptionsComboBox); 
        filterPanel.add(new JLabel("Enter filter keyword:"));
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(loadButton);

        this.add(filterPanel, BorderLayout.NORTH);// add panel to frame

        // Create the table model and set up the table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "ItemType", "SellCost(RM)", "BuyCost(RM)", "Quantity", "Location", "Brand"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Load products button action
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });

        // Filter button action
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterProducts();
            }
        });

        this.setVisible(true);
    }

    private void loadProducts() {
        tableModel.setRowCount(0); // Clear the table
        itemList.clear(); // Clear the list before loading new items

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8) {
                    continue; // Skip lines with insufficient data
                }
                
                //retrieve data from items.txt
                String id = data[0];
                String name = data[1];
                String itemType = data[2];
                double sellCost = Double.parseDouble(data[3]);
                double buyCost = Double.parseDouble(data[4]);
                int quantity = Integer.parseInt(data[5]);
                String location = data[6];
                String brand = data[7];

                // Create a new Item and add to list
                Item item = new Item(id, name, itemType, sellCost, buyCost, quantity, location, brand);
                itemList.add(item);

                // Add item to table
                tableModel.addRow(new Object[]{id, name, itemType, sellCost, buyCost, quantity, location, brand});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file.");
            e.printStackTrace();
        }
    }

    // Enhanced filter method with ComboBox selection
    private void filterProducts() {
        String keyword = filterField.getText().toLowerCase();
        String selectedField = (String) filterOptionsComboBox.getSelectedItem(); // Get the selected field
        tableModel.setRowCount(0); // Clear the table

        // Loop through the items and filter by the selected field
        for (Item item : itemList) {
            boolean match = false;

            switch (selectedField) {
                case "ID":
                    match = item.getItemId().toLowerCase().contains(keyword);
                    break;
                case "Name":
                    match = item.getName().toLowerCase().contains(keyword);
                    break;
                case "ItemType":
                    match = item.getItemType().toLowerCase().contains(keyword);
                    break;
                case "SellCost":
                    match = String.valueOf(item.getSellCost()).toLowerCase().contains(keyword);
                    break;
                case "BuyCost":
                    match = String.valueOf(item.getBuyCost()).toLowerCase().contains(keyword);
                    break;
                case "Quantity":
                    match = String.valueOf(item.getQuantity()).toLowerCase().contains(keyword);
                    break;
                case "Location":
                    match = item.getLocation().toLowerCase().contains(keyword);
                    break;
                case "Brand":
                    match = item.getBrand().toLowerCase().contains(keyword);
                    break;
            }

            if (match) {
                // Add the matching item to the table
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
    }

    // Item class for holding product details


    // Main method to run the application
    public static void main(String[] args) {
        new ViewItem();
    }
}