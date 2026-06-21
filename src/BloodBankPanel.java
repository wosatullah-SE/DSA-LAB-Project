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

public class BloodBankPanel extends JPanel {
    private JTextField groupField;
    private JTextField bankField;
    private JTextField locationField;
    private JTextField unitsField;
    private JTextField phoneField;
    private JTextArea displayArea;

    public BloodBankPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Blood Bank Management - HashMap Lookup by Blood Group");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        groupField = new JTextField();
        bankField = new JTextField();
        locationField = new JTextField();
        unitsField = new JTextField();
        phoneField = new JTextField();

        inputPanel.add(new JLabel("Blood Group"));
        inputPanel.add(groupField);
        inputPanel.add(new JLabel("Bank Name"));
        inputPanel.add(bankField);
        inputPanel.add(new JLabel("Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Units Available"));
        inputPanel.add(unitsField);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(phoneField);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add / Update");
        JButton searchButton = new JButton("Search");
        JButton issueButton = new JButton("Issue 1 Unit");
        JButton displayButton = new JButton("Display All");
        JButton saveButton = new JButton("Save");

        addButton.addActionListener(e -> addOrUpdate());
        searchButton.addActionListener(e -> searchBlood());
        issueButton.addActionListener(e -> issueBlood());
        displayButton.addActionListener(e -> displayArea.setText(AppData.bloodBankManager.displayAllBloodBanks()));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "Blood bank data saved.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addOrUpdate() {
        try {
            BloodBank bloodBank = readBloodBankFromFields();
            AppData.bloodBankManager.addOrUpdateBloodBank(bloodBank);
            AppData.routeGraph.addLocation(bloodBank.getLocation());
            AppData.saveAll();
            displayArea.setText("Blood bank record saved.\n" + bloodBank);
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Units must be a number.");
        }
    }

    private void searchBlood() {
        String group = groupField.getText().trim().toUpperCase();
        BloodBank bank = AppData.bloodBankManager.searchBloodGroup(group);
        if (bank == null) {
            displayArea.setText("Blood group not found.");
        } else {
            displayArea.setText(bank.toString());
            bankField.setText(bank.getBankName());
            locationField.setText(bank.getLocation());
            unitsField.setText(String.valueOf(bank.getUnitsAvailable()));
            phoneField.setText(bank.getPhone());
        }
    }

    private void issueBlood() {
        String group = groupField.getText().trim().toUpperCase();
        boolean issued = AppData.bloodBankManager.issueBlood(group, 1);
        if (issued) {
            AppData.saveAll();
            displayArea.setText("One blood unit issued for group " + group + ".\n" + AppData.bloodBankManager.searchBloodGroup(group));
        } else {
            JOptionPane.showMessageDialog(this, "Blood group not found or units are not available.");
        }
    }

    private BloodBank readBloodBankFromFields() {
        String group = groupField.getText().trim().toUpperCase();
        String bank = bankField.getText().trim();
        String location = locationField.getText().trim();
        int units = Integer.parseInt(unitsField.getText().trim());
        String phone = phoneField.getText().trim();
        if (group.isEmpty() || bank.isEmpty() || location.isEmpty()) {
            throw new IllegalArgumentException("Required field is empty.");
        }
        return new BloodBank(group, bank, location, units, phone);
    }

    private void clearFields() {
        groupField.setText("");
        bankField.setText("");
        locationField.setText("");
        unitsField.setText("");
        phoneField.setText("");
    }
}
