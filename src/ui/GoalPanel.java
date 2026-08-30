package ui;

import model.Goal;
import model.GoalStatus;
import model.User;
import service.GoalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GoalPanel extends JPanel {

    private User currentUser;

    private GoalService goalService;

    private JLabel lblName;
    private JLabel lblTarget;
    private JLabel lblSaved;
    private JLabel lblStatus;

    private JTextField txtName;
    private JTextField txtTarget;
    private JTextField txtSaved;

    private JComboBox<GoalStatus> cmbStatus;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnDeposit;
    private JButton btnClear;

    private JTable table;

    private DefaultTableModel tableModel;

    private int selectedGoalId = -1;

    public GoalPanel(User user) {

        currentUser = user;

        goalService = new GoalService();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        loadGoals();

    }

    private void initializeComponents() {

        lblName = new JLabel("Goal Name");

        lblTarget = new JLabel("Target Amount");

        lblSaved = new JLabel("Saved Amount");

        lblStatus = new JLabel("Status");

        txtName = new JTextField();

        txtTarget = new JTextField();

        txtSaved = new JTextField();

        txtSaved.setText("0");

        cmbStatus =
                new JComboBox<>(GoalStatus.values());

        btnAdd = new JButton("Add");

        btnUpdate = new JButton("Update");

        btnDelete = new JButton("Delete");

        btnDeposit = new JButton("Deposit");

        btnClear = new JButton("Clear");

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{

                "ID",
                "Goal",
                "Target",
                "Saved",
                "Progress",
                "Status"

        });

        table = new JTable(tableModel);

    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Goal Information"));

        formPanel.setLayout(new GridLayout(4, 2, 5, 5));

        formPanel.add(lblName);
        formPanel.add(txtName);

        formPanel.add(lblTarget);
        formPanel.add(txtTarget);

        formPanel.add(lblSaved);
        formPanel.add(txtSaved);

        formPanel.add(lblStatus);
        formPanel.add(cmbStatus);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnDeposit);
        buttonPanel.add(btnClear);

        JPanel northPanel =
                new JPanel(new BorderLayout());

        northPanel.add(formPanel,
                BorderLayout.CENTER);

        northPanel.add(buttonPanel,
                BorderLayout.SOUTH);

        add(northPanel,
                BorderLayout.NORTH);

        add(new JScrollPane(table),
                BorderLayout.CENTER);

    }

    private void initializeEvents() {

        btnAdd.addActionListener(e -> addGoal());

        btnUpdate.addActionListener(e -> updateGoal());

        btnDelete.addActionListener(e -> deleteGoal());

        btnDeposit.addActionListener(e -> deposit());

        btnClear.addActionListener(e -> clearFields());

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        fillFields();

                    }

                });

    }

    private void loadGoals() {

        tableModel.setRowCount(0);

        List<Goal> goals =
                goalService.getGoals(currentUser);

        for (Goal goal : goals) {

            double progress =
                    goalService.getProgressPercent(goal);

            tableModel.addRow(new Object[]{

                    goal.getId(),
                    goal.getName(),
                    goal.getTargetAmount(),
                    goal.getSavedAmount(),
                    String.format("%.2f%%", progress),
                    goal.getStatus()

            });

        }

    }

    private void addGoal() {

        try {

            String name = txtName.getText().trim();

            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter goal name."
                );

                return;
            }

            double targetAmount =
                    Double.parseDouble(
                            txtTarget.getText());

            double savedAmount =
                    Double.parseDouble(
                            txtSaved.getText());

            GoalStatus status =
                    (GoalStatus) cmbStatus.getSelectedItem();

            if (targetAmount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Target amount must be greater than zero."
                );

                return;
            }

            if (savedAmount < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Saved amount cannot be negative."
                );

                return;
            }

            if (savedAmount > targetAmount) {

                JOptionPane.showMessageDialog(
                        this,
                        "Saved amount cannot be greater than target amount."
                );

                return;
            }

            Goal goal = new Goal(
                    0,
                    currentUser,
                    name,
                    targetAmount,
                    savedAmount,
                    status
            );

            if (savedAmount >= targetAmount) {

                goal.setSavedAmount(targetAmount);

                goal.setStatus(
                        GoalStatus.COMPLETED);

            }

            goalService.addGoal(goal);

            JOptionPane.showMessageDialog(
                    this,
                    "Goal added successfully."
            );

            clearFields();

            loadGoals();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    private void updateGoal() {

        if (selectedGoalId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a goal."
            );

            return;
        }

        try {

            Goal goal =
                    goalService.getGoalById(
                            selectedGoalId);

            if (goal == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Goal not found."
                );

                return;
            }

            String name =
                    txtName.getText().trim();

            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter goal name."
                );

                return;
            }

            double targetAmount =
                    Double.parseDouble(
                            txtTarget.getText());

            double savedAmount =
                    Double.parseDouble(
                            txtSaved.getText());

            GoalStatus status =
                    (GoalStatus) cmbStatus.getSelectedItem();

            if (targetAmount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Target amount must be greater than zero."
                );

                return;
            }

            if (savedAmount < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Saved amount cannot be negative."
                );

                return;
            }

            if (savedAmount > targetAmount) {

                JOptionPane.showMessageDialog(
                        this,
                        "Saved amount cannot be greater than target amount."
                );

                return;
            }

            goal.setName(name);

            goal.setTargetAmount(targetAmount);

            goal.setSavedAmount(savedAmount);

            goal.setStatus(status);

            if (savedAmount >= targetAmount) {

                goal.setSavedAmount(targetAmount);

                goal.setStatus(
                        GoalStatus.COMPLETED);

            }

            goalService.updateGoal(goal);

            JOptionPane.showMessageDialog(
                    this,
                    "Goal updated successfully."
            );

            clearFields();

            loadGoals();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    private void deleteGoal() {

        if (selectedGoalId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a goal."
            );

            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this goal?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            goalService.deleteGoal(
                    selectedGoalId);

            JOptionPane.showMessageDialog(
                    this,
                    "Goal deleted successfully."
            );

            clearFields();

            loadGoals();

        }

    }

    private void deposit() {

        if (selectedGoalId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a goal."
            );

            return;
        }

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter amount to deposit:"
                );

        if (input == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(input);

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Deposit amount must be greater than zero."
                );

                return;
            }

            Goal goal =
                    goalService.getGoalById(
                            selectedGoalId);

            if (goal == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Goal not found."
                );

                return;
            }

            if (goal.getStatus() ==
                    GoalStatus.COMPLETED) {

                JOptionPane.showMessageDialog(
                        this,
                        "This goal is already completed."
                );

                return;
            }

            goalService.deposit(
                    selectedGoalId,
                    amount);

            JOptionPane.showMessageDialog(
                    this,
                    "Deposit added successfully."
            );

            clearFields();

            loadGoals();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );

        }

    }

    private void fillFields() {

        int row = table.getSelectedRow();

        if (row == -1) {
            return;
        }

        selectedGoalId =
                (Integer) tableModel.getValueAt(row, 0);

        Goal goal =
                goalService.getGoalById(selectedGoalId);

        if (goal == null) {
            return;
        }

        txtName.setText(goal.getName());

        txtTarget.setText(
                String.valueOf(goal.getTargetAmount()));

        txtSaved.setText(
                String.valueOf(goal.getSavedAmount()));

        cmbStatus.setSelectedItem(
                goal.getStatus());
    }

    private void clearFields() {

        selectedGoalId = -1;

        txtName.setText("");

        txtTarget.setText("");

        txtSaved.setText("0");

        cmbStatus.setSelectedItem(
                GoalStatus.ACTIVE);

        table.clearSelection();
    }
}