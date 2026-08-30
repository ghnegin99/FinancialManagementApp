package repository;

import model.Goal;
import model.GoalStatus;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GoalRepository {

    private static final String FILE_PATH = "data/goals.txt";

    private UserRepository userRepository = new UserRepository();

    public List<Goal> getAllGoals() {

        List<Goal> goals = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return goals;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                User user = userRepository.findById(
                        Integer.parseInt(data[1]));

                Goal goal = new Goal(
                        Integer.parseInt(data[0]),
                        user,
                        data[2],
                        Double.parseDouble(data[3]),
                        Double.parseDouble(data[4]),
                        GoalStatus.valueOf(data[5])
                );

                goals.add(goal);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return goals;
    }

    public void addGoal(Goal goal) {

        List<Goal> goals = getAllGoals();

        goals.add(goal);

        saveAllGoals(goals);

    }

    public Goal findById(int id) {

        for (Goal goal : getAllGoals()) {

            if (goal.getId() == id) {
                return goal;
            }

        }

        return null;
    }

    public List<Goal> getGoalsByUser(User user) {

        List<Goal> result = new ArrayList<>();

        for (Goal goal : getAllGoals()) {

            if (goal.getUser().getId() == user.getId()) {

                result.add(goal);

            }

        }

        return result;
    }

    public void updateGoal(Goal updatedGoal) {

        List<Goal> goals = getAllGoals();

        for (int i = 0; i < goals.size(); i++) {

            if (goals.get(i).getId() == updatedGoal.getId()) {

                goals.set(i, updatedGoal);

                break;

            }

        }

        saveAllGoals(goals);

    }

    public void deleteGoal(int id) {

        List<Goal> goals = getAllGoals();

        goals.removeIf(goal -> goal.getId() == id);

        saveAllGoals(goals);

    }

    private void saveAllGoals(List<Goal> goals) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Goal goal : goals) {

                writer.write(
                        goal.getId() + "," +
                                goal.getUser().getId() + "," +
                                goal.getName() + "," +
                                goal.getTargetAmount() + "," +
                                goal.getSavedAmount() + "," +
                                goal.getStatus()
                );

                writer.newLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getNextId() {

        int max = 0;

        List<Goal> goals =
                getAllGoals();

        for (Goal goal : goals) {

            if (goal.getId() > max) {

                max = goal.getId();

            }

        }

        return max + 1;

    }

}