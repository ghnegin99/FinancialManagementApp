package ui;

import model.User;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtPhone;

    private JButton btnRegister;
    private JButton btnBack;

    private AuthenticationService authenticationService;

    public RegisterFrame() {

        authenticationService =
                new AuthenticationService();

        initializeFrame();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Register");

        setSize(500, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {

        txtUsername =
                new JTextField();

        txtPassword =
                new JPasswordField();

        txtConfirmPassword =
                new JPasswordField();

        txtFullName =
                new JTextField();

        txtEmail =
                new JTextField();

        txtPhone =
                new JTextField();

        btnRegister =
                new JButton("Register");

        btnBack =
                new JButton("Back to Login");
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


        // Confirm password

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(
                new JLabel("Confirm Password:"),
                gbc);

        gbc.gridx = 1;

        panel.add(
                txtConfirmPassword,
                gbc);


        // Full name

        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(
                new JLabel("Full Name:"),
                gbc);

        gbc.gridx = 1;

        panel.add(
                txtFullName,
                gbc);


        // Email

        gbc.gridx = 0;
        gbc.gridy = 4;

        panel.add(
                new JLabel("Email:"),
                gbc);

        gbc.gridx = 1;

        panel.add(
                txtEmail,
                gbc);


        // Phone

        gbc.gridx = 0;
        gbc.gridy = 5;

        panel.add(
                new JLabel("Phone:"),
                gbc);

        gbc.gridx = 1;

        panel.add(
                txtPhone,
                gbc);


        // Register button

        gbc.gridx = 0;
        gbc.gridy = 6;

        panel.add(
                btnRegister,
                gbc);


        // Back button

        gbc.gridx = 1;

        panel.add(
                btnBack,
                gbc);


        add(panel);
    }

    private void initializeEvents() {

        btnRegister.addActionListener(
                e -> register());

        btnBack.addActionListener(
                e -> backToLogin());
    }

    private void register() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword());

        String confirmPassword =
                new String(
                        txtConfirmPassword.getPassword());

        String fullName =
                txtFullName.getText().trim();

        String email =
                txtEmail.getText().trim();

        String phone =
                txtPhone.getText().trim();


        // Empty fields

        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || fullName.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.");

            return;
        }


        // Password confirmation

        if (!password.equals(
                confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.");

            return;
        }


        User user =
                new User(
                        0,
                        username,
                        password,
                        fullName,
                        email,
                        phone);


        boolean registered =
                authenticationService.register(user);


        if (!registered) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username already exists.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);


        backToLogin();
    }

    private void backToLogin() {

        dispose();

        new LoginFrame();
    }
}