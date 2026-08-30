package model;

public class Goal {

    private int id;
    private User user;
    private String name;
    private double targetAmount;
    private double savedAmount;
    private GoalStatus status;

    public Goal() {
    }

    public Goal(int id,
                User user,
                String name,
                double targetAmount,
                double savedAmount,
                GoalStatus status) {

        this.id = id;
        this.user = user;
        this.name = name;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", name='" + name + '\'' +
                ", targetAmount=" + targetAmount +
                ", savedAmount=" + savedAmount +
                ", status=" + status +
                '}';
    }
}