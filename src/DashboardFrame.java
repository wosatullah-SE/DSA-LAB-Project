import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashboardFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel centerPanel;

    public DashboardFrame() {
        setTitle("SwiftAid - Smart Emergency Response & Ambulance Dispatch System");
        setSize(1120, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createMenu(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.add(new PatientPanel(), "PATIENTS");
        centerPanel.add(new HospitalPanel(), "HOSPITALS");
        centerPanel.add(new BloodBankPanel(), "BLOOD_BANKS");
        centerPanel.add(new EmergencyPanel(), "EMERGENCIES");
        centerPanel.add(new AmbulancePanel(), "AMBULANCES");
        centerPanel.add(new RoutePanel(), "ROUTES");
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(24, 48, 75));
        header.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel title = new JLabel("SwiftAid Control Room Dashboard", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(title, BorderLayout.CENTER);
        return header;
    }

    private JPanel createMenu() {
        JPanel menu = new JPanel(new GridLayout(8, 1, 8, 8));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        menu.setBackground(new Color(230, 236, 242));

        JButton patientButton = createMenuButton("Patient Records");
        JButton hospitalButton = createMenuButton("Hospital Records");
        JButton bloodButton = createMenuButton("Blood Bank Records");
        JButton emergencyButton = createMenuButton("Emergency Dispatch");
        JButton ambulanceButton = createMenuButton("Ambulance Rotation");
        JButton routeButton = createMenuButton("Route Finder");
        JButton saveButton = createMenuButton("Save All Data");
        JButton exitButton = createMenuButton("Exit");

        patientButton.addActionListener(e -> cardLayout.show(centerPanel, "PATIENTS"));
        hospitalButton.addActionListener(e -> cardLayout.show(centerPanel, "HOSPITALS"));
        bloodButton.addActionListener(e -> cardLayout.show(centerPanel, "BLOOD_BANKS"));
        emergencyButton.addActionListener(e -> cardLayout.show(centerPanel, "EMERGENCIES"));
        ambulanceButton.addActionListener(e -> cardLayout.show(centerPanel, "AMBULANCES"));
        routeButton.addActionListener(e -> cardLayout.show(centerPanel, "ROUTES"));
        saveButton.addActionListener(e -> {
            AppData.saveAll();
            JOptionPane.showMessageDialog(this, "All data saved successfully.");
        });
        exitButton.addActionListener(e -> {
            AppData.saveAll();
            dispose();
        });

        menu.add(patientButton);
        menu.add(hospitalButton);
        menu.add(bloodButton);
        menu.add(emergencyButton);
        menu.add(ambulanceButton);
        menu.add(routeButton);
        menu.add(saveButton);
        menu.add(exitButton);
        return menu;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        return button;
    }
}
