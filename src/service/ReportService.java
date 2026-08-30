package service;

import model.Budget;
import model.Category;
import model.Goal;
import model.Transaction;
import model.TransactionType;
import model.User;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    private TransactionService transactionService =
            new TransactionService();

    private BudgetService budgetService =
            new BudgetService();

    private GoalService goalService =
            new GoalService();

    /**
     * موجودی حساب
     */
    public double getBalance(User user) {

        return transactionService.getBalance(user);

    }

    /**
     * مجموع درآمد
     */
    public double getTotalIncome(User user) {

        return transactionService.getTotalIncome(user);

    }

    /**
     * مجموع هزینه
     */
    public double getTotalExpense(User user) {

        return transactionService.getTotalExpense(user);

    }

    /**
     * درصد مصرف بودجه
     */
    public double getBudgetUsage(User user,
                                 Category category,
                                 YearMonth month) {

        return budgetService.getBudgetUsagePercent(
                user,
                category,
                month);

    }

    /**
     * مبلغ باقی مانده بودجه
     */
    public double getRemainingBudget(User user,
                                     Category category,
                                     YearMonth month) {

        return budgetService.getRemainingBudget(
                user,
                category,
                month);

    }

    /**
     * آیا بودجه رد شده؟
     */
    public boolean isBudgetExceeded(User user,
                                    Category category,
                                    YearMonth month) {

        return budgetService.isBudgetExceeded(
                user,
                category,
                month);

    }

    /**
     * درصد پیشرفت هدف
     */
    public double getGoalProgress(Goal goal) {

        return goalService.getProgressPercent(goal);

    }

    /**
     * مبلغ باقی مانده هدف
     */
    public double getGoalRemaining(Goal goal) {

        return goalService.getRemainingAmount(goal);

    }

    /**
     * تراکنش‌های یک بازه زمانی
     */
    public List<Transaction> getTransactionsBetweenDates(
            User user,
            LocalDate startDate,
            LocalDate endDate) {

        List<Transaction> result = new ArrayList<>();

        List<Transaction> transactions =
                transactionService.getTransactions(user);

        for (Transaction transaction : transactions) {

            if (!transaction.getDate().isBefore(startDate)
                    && !transaction.getDate().isAfter(endDate)) {

                result.add(transaction);

            }

        }

        return result;

    }

    /**
     * مجموع درآمد در بازه زمانی
     */
    public double getIncomeBetweenDates(User user,
                                        LocalDate startDate,
                                        LocalDate endDate) {

        double total = 0;

        for (Transaction transaction :
                getTransactionsBetweenDates(user,
                        startDate,
                        endDate)) {

            if (transaction.getType() ==
                    TransactionType.INCOME) {

                total += transaction.getAmount();

            }

        }

        return total;

    }

    /**
     * مجموع هزینه در بازه زمانی
     */
    public double getExpenseBetweenDates(User user,
                                         LocalDate startDate,
                                         LocalDate endDate) {

        double total = 0;

        for (Transaction transaction :
                getTransactionsBetweenDates(user,
                        startDate,
                        endDate)) {

            if (transaction.getType() ==
                    TransactionType.EXPENSE) {

                total += transaction.getAmount();

            }

        }

        return total;

    }

}