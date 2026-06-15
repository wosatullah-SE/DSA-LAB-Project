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
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel());

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if(username.equals("admin") &&
                password.equals("admin123")) {

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