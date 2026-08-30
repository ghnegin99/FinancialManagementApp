package ui;

import model.User;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnRegister;

    private AuthenticationService authenticationService;

    public LoginFrame() {

        authenticationService =
                new AuthenticationService();

        initializeFrame();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Login");

        setSize(400, 300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {

        txtUsername =
                new JTextField();

        txtPassword =
                new JPasswordField();

        btnLogin =
                new JButton("Login");

        btnRegister =
                new JButton("Register");
    }

    private void initializeLayout() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // Username

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(
                new JLabel("Username:"),
                gbc);


        gbc.gridx = 1;

        panel.add(
                txtUsername,
                gbc);


        // Password

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(
                new JLabel("Password:"),
                gbc);


        gbc.gridx = 1;

        panel.add(
                txtPassword,
                gbc);


        // Login button

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(
                btnLogin,
                gbc);


        // Register button

        gbc.gridx = 1;

        panel.add(
                btnRegister,
                gbc);


        add(panel);
    }

    private void initializeEvents() {

        btnLogin.addActionListener(
                e -> login());

        btnRegister.addActionListener(
                e -> openRegister());
    }

    private void login() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword());


        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.");

            return;
        }


        User user =
                authenticationService.login(
                        username,
                        password);


        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Login successful!");


        dispose();

        new DashboardFrame(user);
    }

    private void openRegister() {

        dispose();

        new RegisterFrame();
    }
}