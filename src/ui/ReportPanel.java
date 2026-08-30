package ui;

import model.Budget;
import model.User;
import service.BudgetService;
import service.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.util.List;

public class ReportPanel extends JPanel {

    private User currentUser;

    private TransactionService transactionService;
    private BudgetService budgetService;

    private JLabel lblIncome;
    private JLabel lblExpense;
    private JLabel lblBalance;
    private JLabel lblBudgetCount;

    private JTextField txtMonth;

    private JButton btnRefresh;

    private JTextArea txtReport;

    public ReportPanel(User user) {

        currentUser = user;

        transactionService =
                new TransactionService();

        budgetService =
                new BudgetService();

        initializeComponents();

        initializeLayout();

        initializeEvents();

        loadReport();

    }

    private void initializeComponents() {

        lblIncome =
                new JLabel("Total Income: 0");

        lblExpense =
                new JLabel("Total Expense: 0");

        lblBalance =
                new JLabel("Balance: 0");

        lblBudgetCount =
                new JLabel("Budgets: 0");

        txtMonth =
                new JTextField(
                        YearMonth.now().toString());

        btnRefresh =
                new JButton("Refresh");

        txtReport =
                new JTextArea();

        txtReport.setEditable(false);

        txtReport.setFont(
                new Font("Monospaced",
                        Font.PLAIN,
                        14));

    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(2, 2, 10, 10));

        summaryPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Financial Summary"));

        summaryPanel.add(lblIncome);

        summaryPanel.add(lblExpense);

        summaryPanel.add(lblBalance);

        summaryPanel.add(lblBudgetCount);

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT));

        filterPanel.add(
                new JLabel("Month:"));

        filterPanel.add(txtMonth);

        filterPanel.add(btnRefresh);

        JPanel northPanel =
                new JPanel(
                        new BorderLayout());

        northPanel.add(
                summaryPanel,
                BorderLayout.CENTER);

        northPanel.add(
                filterPanel,
                BorderLayout.SOUTH);

        add(
                northPanel,
                BorderLayout.NORTH);

        add(
                new JScrollPane(txtReport),
                BorderLayout.CENTER);

    }

    private void initializeEvents() {

        btnRefresh.addActionListener(
                e -> loadReport());

    }

    private void loadReport() {

        try {

            double income =
                    transactionService
                            .getTotalIncome(currentUser);

            double expense =
                    transactionService
                            .getTotalExpense(currentUser);

            double balance =
                    transactionService
                            .getBalance(currentUser);

            List<Budget> budgets =
                    budgetService
                            .getBudgets(currentUser);

            lblIncome.setText(
                    "Total Income: "
                            + String.format("%.2f", income));

            lblExpense.setText(
                    "Total Expense: "
                            + String.format("%.2f", expense));

            lblBalance.setText(
                    "Balance: "
                            + String.format("%.2f", balance));

            lblBudgetCount.setText(
                    "Budgets: "
                            + budgets.size());

            generateReport(
                    income,
                    expense,
                    balance,
                    budgets);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());

        }

    }

    private void generateReport(
            double income,
            double expense,
            double balance,
            List<Budget> budgets) {

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========== FINANCIAL REPORT ==========\n\n");

        report.append(
                        "User: ")
                .append(currentUser.getUsername())
                .append("\n");

        report.append(
                        "Month: ")
                .append(txtMonth.getText())
                .append("\n\n");

        report.append(
                        "Total Income : ")
                .append(
                        String.format("%.2f", income))
                .append("\n");

        report.append(
                        "Total Expense: ")
                .append(
                        String.format("%.2f", expense))
                .append("\n");

        report.append(
                        "Balance      : ")
                .append(
                        String.format("%.2f", balance))
                .append("\n\n");

        report.append(
                "--------------- BUDGETS ---------------\n\n");

        for (Budget budget : budgets) {

            if (!budget.getMonth().toString()
                    .equals(txtMonth.getText())) {

                continue;
            }

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

            double usage =
                    budgetService.getBudgetUsagePercent(
                            currentUser,
                            budget.getCategory(),
                            budget.getMonth());

            report.append(
                            "Category : ")
                    .append(
                            budget.getCategory().getName())
                    .append("\n");

            report.append(
                            "Limit    : ")
                    .append(
                            String.format(
                                    "%.2f",
                                    budget.getLimitAmount()))
                    .append("\n");

            report.append(
                            "Spent    : ")
                    .append(
                            String.format(
                                    "%.2f",
                                    spent))
                    .append("\n");

            report.append(
                            "Remaining: ")
                    .append(
                            String.format(
                                    "%.2f",
                                    remaining))
                    .append("\n");

            report.append(
                            "Usage    : ")
                    .append(
                            String.format(
                                    "%.2f%%",
                                    usage))
                    .append("\n");

            report.append(
                    "----------------------------------------\n");

        }

        txtReport.setText(
                report.toString());

    }

}