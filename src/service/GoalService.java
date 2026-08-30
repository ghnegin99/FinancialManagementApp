package service;

import model.Goal;
import model.GoalStatus;
import model.User;
import repository.GoalRepository;

import java.util.ArrayList;
import java.util.List;

public class GoalService {

    private GoalRepository goalRepository =
            new GoalRepository();

    /**
     * ثبت هدف جدید
     */
    public void addGoal(Goal goal) {

        goal.setId(goalRepository.getNextId());

        goalRepository.addGoal(goal);

    }

    /**
     * ویرایش هدف
     */
    public void updateGoal(Goal goal) {

        goalRepository.updateGoal(goal);

    }

    /**
     * حذف هدف
     */
    public void deleteGoal(int id) {

        goalRepository.deleteGoal(id);

    }

    /**
     * تمام هدف‌های یک کاربر
     */
    public List<Goal> getGoals(User user) {

        return goalRepository.getGoalsByUser(user);

    }

    /**
     * هدف بر اساس شناسه
     */
    public Goal getGoalById(int id) {

        return goalRepository.findById(id);

    }

    /**
     * افزودن مبلغ به پس‌انداز هدف
     */
    public void deposit(int goalId, double amount) {

        Goal goal = goalRepository.findById(goalId);

        if (goal == null) {
            return;
        }

        goal.setSavedAmount(goal.getSavedAmount() + amount);

        if (goal.getSavedAmount() >= goal.getTargetAmount()) {

            goal.setSavedAmount(goal.getTargetAmount());

            goal.setStatus(GoalStatus.COMPLETED);

        }

        goalRepository.updateGoal(goal);

    }

    /**
     * درصد پیشرفت هدف
     */
    public double getProgressPercent(Goal goal) {

        if (goal.getTargetAmount() == 0) {

            return 0;

        }

        return (goal.getSavedAmount() /
                goal.getTargetAmount()) * 100;

    }

    /**
     * مبلغ باقی مانده
     */
    public double getRemainingAmount(Goal goal) {

        return goal.getTargetAmount()
                - goal.getSavedAmount();

    }

    /**
     * هدف‌های فعال
     */
    public List<Goal> getActiveGoals(User user) {

        List<Goal> result = new ArrayList<>();

        for (Goal goal : goalRepository.getGoalsByUser(user)) {

            if (goal.getStatus() == GoalStatus.ACTIVE) {

                result.add(goal);

            }

        }

        return result;

    }

    /**
     * هدف‌های تکمیل شده
     */
    public List<Goal> getCompletedGoals(User user) {

        List<Goal> result = new ArrayList<>();

        for (Goal goal : goalRepository.getGoalsByUser(user)) {

            if (goal.getStatus() == GoalStatus.COMPLETED) {

                result.add(goal);

            }

        }

        return result;

    }

}