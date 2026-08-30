package util;

import model.Budget;
import model.Goal;
import model.Transaction;
import model.User;
import repository.BudgetRepository;
import repository.GoalRepository;
import repository.TransactionRepository;
import repository.UserRepository;

public class IdGenerator {

    public static int nextUserId() {

        UserRepository repository = new UserRepository();

        int max = 0;

        for (User user : repository.getAllUsers()) {

            if (user.getId() > max) {

                max = user.getId();

            }

        }

        return max + 1;

    }

    public static int nextTransactionId() {

        TransactionRepository repository =
                new TransactionRepository();

        int max = 0;

        for (Transaction transaction :
                repository.getAllTransactions()) {

            if (transaction.getId() > max) {

                max = transaction.getId();

            }

        }

        return max + 1;

    }

    public static int nextBudgetId() {

        BudgetRepository repository =
                new BudgetRepository();

        int max = 0;

        for (Budget budget :
                repository.getAllBudgets()) {

            if (budget.getId() > max) {

                max = budget.getId();

            }

        }

        return max + 1;

    }

    public static int nextGoalId() {

        GoalRepository repository =
                new GoalRepository();

        int max = 0;

        for (Goal goal :
                repository.getAllGoals()) {

            if (goal.getId() > max) {

                max = goal.getId();

            }

        }

        return max + 1;

    }

}