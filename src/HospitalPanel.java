import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HospitalPanel extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField bedsField;
    private JTextField phoneField;
    private JTextArea displayArea;

    public HospitalPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Hospital Management - HashMap Lookup + Array Hospital List");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        bedsField = new JTextField();
        phoneField = new JTextField();

        inputPanel.add(new JLabel("Hospital ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Hospital Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Graph Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Available Beds"));
        inputPanel.add(bedsField);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(phoneField);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton searchButton = new JButton("Search");
        JButton displayButton = new JButton("Display All");
        JButton saveButton = new JButton("Save");

        addButton.addActionListener(e -> addHospital());
        updateButton.addActionListener(e -> updateHospital());
        searchButton.addActionListener(e -> searchHospital());
        displayButton.addActionListener(e -> displayArea.setText(AppData.hospitalManager.displayAllHospitals()));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "Hospital data saved.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addHospital() {
        try {
            Hospital hospital = readHospitalFromFields();
            boolean added = AppData.hospitalManager.addHospital(hospital);
            if (added) {
                AppData.routeGraph.addLocation(hospital.getLocation());
                AppData.saveAll();
                displayArea.setText("Hospital added successfully.\n" + hospital);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Hospital ID already exists or array is full.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. ID and beds must be numbers.");
        }
    }

    private void updateHospital() {
        try {
            Hospital hospital = readHospitalFromFields();
            boolean updated = AppData.hospitalManager.updateHospital(hospital);
            if (updated) {
                AppData.routeGraph.addLocation(hospital.getLocation());
                AppData.saveAll();
                displayArea.setText("Hospital updated successfully.\n" + hospital);
            } else {
                JOptionPane.showMessageDialog(this, "Hospital not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. ID and beds must be numbers.");
        }
    }

    private void searchHospital() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            Hospital hospital = AppData.hospitalManager.searchHospital(id);
            if (hospital == null) {
                displayArea.setText("Hospital not found.");
            } else {
                displayArea.setText(hospital.toString());
                nameField.setText(hospital.getName());
                locationField.setText(hospital.getLocation());
                bedsField.setText(String.valueOf(hospital.getAvailableBeds()));
                phoneField.setText(hospital.getPhone());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric hospital ID.");
        }
    }

    private Hospital readHospitalFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        int beds = Integer.parseInt(bedsField.getText().trim());
        String phone = phoneField.getText().trim();
        if (name.isEmpty() || location.isEmpty()) {
            throw new IllegalArgumentException("Required field is empty.");
        }
        return new Hospital(id, name, location, beds, phone);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        bedsField.setText("");
        phoneField.setText("");
    }
}
