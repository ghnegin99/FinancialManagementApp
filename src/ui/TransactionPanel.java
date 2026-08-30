package ui;

import model.Category;
import model.Transaction;
import model.TransactionType;
import model.User;
import service.CategoryService;
import service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TransactionPanel extends JPanel {

    private User currentUser;

    private JLabel lblTitle;
    private JLabel lblAmount;
    private JLabel lblType;
    private JLabel lblCategory;
    private JLabel lblDate;
    private JLabel lblDescription;

    private JTextField txtTitle;
    private JTextField txtAmount;
    private JTextField txtDate;

    private JTextArea txtDescription;

    private JComboBox<TransactionType> cmbType;
    private JComboBox<Category> cmbCategory;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;

    private DefaultTableModel tableModel;

    private TransactionService transactionService;
    private CategoryService categoryService;

    private int selectedTransactionId = -1;

    public TransactionPanel(User user) {

        this.currentUser = user;

        transactionService = new TransactionService();
        categoryService = new CategoryService();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        loadCategories();

        loadTransactions();

    }

    private void initializeComponents() {

        lblTitle = new JLabel("Title");
        lblAmount = new JLabel("Amount");
        lblType = new JLabel("Type");
        lblCategory = new JLabel("Category");
        lblDate = new JLabel("Date (yyyy-MM-dd)");
        lblDescription = new JLabel("Description");

        txtTitle = new JTextField();

        txtAmount = new JTextField();

        txtDate = new JTextField();

        txtDate.setText(LocalDate.now().toString());

        txtDescription = new JTextArea(3, 20);

        cmbType = new JComboBox<>(TransactionType.values());

        cmbCategory = new JComboBox<>();

        btnAdd = new JButton("Add");

        btnUpdate = new JButton("Update");

        btnDelete = new JButton("Delete");

        btnClear = new JButton("Clear");

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{

                "ID",
                "Title",
                "Amount",
                "Type",
                "Category",
                "Date"

        });

        table = new JTable(tableModel);

    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();

        formPanel.setBorder(
                BorderFactory.createTitledBorder("Transaction Information"));

        formPanel.setLayout(new GridLayout(7, 2, 5, 5));

        formPanel.add(lblTitle);
        formPanel.add(txtTitle);

        formPanel.add(lblAmount);
        formPanel.add(txtAmount);

        formPanel.add(lblType);
        formPanel.add(cmbType);

        formPanel.add(lblCategory);
        formPanel.add(cmbCategory);

        formPanel.add(lblDate);
        formPanel.add(txtDate);

        formPanel.add(lblDescription);
        formPanel.add(new JScrollPane(txtDescription));

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnAdd);
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

        btnAdd.addActionListener(e -> addTransaction());

        btnUpdate.addActionListener(e -> updateTransaction());

        btnDelete.addActionListener(e -> deleteTransaction());

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

    private void loadTransactions() {

        tableModel.setRowCount(0);

        List<Transaction> transactions =
                transactionService.getTransactions(currentUser);

        for (Transaction transaction : transactions) {

            tableModel.addRow(new Object[]{

                    transaction.getId(),
                    transaction.getTitle(),
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getCategory().getName(),
                    transaction.getDate()

            });

        }

    }

    private void addTransaction() {

        try {

            String title = txtTitle.getText();

            double amount =
                    Double.parseDouble(txtAmount.getText());

            TransactionType type =
                    (TransactionType) cmbType.getSelectedItem();

            Category category =
                    (Category) cmbCategory.getSelectedItem();

            LocalDate date =
                    LocalDate.parse(txtDate.getText());

            String description =
                    txtDescription.getText();

            Transaction transaction =
                    new Transaction(
                            0,
                            currentUser,
                            title,
                            amount,
                            type,
                            category,
                            date,
                            description
                    );

            transactionService.addTransaction(transaction);

            JOptionPane.showMessageDialog(this,
                    "Transaction added successfully.");

            clearFields();

            loadTransactions();

        }
        catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

    private void updateTransaction() {

        if (selectedTransactionId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a transaction.");

            return;

        }

        try {

            Transaction transaction =
                    transactionService.getTransactionById(
                            selectedTransactionId);

            transaction.setTitle(txtTitle.getText());

            transaction.setAmount(
                    Double.parseDouble(txtAmount.getText()));

            transaction.setType(
                    (TransactionType) cmbType.getSelectedItem());

            transaction.setCategory(
                    (Category) cmbCategory.getSelectedItem());

            transaction.setDate(
                    LocalDate.parse(txtDate.getText()));

            transaction.setDescription(
                    txtDescription.getText());

            transactionService.updateTransaction(transaction);

            JOptionPane.showMessageDialog(this,
                    "Transaction updated.");

            clearFields();

            loadTransactions();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

    private void deleteTransaction() {

        if (selectedTransactionId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a transaction.");

            return;

        }

        int result = JOptionPane.showConfirmDialog(

                this,
                "Delete this transaction?",
                "Confirm",
                JOptionPane.YES_NO_OPTION

        );

        if (result == JOptionPane.YES_OPTION) {

            transactionService.deleteTransaction(
                    selectedTransactionId);

            clearFields();

            loadTransactions();

        }

    }

    private void fillFieldsFromTable() {

        int row = table.getSelectedRow();

        if (row == -1) {
            return;
        }

        selectedTransactionId =
                (Integer) tableModel.getValueAt(row, 0);

        Transaction transaction =
                transactionService.getTransactionById(
                        selectedTransactionId);

        if (transaction == null) {
            return;
        }

        txtTitle.setText(transaction.getTitle());

        txtAmount.setText(
                String.valueOf(transaction.getAmount()));

        txtDate.setText(
                transaction.getDate().toString());

        txtDescription.setText(
                transaction.getDescription());

        cmbType.setSelectedItem(
                transaction.getType());

        cmbCategory.setSelectedItem(
                transaction.getCategory());

    }

    private void clearFields() {

        selectedTransactionId = -1;

        txtTitle.setText("");

        txtAmount.setText("");

        txtDate.setText(
                LocalDate.now().toString());

        txtDescription.setText("");

        cmbType.setSelectedIndex(0);

        if (cmbCategory.getItemCount() > 0) {

            cmbCategory.setSelectedIndex(0);

        }

        table.clearSelection();

    }
}