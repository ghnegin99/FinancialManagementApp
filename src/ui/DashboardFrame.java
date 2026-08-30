package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private User currentUser;

    private JLabel lblTitle;
    private JLabel lblWelcome;

    private JTabbedPane tabbedPane;

    private TransactionPanel transactionPanel;
    private BudgetPanel budgetPanel;
    private GoalPanel goalPanel;
    private ReportPanel reportPanel;

    public DashboardFrame(User user) {

        this.currentUser = user;

        initializeFrame();

        initializeComponents();

        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Smart Finance System");

        setSize(900, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    private void initializeComponents() {

        // -------------------------
        // Top section
        // -------------------------

        JPanel topPanel =
                new JPanel(
                        new GridLayout(2, 1));

        lblTitle =
                new JLabel(
                        "SMART FINANCE SYSTEM");

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));

        lblWelcome =
                new JLabel(
                        "Welcome : "
                                + currentUser.getUsername());

        lblWelcome.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblWelcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16));

        topPanel.add(lblTitle);

        topPanel.add(lblWelcome);

        add(
                topPanel,
                BorderLayout.NORTH);


        // -------------------------
        // Tabs
        // -------------------------

        tabbedPane =
                new JTabbedPane();


        transactionPanel =
                new TransactionPanel(
                        currentUser);

        budgetPanel =
                new BudgetPanel(
                        currentUser);

        goalPanel =
                new GoalPanel(
                        currentUser);

        reportPanel =
                new ReportPanel(
                        currentUser);


        tabbedPane.addTab(
                "Transactions",
                transactionPanel);

        tabbedPane.addTab(
                "Budgets",
                budgetPanel);

        tabbedPane.addTab(
                "Goals",
                goalPanel);

        tabbedPane.addTab(
                "Reports",
                reportPanel);


        add(
                tabbedPane,
                BorderLayout.CENTER);
    }
}