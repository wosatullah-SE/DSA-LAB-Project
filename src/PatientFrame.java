import javax.swing.*;
import java.awt.*;

public class PatientFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtBloodGroup;

    private JTextArea displayArea;

    private PatientManager manager;

    public PatientFrame() {

        manager = new PatientManager();

        PatientFileManager.loadPatients(manager);

        setTitle("SwiftAid - Patient Management");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        topPanel.add(new JLabel("Patient ID"));
        txtId = new JTextField();
        topPanel.add(txtId);

        topPanel.add(new JLabel("Name"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Age"));
        txtAge = new JTextField();
        topPanel.add(txtAge);

        topPanel.add(new JLabel("Blood Group"));
        txtBloodGroup = new JTextField();
        topPanel.add(txtBloodGroup);

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton displayButton = new JButton("Display All");
        JButton saveButton = new JButton("Save");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(displayArea);

        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addPatient());

        searchButton.addActionListener(e -> searchPatient());

        deleteButton.addActionListener(e -> deletePatient());

        displayButton.addActionListener(e -> displayPatients());

        saveButton.addActionListener(e -> savePatients());

        setVisible(true);
    }

    private void addPatient() {

        try {

            int id = Integer.parseInt(txtId.getText());

            String name = txtName.getText();

            int age = Integer.parseInt(txtAge.getText());

            String bloodGroup = txtBloodGroup.getText();

            Patient patient =
                    new Patient(id, name, age, bloodGroup);

            manager.addPatient(patient);

            JOptionPane.showMessageDialog(
                    this,
                    "Patient Added Successfully"
            );

            clearFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values"
            );
        }
    }

    private void searchPatient() {

        try {

            int id = Integer.parseInt(txtId.getText());

            Patient patient = manager.searchPatient(id);

            if (patient != null) {

                displayArea.setText(patient.toString());

            } else {

                displayArea.setText("Patient Not Found");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Patient ID"
            );
        }
    }

    private void deletePatient() {

        try {

            int id = Integer.parseInt(txtId.getText());

            manager.deletePatient(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Patient Deleted"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Patient ID"
            );
        }
    }

    private void displayPatients() {

        StringBuilder builder = new StringBuilder();

        for (Patient patient :
                manager.getPatients().values()) {

            builder.append(patient)
                    .append("\n");
        }

        if (builder.length() == 0) {

            displayArea.setText(
                    "No Patients Available"
            );

        } else {

            displayArea.setText(
                    builder.toString()
            );
        }
    }

    private void savePatients() {

        PatientFileManager.savePatients(manager);

        JOptionPane.showMessageDialog(
                this,
                "Data Saved Successfully"
        );
    }

    private void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtBloodGroup.setText("");
    }
}