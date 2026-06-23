import javax.swing.*;
import java.awt.*;

public class PatientPanel extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField bloodField;
    private JTextField phoneField;
    private JTextArea displayArea;

    public PatientPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Patient Management - HashMap + BST Lookup");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        bloodField = new JTextField();
        phoneField = new JTextField();

        inputPanel.add(new JLabel("Patient ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Blood Group"));
        inputPanel.add(bloodField);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(phoneField);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton searchHashButton = new JButton("Search HashMap");
        JButton searchBSTButton = new JButton("Search BST");
        JButton deleteButton = new JButton("Delete");
        JButton displayButton = new JButton("Display All");
        JButton saveButton = new JButton("Save");

        addButton.addActionListener(e -> addPatient());
        updateButton.addActionListener(e -> updatePatient());
        searchHashButton.addActionListener(e -> searchPatient(false));
        searchBSTButton.addActionListener(e -> searchPatient(true));
        deleteButton.addActionListener(e -> deletePatient());
        displayButton.addActionListener(e -> displayArea.setText(AppData.patientManager.displayAllPatients()));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "Patient data saved.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(searchHashButton);
        buttonPanel.add(searchBSTButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addPatient() {
        try {
            Patient patient = readPatientFromFields();
            boolean added = AppData.patientManager.addPatient(patient);
            if (added) {
                AppData.saveAll();
                displayArea.setText("Patient added successfully.\n" + patient);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Patient ID already exists.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Age and ID must be numbers.");
        }
    }

    private void updatePatient() {
        try {
            Patient patient = readPatientFromFields();
            boolean updated = AppData.patientManager.updatePatient(patient);
            if (updated) {
                AppData.saveAll();
                displayArea.setText("Patient updated successfully.\n" + patient);
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Age and ID must be numbers.");
        }
    }

    private void searchPatient(boolean useBST) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            Patient patient;
            if (useBST) {
                patient = AppData.patientManager.searchPatientUsingBST(id);
            } else {
                patient = AppData.patientManager.searchPatient(id);
            }
            if (patient == null) {
                displayArea.setText("Patient not found.");
            } else {
                displayArea.setText((useBST ? "Found using BST:\n" : "Found using HashMap:\n") + patient);
                nameField.setText(patient.getName());
                ageField.setText(String.valueOf(patient.getAge()));
                bloodField.setText(patient.getBloodGroup());
                phoneField.setText(patient.getPhone());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric patient ID.");
        }
    }

    private void deletePatient() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean deleted = AppData.patientManager.deletePatient(id);
            if (deleted) {
                AppData.saveAll();
                displayArea.setText("Patient deleted successfully.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric patient ID.");
        }
    }

    private Patient readPatientFromFields() {
        int id = Integer.parseInt(idField.getText().trim());
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String bloodGroup = bloodField.getText().trim().toUpperCase();
        String phone = phoneField.getText().trim();
        if (name.isEmpty() || bloodGroup.isEmpty()) {
            throw new IllegalArgumentException("Required field is empty.");
        }
        return new Patient(id, name, age, bloodGroup, phone);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        bloodField.setText("");
        phoneField.setText("");
    }
}
