package model;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private User user;
    private String title;
    private double amount;
    private TransactionType type;
    private Category category;
    private LocalDate date;
    private String description;

    public Transaction() {
    }

    public Transaction(int id,
                       User user,
                       String title,
                       double amount,
                       TransactionType type,
                       Category category,
                       LocalDate date,
                       String description) {

        this.id = id;
        this.user = user;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", category=" + category.getName() +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}