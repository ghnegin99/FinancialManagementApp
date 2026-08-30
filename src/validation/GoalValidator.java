package validation;

import model.Goal;

public class GoalValidator {

    public static String validate(Goal goal) {

        if (goal.getName() == null ||
                goal.getName().trim().isEmpty()) {

            return "Goal name cannot be empty.";

        }

        if (goal.getTargetAmount() <= 0) {

            return "Target amount must be greater than zero.";

        }

        if (goal.getSavedAmount() < 0) {

            return "Saved amount cannot be negative.";

        }

        return null;

    }

}