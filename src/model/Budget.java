package model;

import java.time.YearMonth;

public class Budget {

    private int id;
    private User user;
    private Category category;
    private YearMonth month;
    private double limitAmount;

    public Budget() {
    }

    public Budget(int id,
                  User user,
                  Category category,
                  YearMonth month,
                  double limitAmount) {

        this.id = id;
        this.user = user;
        this.category = category;
        this.month = month;
        this.limitAmount = limitAmount;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", category=" + (category != null ? category.getName() : "null") +
                ", month=" + month +
                ", limitAmount=" + limitAmount +
                '}';
    }
}