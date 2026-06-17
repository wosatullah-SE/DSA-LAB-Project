import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("SwiftAid Dashboard");

        setSize(600, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(7,1,10,10));

        JLabel title =
                new JLabel(
                        "SwiftAid Control Room Dashboard",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        JButton patientButton =
                new JButton(
                        "Patient Management"
                );

        JButton hospitalButton =
                new JButton(
                        "Hospital Management"
                );

        JButton bloodButton =
                new JButton(
                        "Blood Bank Management"
                );

        JButton emergencyButton =
                new JButton(
                        "Emergency Management (Phase 3)"
                );

        JButton ambulanceButton =
                new JButton(
                        "Ambulance Dispatch (Phase 4)"
                );

        JButton routeButton =
                new JButton(
                        "Route Optimization (Phase 5)"
                );

        add(title);

        add(patientButton);

        add(hospitalButton);

        add(bloodButton);

        add(emergencyButton);

        add(ambulanceButton);

        add(routeButton);

        patientButton.addActionListener(
                e -> new PatientFrame()
        );

        hospitalButton.addActionListener(
                e -> new HospitalFrame()
        );

        bloodButton.addActionListener(
                e -> new BloodBankFrame()
        );

        emergencyButton.addActionListener(
                e ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Available in Phase 3"
                        )
        );

        ambulanceButton.addActionListener(
                e ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Available in Phase 4"
                        )
        );

        routeButton.addActionListener(
                e ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Available in Phase 5"
                        )
        );

        setVisible(true);
    }
}