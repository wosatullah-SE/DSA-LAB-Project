import javax.swing.*;
import java.awt.*;

public class BloodBankFrame extends JFrame {

    private JTextField txtBloodGroup;
    private JTextField txtUnits;

    private JTextArea displayArea;

    private BloodBankManager manager;

    public BloodBankFrame() {

        manager = new BloodBankManager();

        BloodBankFileManager
                .loadBloodRecords(manager);

        setTitle("Blood Bank Management");

        setSize(900,600);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel =
                new JPanel(
                        new GridLayout(2,2,5,5)
                );

        topPanel.add(
                new JLabel("Blood Group")
        );

        txtBloodGroup =
                new JTextField();

        topPanel.add(txtBloodGroup);

        topPanel.add(
                new JLabel("Units Available")
        );

        txtUnits =
                new JTextField();

        topPanel.add(txtUnits);

        add(topPanel,
                BorderLayout.NORTH);

        JPanel buttonPanel =
                new JPanel();

        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton updateButton =
                new JButton("Update Units");

        JButton displayButton =
                new JButton("Display All");

        JButton saveButton =
                new JButton("Save");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
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
                e -> addBloodRecord()
        );

        searchButton.addActionListener(
                e -> searchBloodGroup()
        );

        updateButton.addActionListener(
                e -> updateUnits()
        );

        displayButton.addActionListener(
                e -> displayRecords()
        );

        saveButton.addActionListener(
                e -> saveRecords()
        );

        setVisible(true);
    }

    private void addBloodRecord() {

        try {

            String bloodGroup =
                    txtBloodGroup.getText();

            int units =
                    Integer.parseInt(
                            txtUnits.getText()
                    );

            BloodBank bloodBank =
                    new BloodBank(
                            bloodGroup,
                            units
                    );

            manager.addBloodRecord(
                    bloodBank
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Blood Record Added"
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void searchBloodGroup() {

        String bloodGroup =
                txtBloodGroup.getText();

        BloodBank bloodBank =
                manager.searchBloodGroup(
                        bloodGroup
                );

        if(bloodBank != null) {

            displayArea.setText(
                    bloodBank.toString()
            );

        } else {

            displayArea.setText(
                    "Blood Group Not Found"
            );
        }
    }

    private void updateUnits() {

        try {

            String bloodGroup =
                    txtBloodGroup.getText();

            int units =
                    Integer.parseInt(
                            txtUnits.getText()
                    );

            BloodBank bloodBank =
                    manager.searchBloodGroup(
                            bloodGroup
                    );

            if(bloodBank != null) {

                bloodBank.setUnitsAvailable(
                        units
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Units Updated"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Blood Group Not Found"
                );
            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void displayRecords() {

        StringBuilder builder =
                new StringBuilder();

        for(BloodBank bloodBank :
                manager.getBloodRecords()) {

            builder.append(bloodBank)
                    .append("\n");
        }

        displayArea.setText(
                builder.toString()
        );
    }

    private void saveRecords() {

        BloodBankFileManager
                .saveBloodRecords(manager);

        JOptionPane.showMessageDialog(
                this,
                "Data Saved"
        );
    }
}