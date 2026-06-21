import javax.swing.*;
import java.awt.*;

public class AmbulancePanel extends JPanel {
    private JTextField idField;
    private JTextField driverField;
    private JTextField locationField;
    private JTextField phoneField;
    private JCheckBox availableBox;
    private JTextArea displayArea;

    public AmbulancePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Ambulance Management - Circular Queue Rotation");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        idField = new JTextField();
        driverField = new JTextField();
        locationField = new JTextField();
        phoneField = new JTextField();
        availableBox = new JCheckBox("Available", true);

        inputPanel.add(new JLabel("Ambulance ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Driver Name"));
        inputPanel.add(driverField);
        inputPanel.add(new JLabel("Current Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Status"));
        inputPanel.add(availableBox);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton searchButton = new JButton("Search");
        JButton availableButton = new JButton("Mark Available");
        JButton busyButton = new JButton("Mark Busy");
        JButton nextButton = new JButton("Show Next Available");
        JButton displayButton = new JButton("Display Queue");
        JButton saveButton = new JButton("Save");

        addButton.addActionListener(e -> addAmbulance());
        searchButton.addActionListener(e -> searchAmbulance());
        availableButton.addActionListener(e -> markStatus(true));
        busyButton.addActionListener(e -> markStatus(false));
        nextButton.addActionListener(e -> showNextAvailable());
        displayButton.addActionListener(e -> displayArea.setText(AppData.ambulanceQueue.displayAmbulances()));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "Ambulance data saved.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(availableButton);
        buttonPanel.add(busyButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addAmbulance() {
        try {
            Ambulance ambulance = readAmbulanceFromFields();
            boolean added = AppData.ambulanceQueue.addAmbulance(ambulance);
            if (added) {
                AppData.routeGraph.addLocation(ambulance.getCurrentLocation());
                AppData.saveAll();
                displayArea.setText("Ambulance added in circular queue.\n" + ambulance);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Ambulance ID already exists or queue is full.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. ID must be a number.");
        }
    }

    private void searchAmbulance() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            Ambulance ambulance = AppData.ambulanceQueue.searchAmbulance(id);
            if (ambulance == null) {
                displayArea.setText("Ambulance not found.");
            } else {
                displayArea.setText(ambulance.toString());
                driverField.setText(ambulance.getDriverName());
                locationField.setText(ambulance.getCurrentLocation());
                phoneField.setText(ambulance.getPhone());
                availableBox.setSelected(ambulance.isAvailable());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric ambulance ID.");
        }
    }

    private void markStatus(boolean available) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean changed = AppData.ambulanceQueue.markAvailable(id, available);
            if (changed) {
                AppData.saveAll();
                displayArea.setText("Ambulance status updated.\n" + AppData.ambulanceQueue.searchAmbulance(id));
            } else {
                JOptionPane.showMessageDialog(this, "Ambulance not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric ambulance ID.");
        }
    }

    private void showNextAvailable() {
        Ambulance ambulance = AppData.ambulanceQueue.getNextAvailableAmbulance();
        if (ambulance == null) {
            displayArea.setText("No available ambulance found.");
        } else {
            displayArea.setText("Next ambulance selected by circular rotation:\n" + ambulance);
        }
    }

    private Ambulance readAmbulanceFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        String driver = driverField.getText().trim();
        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        if (driver.isEmpty() || location.isEmpty()) {
            throw new IllegalArgumentException("Required field is empty.");
        }
        return new Ambulance(id, driver, location, availableBox.isSelected(), phone);
    }

    private void clearFields() {
        idField.setText("");
        driverField.setText("");
        locationField.setText("");
        phoneField.setText("");
        availableBox.setSelected(true);
    }
}
