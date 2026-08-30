package validation;

import model.Budget;

public class BudgetValidator {

    public static String validate(Budget budget) {

        if (budget.getCategory() == null) {

            return "Category is required.";

        }

        if (budget.getMonth() == null) {

            return "Month is required.";

        }

        if (budget.getLimitAmount() <= 0) {

            return "Budget amount must be greater than zero.";

        }

        return null;

    }

}