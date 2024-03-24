import javax.swing.*;

interface Item {
    String getItemName();
    double getPrice();
    double calculateTotalSales(int quantity);
}

class Product implements Item {
    private String itemName;
    private double price;

    public Product(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public double calculateTotalSales(int quantity) {
        return price * quantity;
    }
}

class Service implements Item {
    private String itemName;
    private double pricePerHour;

    public Service(String itemName, double pricePerHour) {
        this.itemName = itemName;
        this.pricePerHour = pricePerHour;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return pricePerHour;
    }

    public double calculateTotalSales(int hours) {
        return pricePerHour * hours;
    }
}

public class SalesCalculatorGUI extends JFrame {
    private List<Item> items;
    private JTextArea salesTextArea;
    private JTextField quantityField;
    private JComboBox<String> itemComboBox;

    public SalesCalculatorGUI() {
        setTitle("Sales Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        items = new ArrayList<>();
        items.add(new Product("Product A", 10.0));
        items.add(new Product("Product B", 15.0));
        items.add(new Service("Service X", 20.0));
        items.add(new Service("Service Y", 25.0));

        JLabel itemLabel = new JLabel("Item:");
        itemComboBox = new JComboBox<>();
        for (Item item : items) {
            itemComboBox.addItem(item.getItemName());
        }

        JLabel quantityLabel = new JLabel("Quantity/Hours:");
        quantityField = new JTextField(10);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalSales();
            }
        });

        salesTextArea = new JTextArea(10, 30);
        salesTextArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(itemLabel);
        panel.add(itemComboBox);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(calculateButton);

        JScrollPane scrollPane = new JScrollPane(salesTextArea);

        getContentPane().add(panel, "North");
        getContentPane().add(scrollPane, "Center");
    }

    private void calculateTotalSales() {
        int selectedIndex = itemComboBox.getSelectedIndex();
        Item selectedItem = items.get(selectedIndex);
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double totalSales = selectedItem.calculateTotalSales(quantity);
            salesTextArea.append(selectedItem.getItemName() + ": $" + totalSales + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity/hours.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SalesCalculatorGUI().setVisible(true);
            }
        });
    }
}

