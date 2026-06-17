import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("SwiftAid - Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("SwiftAid Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel usernameLabel = new JLabel("Username: ");

        usernameField = new JTextField(15);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        mainPanel.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel passwordLabel = new JLabel("Password: ");

        passwordField = new JPasswordField(15);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        mainPanel.add(passwordPanel);

        mainPanel.add(Box.createVerticalStrut(15));

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.addActionListener(this);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(loginButton);

        add(mainPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.equals("admin")
                && password.equals("admin123")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful"
            );

            dispose();

            new PatientFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password"
            );
        }
    }
}