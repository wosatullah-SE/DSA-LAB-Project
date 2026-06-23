import javax.swing.*;
import java.awt.*;

public class EmergencyPanel extends JPanel {
    private JTextField caseIdField;
    private JTextField patientIdField;
    private JTextField locationField;
    private JTextField conditionField;
    private JTextField severityField;
    private JTextField bloodField;
    private JTextField completeCaseField;
    private JTextArea displayArea;

    public EmergencyPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Emergency Dispatch - Max Heap Priority Queue + Linked List History");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        caseIdField = new JTextField();
        patientIdField = new JTextField();
        locationField = new JTextField("Emergency Spot 1");
        conditionField = new JTextField();
        severityField = new JTextField("8");
        bloodField = new JTextField();
        completeCaseField = new JTextField();

        inputPanel.add(new JLabel("Case ID"));
        inputPanel.add(caseIdField);
        inputPanel.add(new JLabel("Patient ID"));
        inputPanel.add(patientIdField);
        inputPanel.add(new JLabel("Emergency Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Condition"));
        inputPanel.add(conditionField);
        inputPanel.add(new JLabel("Severity 1-10"));
        inputPanel.add(severityField);
        inputPanel.add(new JLabel("Required Blood Group"));
        inputPanel.add(bloodField);
        inputPanel.add(new JLabel("Complete Case ID"));
        inputPanel.add(completeCaseField);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton registerButton = new JButton("Register Emergency");
        JButton dispatchButton = new JButton("Dispatch Highest Priority");
        JButton completeButton = new JButton("Complete Case");
        JButton waitingButton = new JButton("Show Waiting Heap");
        JButton historyButton = new JButton("Show History");
        JButton saveButton = new JButton("Save");

        registerButton.addActionListener(e -> registerEmergency());
        dispatchButton.addActionListener(e -> dispatchHighestPriorityCase());
        completeButton.addActionListener(e -> completeCase());
        waitingButton.addActionListener(e -> displayArea.setText(AppData.emergencyQueue.displayQueue()));
        historyButton.addActionListener(e -> displayArea.setText(AppData.emergencyHistory.displayHistory()));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "Emergency data saved.");
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(dispatchButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(waitingButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void registerEmergency() {
        try {
            int caseId = Integer.parseInt(caseIdField.getText().trim());
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            Patient patient = AppData.patientManager.searchPatient(patientId);
            if (patient == null) {
                JOptionPane.showMessageDialog(this, "Patient ID not found. Add patient first.");
                return;
            }

            String location = locationField.getText().trim();
            String condition = conditionField.getText().trim();
            int severity = Integer.parseInt(severityField.getText().trim());
            String bloodGroup = bloodField.getText().trim().toUpperCase();
            if (bloodGroup.isEmpty()) {
                bloodGroup = patient.getBloodGroup();
            }
            if (severity < 1 || severity > 10 || location.isEmpty() || condition.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Severity must be 1 to 10 and all fields are required.");
                return;
            }
            if (AppData.emergencyHistory.searchByCaseId(caseId) != null) {
                JOptionPane.showMessageDialog(this, "Case ID already exists.");
                return;
            }

            EmergencyCase emergencyCase = new EmergencyCase(caseId, patientId, patient.getName(), location, condition, severity, bloodGroup);
            AppData.emergencyQueue.insert(emergencyCase);
            AppData.emergencyHistory.addCase(emergencyCase);
            AppData.routeGraph.addLocation(location);
            AppData.saveAll();
            displayArea.setText("Emergency case registered in Max-Heap Priority Queue.\n" + emergencyCase);
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Case ID, Patient ID, and Severity must be numeric.");
        }
    }

    private void dispatchHighestPriorityCase() {
        EmergencyCase emergencyCase = AppData.emergencyQueue.extractMax();
        if (emergencyCase == null) {
            displayArea.setText("No waiting emergency case available.");
            return;
        }

        Ambulance ambulance = AppData.ambulanceQueue.getNextAvailableAmbulance();
        if (ambulance == null) {
            AppData.emergencyQueue.insert(emergencyCase);
            displayArea.setText("No ambulance available right now. Case returned to priority queue.");
            return;
        }

        Hospital hospital = AppData.hospitalManager.findHospitalWithBeds();
        if (hospital == null) {
            AppData.emergencyQueue.insert(emergencyCase);
            displayArea.setText("No hospital with available beds. Case returned to priority queue.");
            return;
        }

        ambulance.setAvailable(false);
        hospital.reduceOneBed();
        emergencyCase.assign(ambulance, hospital);

        BloodBank bank = AppData.bloodBankManager.searchBloodGroup(emergencyCase.getRequiredBloodGroup());
        String bloodMessage;
        if (bank != null && bank.getUnitsAvailable() > 0) {
            bank.reduceUnits(1);
            bloodMessage = "One unit of " + emergencyCase.getRequiredBloodGroup() + " blood reserved from " + bank.getBankName() + ".";
        } else {
            bloodMessage = "Blood unit not available immediately; manual follow-up required.";
        }

        RouteGraph.RouteResult ambulanceToPatient = AppData.routeGraph.findShortestPathDijkstra(
                ambulance.getCurrentLocation(), emergencyCase.getLocation());
        RouteGraph.RouteResult patientToHospital = AppData.routeGraph.findShortestPathDijkstra(
                emergencyCase.getLocation(), hospital.getLocation());

        AppData.saveAll();
        displayArea.setText("Highest priority case dispatched successfully.\n" +
                emergencyCase + "\n\n" +
                "Ambulance to Patient Route:\n" + ambulanceToPatient.pathToString() +
                "\nDistance: " + ambulanceToPatient.getDistance() + " km\n\n" +
                "Patient to Hospital Route:\n" + patientToHospital.pathToString() +
                "\nDistance: " + patientToHospital.getDistance() + " km\n\n" +
                bloodMessage);
    }

    private void completeCase() {
        try {
            int caseId = Integer.parseInt(completeCaseField.getText().trim());
            EmergencyCase emergencyCase = AppData.emergencyHistory.searchByCaseId(caseId);
            if (emergencyCase == null) {
                JOptionPane.showMessageDialog(this, "Case not found.");
                return;
            }
            emergencyCase.complete();
            if (emergencyCase.getAssignedAmbulanceId() != 0) {
                AppData.ambulanceQueue.markAvailable(emergencyCase.getAssignedAmbulanceId(), true);
            }
            AppData.saveAll();
            displayArea.setText("Case completed and ambulance marked available if assigned.\n" + emergencyCase);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numeric case ID.");
        }
    }

    private void clearFields() {
        caseIdField.setText("");
        patientIdField.setText("");
        conditionField.setText("");
        severityField.setText("8");
        bloodField.setText("");
    }
}
