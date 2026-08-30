package ui;

import model.Budget;
import model.Category;
import model.User;
import service.BudgetService;
import service.CategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.YearMonth;
import java.util.List;

public class BudgetPanel extends JPanel {

    private User currentUser;

    private JLabel lblCategory;
    private JLabel lblMonth;
    private JLabel lblLimit;

    private JComboBox<Category> cmbCategory;

    private JTextField txtMonth;
    private JTextField txtLimit;

    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;

    private DefaultTableModel tableModel;

    private BudgetService budgetService;

    private CategoryService categoryService;

    private int selectedBudgetId = -1;

    public BudgetPanel(User user) {

        currentUser = user;

        budgetService = new BudgetService();

        categoryService = new CategoryService();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        loadCategories();

        loadBudgets();

    }

    private void initializeComponents() {

        lblCategory = new JLabel("Category");

        lblMonth = new JLabel("Month (yyyy-MM)");

        lblLimit = new JLabel("Budget Limit");

        cmbCategory = new JComboBox<>();

        txtMonth = new JTextField();

        txtMonth.setText(YearMonth.now().toString());

        txtLimit = new JTextField();

        btnSave = new JButton("Save");

        btnUpdate = new JButton("Update");

        btnDelete = new JButton("Delete");

        btnClear = new JButton("Clear");

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{

                "ID",
                "Category",
                "Month",
                "Limit",
                "Spent",
                "Remaining"

        });

        table = new JTable(tableModel);

    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();

        formPanel.setBorder(
                BorderFactory.createTitledBorder("Budget Information"));

        formPanel.setLayout(new GridLayout(4, 2, 5, 5));

        formPanel.add(lblCategory);

        formPanel.add(cmbCategory);

        formPanel.add(lblMonth);

        formPanel.add(txtMonth);

        formPanel.add(lblLimit);

        formPanel.add(txtLimit);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnSave);

        buttonPanel.add(btnUpdate);

        buttonPanel.add(btnDelete);

        buttonPanel.add(btnClear);

        JPanel northPanel = new JPanel(new BorderLayout());

        northPanel.add(formPanel, BorderLayout.CENTER);

        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

    }

    private void initializeEvents() {

        btnSave.addActionListener(e -> saveBudget());

        btnUpdate.addActionListener(e -> updateBudget());

        btnDelete.addActionListener(e -> deleteBudget());

        btnClear.addActionListener(e -> clearFields());

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                fillFieldsFromTable();

            }

        });

    }

    private void loadCategories() {

        cmbCategory.removeAllItems();

        List<Category> categories =
                categoryService.getAllCategories();

        for (Category category : categories) {

            cmbCategory.addItem(category);

        }

    }

    private void loadBudgets() {

        tableModel.setRowCount(0);

        List<Budget> budgets =
                budgetService.getBudgets(currentUser);

        for (Budget budget : budgets) {

            double spent =
                    budgetService.getSpentAmount(
                            currentUser,
                            budget.getCategory(),
                            budget.getMonth());

            double remaining =
                    budgetService.getRemainingBudget(
                            currentUser,
                            budget.getCategory(),
                            budget.getMonth());

            tableModel.addRow(new Object[]{

                    budget.getId(),
                    budget.getCategory().getName(),
                    budget.getMonth(),
                    budget.getLimitAmount(),
                    spent,
                    remaining

            });

        }

    }

    private void saveBudget() {

        try {

            Category category =
                    (Category) cmbCategory.getSelectedItem();

            YearMonth month =
                    YearMonth.parse(txtMonth.getText());

            double limit =
                    Double.parseDouble(txtLimit.getText());

            Budget budget = new Budget(
                    0,
                    currentUser,
                    category,
                    month,
                    limit
            );

            budgetService.saveBudget(budget);

            JOptionPane.showMessageDialog(this,
                    "Budget saved successfully.");

            clearFields();

            loadBudgets();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

    private void updateBudget() {

        if (selectedBudgetId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a budget.");

            return;

        }

        try {

            Budget budget =
                    budgetService.getBudgetById(
                            selectedBudgetId);

            budget.setCategory(
                    (Category) cmbCategory.getSelectedItem());

            budget.setMonth(
                    YearMonth.parse(txtMonth.getText()));

            budget.setLimitAmount(
                    Double.parseDouble(txtLimit.getText()));

            budgetService.saveBudget(budget);

            JOptionPane.showMessageDialog(this,
                    "Budget updated successfully.");

            clearFields();

            loadBudgets();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

    private void deleteBudget() {

        if (selectedBudgetId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a budget.");

            return;

        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete selected budget?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            budgetService.deleteBudget(
                    selectedBudgetId);

            clearFields();

            loadBudgets();

        }

    }

    private void fillFieldsFromTable() {

        int row = table.getSelectedRow();

        if (row == -1) {
            return;
        }

        selectedBudgetId =
                (Integer) tableModel.getValueAt(row, 0);

        Budget budget =
                budgetService.getBudgetById(selectedBudgetId);

        if (budget == null) {
            return;
        }

        cmbCategory.setSelectedItem(
                budget.getCategory());

        txtMonth.setText(
                budget.getMonth().toString());

        txtLimit.setText(
                String.valueOf(budget.getLimitAmount()));

    }

    private void clearFields() {

        selectedBudgetId = -1;

        txtMonth.setText(
                YearMonth.now().toString());

        txtLimit.setText("");

        if (cmbCategory.getItemCount() > 0) {

            cmbCategory.setSelectedIndex(0);

        }

        table.clearSelection();

    }
}