import javax.swing.*;
import java.awt.*;

public class HospitalFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtLocation;
    private JTextField txtBeds;

    private JTextArea displayArea;

    private HospitalManager manager;

    public HospitalFrame() {

        manager = new HospitalManager();

        HospitalFileManager.loadHospitals(manager);

        setTitle("Hospital Management");

        setSize(900,600);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel =
                new JPanel(
                        new GridLayout(4,2,5,5)
                );

        topPanel.add(
                new JLabel("Hospital ID")
        );

        txtId = new JTextField();

        topPanel.add(txtId);

        topPanel.add(
                new JLabel("Hospital Name")
        );

        txtName = new JTextField();

        topPanel.add(txtName);

        topPanel.add(
                new JLabel("Location")
        );

        txtLocation = new JTextField();

        topPanel.add(txtLocation);

        topPanel.add(
                new JLabel("Available Beds")
        );

        txtBeds = new JTextField();

        topPanel.add(txtBeds);

        add(topPanel,
                BorderLayout.NORTH);

        JPanel buttonPanel =
                new JPanel();

        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton deleteButton =
                new JButton("Delete");

        JButton displayButton =
                new JButton("Display All");

        JButton saveButton =
                new JButton("Save");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);

        add(buttonPanel,
                BorderLayout.SOUTH);

        displayArea =
                new JTextArea();

        displayArea.setEditable(false);

        add(
                new JScrollPane(displayArea),
                BorderLayout.CENTER
        );

        addButton.addActionListener(
                e -> addHospital()
        );

        searchButton.addActionListener(
                e -> searchHospital()
        );

        deleteButton.addActionListener(
                e -> deleteHospital()
        );

        displayButton.addActionListener(
                e -> displayHospitals()
        );

        saveButton.addActionListener(
                e -> saveHospitals()
        );

        setVisible(true);
    }

    private void addHospital() {

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                    );

            String name =
                    txtName.getText();

            String location =
                    txtLocation.getText();

            int beds =
                    Integer.parseInt(
                            txtBeds.getText()
                    );

            Hospital hospital =
                    new Hospital(
                            id,
                            name,
                            location,
                            beds
                    );

            manager.addHospital(hospital);

            JOptionPane.showMessageDialog(
                    this,
                    "Hospital Added"
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void searchHospital() {

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                    );

            Hospital hospital =
                    manager.searchHospital(id);

            if(hospital != null) {

                displayArea.setText(
                        hospital.toString()
                );

            } else {

                displayArea.setText(
                        "Hospital Not Found"
                );
            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid ID"
            );
        }
    }

    private void deleteHospital() {

        try {

            int id =
                    Integer.parseInt(
                            txtId.getText()
                    );

            manager.deleteHospital(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Hospital Deleted"
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid ID"
            );
        }
    }

    private void displayHospitals() {

        StringBuilder builder =
                new StringBuilder();

        for(Hospital hospital :
                manager.getHospitals()) {

            builder.append(hospital)
                    .append("\n");
        }

        displayArea.setText(
                builder.toString()
        );
    }

    private void saveHospitals() {

        HospitalFileManager
                .saveHospitals(manager);

        JOptionPane.showMessageDialog(
                this,
                "Data Saved"
        );
    }
}