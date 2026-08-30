package repository;

import model.Budget;
import model.Category;
import model.User;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepository {

    private static final String FILE_PATH = "data/budgets.txt";

    private UserRepository userRepository = new UserRepository();
    private CategoryRepository categoryRepository = new CategoryRepository();

    public List<Budget> getAllBudgets() {

        List<Budget> budgets = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return budgets;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                User user = userRepository.findById(
                        Integer.parseInt(data[1]));

                Category category = categoryRepository.findById(
                        Integer.parseInt(data[2]));

                Budget budget = new Budget(
                        Integer.parseInt(data[0]),
                        user,
                        category,
                        YearMonth.parse(data[3]),
                        Double.parseDouble(data[4])
                );

                budgets.add(budget);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return budgets;
    }

    public void addBudget(Budget budget) {

        List<Budget> budgets = getAllBudgets();

        budgets.add(budget);

        saveAllBudgets(budgets);

    }

    public Budget findById(int id) {

        for (Budget budget : getAllBudgets()) {

            if (budget.getId() == id) {
                return budget;
            }

        }

        return null;
    }

    public List<Budget> getBudgetsByUser(User user) {

        List<Budget> result = new ArrayList<>();

        for (Budget budget : getAllBudgets()) {

            if (budget.getUser().getId() == user.getId()) {

                result.add(budget);

            }

        }

        return result;
    }

    public void updateBudget(Budget updatedBudget) {

        List<Budget> budgets = getAllBudgets();

        for (int i = 0; i < budgets.size(); i++) {

            if (budgets.get(i).getId() == updatedBudget.getId()) {

                budgets.set(i, updatedBudget);

                break;

            }

        }

        saveAllBudgets(budgets);

    }

    public void deleteBudget(int id) {

        List<Budget> budgets = getAllBudgets();

        budgets.removeIf(budget -> budget.getId() == id);

        saveAllBudgets(budgets);

    }

    private void saveAllBudgets(List<Budget> budgets) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Budget budget : budgets) {

                writer.write(
                        budget.getId() + "," +
                                budget.getUser().getId() + "," +
                                budget.getCategory().getId() + "," +
                                budget.getMonth() + "," +
                                budget.getLimitAmount()
                );

                writer.newLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Budget findByUserCategoryAndMonth(User user,
                                             Category category,
                                             YearMonth month) {

        for (Budget budget : getAllBudgets()) {

            if (budget.getUser().getId() == user.getId()
                    && budget.getCategory().getId() == category.getId()
                    && budget.getMonth().equals(month)) {

                return budget;

            }

        }

        return null;

    }
    public int getNextId() {

        int max = 0;

        List<Budget> budgets =
                getAllBudgets();

        for (Budget budget : budgets) {

            if (budget.getId() > max) {

                max = budget.getId();

            }

        }

        return max + 1;

    }

}