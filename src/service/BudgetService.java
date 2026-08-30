package service;

import model.*;
import repository.BudgetRepository;
import repository.TransactionRepository;

import java.time.YearMonth;
import java.util.List;

public class BudgetService {

    private BudgetRepository budgetRepository =
            new BudgetRepository();

    private TransactionRepository transactionRepository =
            new TransactionRepository();

    /**
     * ثبت یا ویرایش بودجه
     */
    public void saveBudget(Budget budget) {

        Budget existingBudget =
                budgetRepository.findByUserCategoryAndMonth(
                        budget.getUser(),
                        budget.getCategory(),
                        budget.getMonth());

        if (existingBudget == null) {

            budget.setId(budgetRepository.getNextId());

            budgetRepository.addBudget(budget);

        } else {

            existingBudget.setLimitAmount(
                    budget.getLimitAmount());

            budgetRepository.updateBudget(existingBudget);

        }

    }

    /**
     * حذف بودجه
     */
    public void deleteBudget(int id) {

        budgetRepository.deleteBudget(id);

    }

    /**
     * دریافت تمام بودجه‌های یک کاربر
     */
    public List<Budget> getBudgets(User user) {

        return budgetRepository.getBudgetsByUser(user);

    }

    /**
     * دریافت یک بودجه خاص
     */
    public Budget getBudget(User user,
                            Category category,
                            YearMonth month) {

        return budgetRepository.findByUserCategoryAndMonth(
                user,
                category,
                month);

    }

    /**
     * مجموع هزینه‌های یک دسته در یک ماه
     */
    public double getSpentAmount(User user,
                                 Category category,
                                 YearMonth month) {

        double total = 0;

        List<Transaction> transactions =
                transactionRepository.getTransactionsByUserCategoryAndMonth(
                        user,
                        category,
                        month);

        for (Transaction transaction : transactions) {

            if (transaction.getType() == TransactionType.EXPENSE) {

                total += transaction.getAmount();

            }

        }

        return total;

    }

    /**
     * مبلغ باقی‌مانده از بودجه
     */
    public double getRemainingBudget(User user,
                                     Category category,
                                     YearMonth month) {

        Budget budget =
                budgetRepository.findByUserCategoryAndMonth(
                        user,
                        category,
                        month);

        if (budget == null) {

            return 0;

        }

        return budget.getLimitAmount()
                - getSpentAmount(user, category, month);

    }

    /**
     * درصد استفاده از بودجه
     */
    public double getBudgetUsagePercent(User user,
                                        Category category,
                                        YearMonth month) {

        Budget budget =
                budgetRepository.findByUserCategoryAndMonth(
                        user,
                        category,
                        month);

        if (budget == null) {

            return 0;

        }

        return (getSpentAmount(user, category, month)
                / budget.getLimitAmount()) * 100;

    }

    /**
     * آیا بودجه رد شده است؟
     */
    public boolean isBudgetExceeded(User user,
                                    Category category,
                                    YearMonth month) {

        Budget budget =
                budgetRepository.findByUserCategoryAndMonth(
                        user,
                        category,
                        month);

        if (budget == null) {

            return false;

        }

        return getSpentAmount(user, category, month)
                > budget.getLimitAmount();

    }

    public Budget getBudgetById(int id){

        return budgetRepository.findById(id);

    }
}